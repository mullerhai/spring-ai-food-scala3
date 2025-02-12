package vector_rag

import io.milvus.client.MilvusServiceClient
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.*
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.document.Document
import org.springframework.ai.rag.Query
import org.springframework.ai.rag.generation.augmentation.QueryAugmenter
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer
import org.springframework.ai.rag.retrieval.search.{DocumentRetriever, VectorStoreDocumentRetriever}
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestParam, RestController}

import java.util.function.{BiFunction, Consumer, Function}
import java.util
import java.util.function.Consumer
import java.util.stream.Collectors
import scala.jdk.FunctionConverters.*
import scala.jdk.FunctionWrappers.{AsJavaBiFunction, AsJavaFunction}

@RestController class WEBRagController {
  @Autowired private val chatClient: ChatClient = null
  @Autowired private val vectorStore: VectorStore = null
  @Autowired private val chatMemory: ChatMemory = null
  @Autowired private val milvusClient : MilvusServiceClient = null

  @PostMapping(Array("/rag1")) def rag1(@RequestParam question: String): AnyRef = {
    val answer = chatClient.prompt.advisors(new QuestionAnswerAdvisor(vectorStore), new SimpleLoggerAdvisor).user(question).call.content
    answer
  }

  @GetMapping(value = Array("/chatseek"), produces = Array("application/json;charset=UTF-8")) def chatss(@RequestParam(name = "prompt") prompt: String): String = {
//    val currentPromptMessage = new UserMessage(prompt)
//    val prompts = new Prompt(prompt)
    val response = chatClient.prompt.user(prompt).call().chatResponse() //.call(prompt);
    val content = response.getResult.getOutput.getContent
    println(content)
    content
  }

  @PostMapping(Array("/rag2")) def rag2(@RequestParam question: String, @RequestParam(defaultValue = "") chatHistory: util.List[String]): AnyRef = {
    // https://python.langchain.com/docs/expression_language/cookbook/retrieval#conversational-retrieval-chain
    val documentRetriever = VectorStoreDocumentRetriever.builder.vectorStore(vectorStore).build
    val condenseQuestionPromt =
      """
                       Given the following conversation and a follow up question, rephrase the follow up question to be a standalone question, in its original language.

                       Chat History:
                       {chat_history}
                       Follow Up Input: {question}
                       Standalone question:
                       """
    def queryTranfunc(query: Query) : Query = {
      def userFunc(user: ChatClient.PromptUserSpec):Unit= user.text(condenseQuestionPromt).param("question", query.text).param("chat_history", chatHistory.stream.collect(Collectors.joining("\n")))
      val input = chatClient.prompt
        .user(us => userFunc(us))
        .call.content
      new Query(input)
    }

    def queryDocFunc(query: Query, documents: util.List[Document]): Query = {
      val answerPrompt =
        """
                    Answer the question based only on the following context:
                    {context}

                    Question: {question}
                    """
      val template = new PromptTemplate(answerPrompt)
      template.add("question", query.text)
//      template.add("context", documents.map((doc: Document) => doc.getContent).mkString("\n\n"))
      template.add("context", documents.stream.map((doc: Document) => doc.getContent).collect(Collectors.joining("\n\n")))
      new Query(template.render)
    }

//    Function[Query, Query] queryTranfunc = queryTranfunc.asJavaFunction
//    val  queryTranfun:Function[Query, Query] = queryTranfunc.asJavaFunction
//    val queryDocfun:BiFunction[Query, util.List[Document], Query] = queryDocFunc.asJavaBiFunction
    val rag = RetrievalAugmentationAdvisor.builder()
      .documentRetriever(documentRetriever)
      .queryTransformers((q:Query) =>queryTranfunc(q))
      .queryAugmenter((q,doc) =>queryDocFunc(q,doc))
      .build
    val answer = chatClient.prompt.user(question).advisors(rag).call.content
    answer

    //    val userFunc = (user: ChatClient.PromptUserSpec) => user.text(condenseQuestionPromt).param("question", query.text).param("chat_history", chatHistory.stream.collect(Collectors.joining("\n")))
    //
    //    val input = chatClient.prompt
    //      .user(userFunc.asInstanceOf[Consumer[ChatClient.PromptUserSpec]])
    //      .call.content
    //
    //    val queryTransformer =  (query: Query) => {
    //      val userFunc = (user: ChatClient.PromptUserSpec) => user.text(condenseQuestionPromt).param("question", query.text).param("chat_history", chatHistory.stream.collect(Collectors.joining("\n")))
    //      val input = chatClient.prompt
    //        .user(userFunc.asInstanceOf[Consumer[ChatClient.PromptUserSpec]])
    //        .call.content
    //      new Query(input)
    //    }.asJavaFunction[Query,Query]

//    val queryTransformer : QueryTransformer = (query: Query) => {
//      def userFunc(user: ChatClient.PromptUserSpec):Unit= user.text(condenseQuestionPromt).param("question", query.text).param("chat_history", chatHistory.stream.collect(Collectors.joining("\n")))
//      val input = chatClient.prompt
//        .user(us => userFunc(us))
//        .call.content
//      new Query(input)
//    }.asJavaFunction[Query, Query]

//    val queryTransformers = (query: Query) => {
//      val condenseQuestionPromt =
//        """
//                    Given the following conversation and a follow up question, rephrase the follow up question to be a standalone question, in its original language.
//
//                    Chat History:
//                    {chat_history}
//                    Follow Up Input: {question}
//                    Standalone question:
//                    """
//
//
//      val userFunc = (user: ChatClient.PromptUserSpec) => user.text(condenseQuestionPromt).param("question", query.text).param("chat_history", chatHistory.stream.collect(Collectors.joining("\n")))
//
//      val input = chatClient.prompt
//        .user(userFunc.asInstanceOf[Consumer[ChatClient.PromptUserSpec]])
//        .call.content
//      return new Query(input)
//    }.asJavaFunction[Query,Query]


//    val queryAugmenter = (query: Query, documents: util.List[Document]) => {
//      val answerPrompt =
//        """
//                    Answer the question based only on the following context:
//                    {context}
//
//                    Question: {question}
//                    """
//      val template = new PromptTemplate(answerPrompt)
//      template.add("question", query.text)
//      template.add("context", documents.stream.map((doc: Document) => doc.getContent).collect(Collectors.joining("\n\n")))
//      return new Query(template.render)
//    }.asJavaBiFunction[Query,util.List[Document],Query]



  }

  @PostMapping(Array("/chat")) def chat(@RequestParam query: String, @RequestParam conversationId: String): AnyRef = {
    val msgChatMemAdvisor = new MessageChatMemoryAdvisor(chatMemory)
    def advisorFunc(advisor: ChatClient.AdvisorSpec):Unit = advisor
      .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId)
//    val advisorFunc = (advisor: ChatClient.AdvisorSpec) => advisor
//      .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId)
    //advisorFunc.asInstanceOf[Consumer[ChatClient.AdvisorSpec]]
    val answer = chatClient.prompt
      .advisors(msgChatMemAdvisor)
      .advisors(ad => advisorFunc(ad))
      .user(query).call.content
    answer
  }
}