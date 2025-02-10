package example

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.io.Resource

@Configuration class RecipeFinderConfiguration {
  @Value("classpath:/prompts/fix-json-response") private val fixJsonResponsePromptResource: Resource = null

  @Bean def chatClient(chatClientBuilder: ChatClient.Builder): ChatClient = chatClientBuilder.defaultSystem(fixJsonResponsePromptResource).build
}