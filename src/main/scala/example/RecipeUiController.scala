package example

import org.apache.commons.lang3.reflect.FieldUtils
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.messages.{AssistantMessage, UserMessage}
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.ChatOptions
import org.springframework.ai.image.ImageModel
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils.capitalize
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestMapping}
import org.springframework.ai.chat.prompt.{Prompt, PromptTemplate}
import org.springframework.http.MediaType

import scala.collection.mutable.ArrayBuffer
import java.util.Optional
import org.springframework.web.bind.annotation.*
@Controller
@RequestMapping(Array("/")) object RecipeUiController {
  private val log = LoggerFactory.getLogger(classOf[RecipeUiController])
}

@Controller
@RequestMapping(Array("/")) class RecipeUiController(private val recipeService: RecipeService, private val chatModel: ChatModel, private val imageModel: Optional[ImageModel]) {
  @GetMapping def fetchUI(model: Model): String = {
    val aiModelNames = getAiModelNames.mkString("&")
    model.addAttribute("aiModel",  aiModelNames)
    if (!model.containsAttribute("fetchRecipeData")) model.addAttribute("fetchRecipeData", new example.FetchRecipeData)
    "index"
  }
//Array("application/json;charset=UTF-8") MediaType.APPLICATION_JSON_VALUE
  @GetMapping(value = Array("/mosses"),produces = Array("application/json;charset=UTF-8")  ) def demoss(@RequestParam(name = "prompt") prompt: String): String = {
    val currentPromptMessage = new UserMessage(prompt)
    val prompts = new Prompt(prompt)
    val response = this.chatModel.call(prompts) //.call(prompt);
    val content = response.getResult.getOutput.getContent
    println(content)
    content
  }
  @PostMapping
  @throws[Exception]
  def fetchRecipeUiFor(fetchRecipeData: example.FetchRecipeData, model: Model): String = {
    var recipe: Recipe = null
    try recipe = recipeService.fetchRecipeFor(fetchRecipeData.ingredients, fetchRecipeData.isPreferAvailableIngredients, fetchRecipeData.isPreferOwnRecipes)
    catch {
      case e: Exception =>
        RecipeUiController.log.info("Retry RecipeUiController:fetchRecipeFor after exception caused by LLM")
        recipe = recipeService.fetchRecipeFor(fetchRecipeData.ingredients, fetchRecipeData.isPreferAvailableIngredients, fetchRecipeData.isPreferOwnRecipes)
    }
    model.addAttribute("recipe", recipe)
    model.addAttribute("fetchRecipeData", fetchRecipeData)
    fetchUI(model)
  }

  private def getAiModelNames = {
//    val modelNames = new util.ArrayList[String]
    val modelNames = new ArrayBuffer[String]()
    val chatModelProvider = chatModel.getClass.getSimpleName.replace("ChatModel", "")
    val chatModelDefaultOptions = chatModel.getDefaultOptions
    try {
      val modelName = FieldUtils.readField(chatModelDefaultOptions, "model", true).asInstanceOf[String]
      val nameStr = s"${chatModelProvider} (${capitalize(modelName)}"
      modelNames.append(nameStr)
//      modelNames.append("%s (%s)".formatted(chatModelProvider, capitalize(modelName)))
//      modelNames.add("%s (%s)".formatted(chatModelProvider, capitalize(modelName)))
    } catch {
      case e1: Exception =>
        try {
          val modelName = FieldUtils.readField(chatModelDefaultOptions, "deploymentName", true).asInstanceOf[String]
//          modelNames.add("%s (%s)".formatted(chatModelProvider, capitalize(modelName)))
          val modelStr = s"${chatModelProvider} (${capitalize(modelName)}"
          modelNames.append(modelStr)
//          modelNames.append("%s (%s)".formatted(chatModelProvider, capitalize(modelName)))
        } catch {
          case e2: Exception =>
//            modelNames.add(chatModelProvider)
            modelNames.append(chatModelProvider)
        }
    }
    if (imageModel.isPresent) {
      val imageModelProvider = imageModel.get.getClass.getSimpleName.replace("ImageModel", "")
      try {
        val imageModelDefaultOptions : ChatOptions= FieldUtils.readField(imageModel.get, "defaultOptions", true).asInstanceOf[ChatOptions]
        val imageModel2 = FieldUtils.readField(imageModelDefaultOptions, "model", true).toString //.asInstanceOf[String]
//        modelNames.add("%s (%s)".formatted(imageModelProvider, capitalize(imageModel)))
        val modelStr = s"${chatModelProvider} (${capitalize(imageModel2)}"
        modelNames.append(modelStr)
//        modelNames.append("%s (%s)".formatted(chatModelProvider, capitalize(imageModel2)))
      } catch {
        case e: Exception =>
//          modelNames.add(imageModelProvider)
          modelNames.append(imageModelProvider)
      }
    }
    modelNames
  }
}