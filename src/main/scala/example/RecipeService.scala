package example

import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.image.{ImageModel, ImagePrompt}
import org.springframework.ai.model.function.FunctionCallback
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.{SearchRequest, VectorStore}
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

import java.util.function.Consumer
//import scala.collection.JavaConverters._
import scala.jdk.CollectionConverters._
import java.util.Optional
import scala.jdk.FunctionConverters._
import java.util.function.Consumer
import scala.jdk.FunctionWrappers.AsJavaConsumer
@Service object RecipeService {
  private val log = LoggerFactory.getLogger(classOf[RecipeService])
}

@Service class RecipeService(private val chatClient: ChatClient, private val imageModel: Optional[ImageModel], private val vectorStore: VectorStore) {
  @Value("classpath:/prompts/recipe-for-ingredients") private val recipeForIngredientsPromptResource:Resource = null
  @Value("classpath:/prompts/recipe-for-available-ingredients") private val recipeForAvailableIngredientsPromptResource:Resource = null
  @Value("classpath:/prompts/prefer-own-recipe") private val preferOwnRecipePromptResource:Resource = null
  @Value("classpath:/prompts/image-for-recipe") private val imageForRecipePromptResource:Resource = null
  @Value("${app.always-available-ingredients}") private val alwaysAvailableIngredients:String = null //seq[String]
  @Value("${app.available-ingredients-in-fridge}") private val availableIngredientsInFridge :String= null //seq[String]

  def addRecipeDocumentForRag(pdfResource: Resource, pageTopMargin: Int, pageBottomMargin: Int): Unit = {
    RecipeService.log.info("Add recipe document {} for rag", pdfResource.getFilename)
    val documentReaderConfig = PdfDocumentReaderConfig.builder.withPageTopMargin(pageTopMargin).withPageBottomMargin(pageBottomMargin).build
    val documentReader = new PagePdfDocumentReader(pdfResource, documentReaderConfig)
    val documents = new TokenTextSplitter().apply(documentReader.get)
    vectorStore.accept(documents)
  }

  def fetchRecipeFor(ingredients: Seq[String], preferAvailableIngredients: Boolean, preferOwnRecipes: Boolean): example.Recipe = {
    var recipe: example.Recipe = null
    if (!preferAvailableIngredients && !preferOwnRecipes) recipe = fetchRecipeFor(ingredients.toList)
    else if (preferAvailableIngredients && !preferOwnRecipes) recipe = fetchRecipeWithFunctionCallingFor(ingredients)
    else if (!preferAvailableIngredients && preferOwnRecipes) recipe = fetchRecipeWithRagFor(ingredients)
    else recipe = fetchRecipeWithRagAndFunctionCallingFor(ingredients)
    if (imageModel.isPresent) {
      val imagePromptTemplate = new PromptTemplate(imageForRecipePromptResource)
      val map = Map{"recipe"-> recipe.name;"ingredients"->recipe.ingredients.mkString(",")}
      val imagePromptInstructions =imagePromptTemplate.render(map.asJava)
//      val imagePromptInstructions = imagePromptTemplate.render(util.Map.of("recipe", recipe.name, "ingredients", String.join(",", recipe.ingredients)))
      val imageGeneration = imageModel.get.call(new ImagePrompt(imagePromptInstructions)).getResult
//      Recipe(recipe.name, recipe.description, recipe.ingredients, recipe.instructions, imageUrl)
      return  example.Recipe( recipe.name, recipe.description, recipe.ingredients, recipe.instructions, imageGeneration.getOutput.getUrl)
    }
    recipe
  }


  private def fetchRecipeFor(ingredients: Seq[String]) = {
    def  lanfunc (us: ChatClient.PromptUserSpec):Unit = us.text(recipeForIngredientsPromptResource).param("ingredients", ingredients.mkString(","))
    RecipeService.log.info("Fetch recipe without additional information")
    val userFunc = (us: ChatClient.PromptUserSpec) => us.text(recipeForIngredientsPromptResource).param("ingredients", ingredients.mkString(","))
//    AsJavaConsumer[PromptUserSpec](userFunc.asJavaFunction).asJavaConsumer
    val uf :java.util.function.Function[PromptUserSpec, PromptUserSpec] = userFunc.asJavaFunction
//    uf.asInstanceOf[Consumer[PromptUserSpec]]
//    uf.asJavaConsumer

//    val userJavaConsumer:Consumer[PromptUserSpec] = uf.asInstanceOf[Consumer[PromptUserSpec]] // userFunc.asInstanceOf[Consumer[PromptUserSpec]]
//    chatClient.prompt.user(userJavaConsumer).call.entity(classOf[example.Recipe])
    chatClient.prompt.user(us => lanfunc(us)).call.entity(classOf[example.Recipe])
  }

  private def fetchRecipeWithFunctionCallingFor(ingredients: Seq[String]) = {
    RecipeService.log.info("Fetch recipe with additional information from function calling")
    def  lanfunc (us: ChatClient.PromptUserSpec):Unit = us.text(recipeForIngredientsPromptResource).param("ingredients", ingredients.mkString(","))
    val userFunc = (us: ChatClient.PromptUserSpec) => { us.text(recipeForAvailableIngredientsPromptResource).param("ingredients", ingredients.mkString(","))}
//    chatClient.prompt.user((us: ChatClient.PromptUserSpec) => us.text(recipeForAvailableIngredientsPromptResource).param("ingredients", ingredients.mkString(",")))
//    FunctionCallback.builder().function("fetchIngredientsAvailableAtHome", this.fetchIngredientsAvailableAtHome).build
//    val userJavaConsumer:Consumer[PromptUserSpec] = userFunc.asInstanceOf[Consumer[PromptUserSpec]] // AsJavaConsumer[PromptUserSpec](userFunc)
//      .user(userFunc.asJavaFunction.asInstanceOf[Consumer[PromptUserSpec]])
    chatClient.prompt.user(us => lanfunc(us))
      .functions("fetchIngredientsAvailableAtHome")
      .call.entity(classOf[example.Recipe])
  }

  private def fetchIngredientsAvailableAtHome = {
    RecipeService.log.info("Fetching ingredients available at home function called by LLM")
    Stream.concat(availableIngredientsInFridge, alwaysAvailableIngredients).toList
  }

  private def fetchRecipeWithRagFor(ingredients: Seq[String]) = {
    RecipeService.log.info("Fetch recipe with additional information from vector store")
    val promptTemplate = new PromptTemplate(recipeForIngredientsPromptResource, Map{"ingredients"-> ingredients.mkString(",")}.asJava)
    val advise = new PromptTemplate(preferOwnRecipePromptResource).getTemplate
//    val advisorSearchRequest = SearchRequest.defaults.withTopK(2).withSimilarityThreshold(0.7)
    val advisorSearchRequest = SearchRequest.builder().topK(2).similarityThreshold(0.7).build()
    chatClient.prompt.user(promptTemplate.render).advisors(new QuestionAnswerAdvisor(vectorStore, advisorSearchRequest, advise)).call.entity(classOf[example.Recipe])
  }

  private def fetchRecipeWithRagAndFunctionCallingFor(ingredients: Seq[String]) = {
    RecipeService.log.info("Fetch recipe with additional information from vector store and function calling")
    val promptTemplate = new PromptTemplate(recipeForAvailableIngredientsPromptResource, Map{"ingredients"-> ingredients.mkString(",")}.asJava)
    val advise = new PromptTemplate(preferOwnRecipePromptResource).getTemplate
    val advisorSearchRequest = SearchRequest.builder().topK(2).similarityThreshold(0.7).build()
//    val advisorSearchRequest = SearchRequest.defaults.withTopK(2).withSimilarityThreshold(0.7)
    chatClient.prompt.user(promptTemplate.render).functions("fetchIngredientsAvailableAtHome").advisors(new QuestionAnswerAdvisor(vectorStore, advisorSearchRequest, advise)).call.entity(classOf[example.Recipe])
  }
}