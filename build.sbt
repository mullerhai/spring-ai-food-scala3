import scala.collection.immutable.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.2"

lazy val root = (project in file("."))
  .settings(
    name := "spring-ai-food-scala3"
  )

javacOptions ++= Seq("-encoding", "UTF-8")
//jdkOptions ++= Seq("-encoding", "UTF-8") jdk 15 -23
ThisBuild / resolvers += "springs" at  "https://repo.spring.io/milestone/"
libraryDependencies += "org.apache.httpcomponents.client5" % "httpclient5" % "5.4.2"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.17.0"
// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-webflux
//libraryDependencies += "org.springframework.boot" % "spring-boot-starter-webflux" % "3.4.2"
libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % "3.4.2"
libraryDependencies += "org.springframework.boot" % "spring-boot-starter-actuator" % "3.4.2"
libraryDependencies += "org.springframework.boot" % "spring-boot-starter-thymeleaf" % "3.4.2"
//libraryDependencies += "org.springframework.ai" % "spring-ai-redis-store-spring-boot-starter" % "1.0.0-M5"
libraryDependencies += "org.springframework.ai" % "spring-ai-pdf-document-reader" % "1.0.0-M5"
libraryDependencies += "org.springframework.ai" % "spring-ai-tika-document-reader" % "1.0.0-M5"
libraryDependencies += "org.springframework.ai" % "spring-ai-ollama-spring-boot-starter" % "1.0.0-M5"
libraryDependencies += "org.springframework.boot" % "spring-boot-devtools" % "3.4.1"
libraryDependencies += "org.springframework.ai" % "spring-ai-bom" % "1.0.0-M5"
libraryDependencies += "org.springframework.ai" % "spring-ai-milvus-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-openai-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-openai-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-transformers-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-redis" % "0.8.1"

// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc spring-boot-docker-compose spring-ai-postgresml-spring-boot-starter
//libraryDependencies += "org.springframework.boot" % "spring-boot-starter-jdbc" % "3.4.2"
//libraryDependencies += "org.springframework.boot" % "spring-boot-docker-compose" % "3.4.2"


//
//libraryDependencies += "org.bytedeco" % "javacv" % "1.5.11"
//libraryDependencies += "org.bytedeco" % "javacv-platform" % "1.5.11"
//libraryDependencies += "org.springframework" % "spring-context" % "6.2.2" // "7.0.0-M1"
//libraryDependencies += "com.alibaba.nacos" % "nacos-spring-context" % "2.1.1"
//libraryDependencies += "org.springframework.cloud" % "spring-cloud-context" % "4.2.0"
//libraryDependencies += "org.springframework" % "spring-context-support" % "6.2.2" // "7.0.0-M1"
//excludeDependencies += "org.springframework" % "spring-context" % "7.0.0-M1"
//libraryDependencies += "org.springframework.ai" % "spring-ai-openai-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-ollama-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-postgresml-spring-boot-starter" % "1.0.0-M5"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-mistral-ai-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.experimental" % "mcp" % "0.6.0"
//libraryDependencies += "org.springframework.experimental" % "spring-ai-mcp" % "0.6.0"
//
//libraryDependencies += "org.springframework.boot" % "spring-boot-starter-actuator" % "3.4.2"
//
//libraryDependencies += "org.springframework.data" % "spring-data-neo4j" % "8.0.0-M1"
//libraryDependencies += "jakarta.validation" % "jakarta.validation-api" % "3.1.1"
//libraryDependencies += "org.springframework.ai" % "spring-ai-stability-ai" % "1.0.0-M5"
//
////spring-boot-starter-oss
//libraryDependencies += "org.babyfish.jimmer" % "jimmer-spring-boot-starter" % "0.9.48"
//libraryDependencies += "cn.dev33" % "sa-token-redis-jackson" % "1.40.0"
////libraryDependencies += "cn.dev33" % "sa-token-spring-boot-starter" % "1.40.0"
//libraryDependencies += "cn.dev33" % "sa-token-spring-boot3-starter" % "1.40.0"
//libraryDependencies += "cn.hutool" % "hutool-core" % "5.8.35"
//libraryDependencies += "com.alibaba.cloud.ai" % "spring-ai-alibaba-starter" % "1.0.0-M3.3"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-vertex-ai-embedding" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-huggingface" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-pdf-document-reader" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-markdown-document-reader" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-tika-document-reader" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-qdrant-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-redis-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-neo4j-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-milvus-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-pgvector-store" % "1.0.0-M5"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-cassandra-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-pinecone-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-elasticsearch-store" % "1.0.0-M5"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-pgvector-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-spring-boot-docker-compose" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-minimax-spring-boot-starter" % "1.0.0-M5"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-spring-boot-testcontainers" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-cosmos-db-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-openai-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-openai" % "1.0.0-M5"
//
//libraryDependencies += "io.micrometer" % "micrometer-tracing-bridge-otel" % "1.4.2"
//libraryDependencies += "io.opentelemetry" % "opentelemetry-exporter-otlp" % "1.46.0"
//libraryDependencies += "io.micrometer" % "micrometer-registry-otlp" % "1.14.3"
//libraryDependencies += "net.ttddyy.observation" % "datasource-micrometer-spring-boot" % "1.0.6"
//libraryDependencies += "io.opentelemetry.instrumentation" % "opentelemetry-logback-appender-1.0" % "2.12.0-alpha" //% "runtime"
////libraryDependencies += "io.github.qifan777" % "spring-ai-spark-spring-boot-starter" % "0.1.10"
//
//libraryDependencies += "org.projectlombok" % "lombok" % "1.18.36" // % "provided"
//libraryDependencies += "org.springframework.ai" % "spring-ai-transformers-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-pinecone-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-huggingface-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-neo4j-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-spring-cloud-bindings" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-milvus-store-spring-boot-starter" % "1.0.0-M5"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-weaviate-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-retry" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-minimax" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-mistral-ai" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-moonshot" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-openai" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-chroma-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-mongodb-atlas-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-bedrock" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-cosmos-db-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-oci-genai" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-vector-store" % "0.8.1"
//libraryDependencies += "org.springframework.ai" % "spring-ai-hanadb-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-gemfire-store" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-anthropic" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-watsonx-ai" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-watsonx-ai-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-spring-boot-testcontainers" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-qianfan-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-zhipuai-spring-boot-starter" % "1.0.0-M5"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-typesense-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-postgresml-spring-boot-starter" % "1.0.0-M5"
//
//libraryDependencies += "org.springframework.ai" % "spring-ai-azure-openai-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-pinecone-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-huggingface-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-neo4j-store-spring-boot-starter" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-spring-cloud-bindings" % "1.0.0-M5"
//
////libraryDependencies += "org.springframework.ai" % "spring-ai-qwen-spring-boot-starter" % "1.0.0-M5"
////libraryDependencies += "io.springboot.ai" % "spring-ai-qwen-ai" % "1.0.3"
////libraryDependencies += "io.springboot.ai" % "spring-ai-qwen-spring-boot-starter" % "1.0.3"
//libraryDependencies += "io.swagger.core.v3" % "swagger-models" % "2.2.28"
//
//libraryDependencies += "org.springframework.boot" % "spring-boot-starter-data-redis" % "3.4.2"
//libraryDependencies += "org.springframework.ai" % "spring-ai-transformers" % "1.0.0-M5"
//libraryDependencies += "org.springframework.ai" % "spring-ai-redis" % "0.8.1"
//libraryDependencies += "org.springframework.ai" % "spring-ai-redis-spring-boot-starter" % "0.8.1"
//libraryDependencies += "com.github.javaparser" % "javaparser-core" % "3.26.3"
//libraryDependencies += "com.github.javaparser" % "javaparser-symbol-solver-core" % "3.26.3"
//libraryDependencies += "com.github.javaparser" % "javaparser-core-serialization" % "3.26.3"
//
//libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "2.5.3"
//libraryDependencies += "org.apache.opennlp" % "opennlp-uima" % "2.5.3"
////libraryDependencies += "org.apache.opennlp" % "opennlp-dl" % "2.5.3"
//libraryDependencies += "org.apache.opennlp" % "opennlp-tools-models" % "2.5.3"
//libraryDependencies += "org.apache.opennlp" % "opennlp-models-training" % "1.2.0" pomOnly()
////libraryDependencies += "org.apache.opennlp" % "opennlp-distr" % "2.5.3" pomOnly()
////libraryDependencies += "org.apache.opennlp" % "opennlp-dl-gpu" % "2.5.3"
////libraryDependencies += "org.apache.opennlp" % "opennlp-models-tokenizer" % "1.2.0" pomOnly()
//
//libraryDependencies += "work.nvwa" % "nvwa-vine-spring-boot-starter-deepseek" % "0.2.0"
//libraryDependencies += "cn.lishiyuan" % "deepseek4j" % "1.0.0"
//libraryDependencies += "io.github.pig-mesh.ai" % "spring-ai-transformers" % "1.3.0"
//libraryDependencies += "org.apache.camel" % "camel-torchserve" % "4.9.0"
//libraryDependencies += "org.apache.camel.karaf" % "camel-torchserve" % "4.9.0"
