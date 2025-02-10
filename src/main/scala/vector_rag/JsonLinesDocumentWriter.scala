package com.vector_rag

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.ai.document.{Document, DocumentWriter}

import java.io.{IOException, UncheckedIOException}
import java.nio.file.{Files, Path}
import java.util

class JsonLinesDocumentWriter(private var objectMapper: ObjectMapper, private var path: Path) extends DocumentWriter {
  override def accept(documents: util.List[Document]): Unit = {
    try {
      val writer = Files.newBufferedWriter(path)
      try{
        import scala.jdk.CollectionConverters.*
//        import scala.collection.JavaConverters._
//        import scala.collection.JavaConverters.* //.JavaConversions.*
        for (document <- documents.asScala) {
          val line = objectMapper.writeValueAsString(document)
          writer.write(line)
          writer.write("\n")
        }
      } catch
      {
        case e: IOException =>
          throw new UncheckedIOException(e)
      }
      finally
      {
        if (writer != null) writer.close()
      }
    }catch {
      case e: IOException =>
        throw new UncheckedIOException(e)
    }
  }
}