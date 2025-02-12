package vector_rag

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.{ChatMemory, InMemoryChatMemory}
import org.springframework.ai.document.Document
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore
import java.util

//@SpringBootApplication 
object WEBApp {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[WEBApp], args*)
  }
}

@SpringBootApplication class WEBApp {
  @Bean private[vector_rag] def simpleVectorStore(embeddingModel: EmbeddingModel) ={
//    val builder = SimpleVectorStoreBuilder.builder
    val vectorStore = new SimpleVectorStore(embeddingModel)
    vectorStore.add(util.List.of(new Document("harrison worked at kensho")))
    vectorStore
  }

//  @Bean private[vector_rag] def milvusVectorStore(embeddingModel: EmbeddingModel) = {
//    //    val builder = SimpleVectorStoreBuilder.builder
//    val vectorStore = new MilvusVectorStore(embeddingModel)
//    vectorStore.add(util.List.of(new Document("harrison worked at kensho")))
//    vectorStore
//  }


  @Bean private[vector_rag] def chatClient(builder: ChatClient.Builder) = builder.build

  @Bean private[vector_rag] def chatMemory = new InMemoryChatMemory
}