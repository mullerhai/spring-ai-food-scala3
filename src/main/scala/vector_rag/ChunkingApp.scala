package com.vector_rag

import com.fasterxml.jackson.databind.ObjectMapper
import com.vector_rag.JsonLinesDocumentWriter
import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.boot.{ApplicationArguments, ApplicationRunner, SpringApplication, WebApplicationType}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

import java.nio.file.Path

//@SpringBootApplication 
object ChunkingApp {
  def main(args: Array[String]): Unit = {
    val sa = new SpringApplication(classOf[ChunkingApp])
    sa.setWebApplicationType(WebApplicationType.NONE)
    sa.run(args*)
  }
}

@SpringBootApplication class ChunkingApp {
  @Bean def readDocuments(objectMapper: ObjectMapper): ApplicationRunner = (args: ApplicationArguments) => {
    val input = "https://www.ipa.go.jp/security/vuln/websecurity/ug65p900000196e2-att/000017316.pdf"
    val output = Path.of("target", "documents.jsonl")
    val reader = new TikaDocumentReader(input)
    val transformer = new TokenTextSplitter
    val writer = new JsonLinesDocumentWriter(objectMapper, output)
    writer.write(transformer.transform(reader.read))
  }
}