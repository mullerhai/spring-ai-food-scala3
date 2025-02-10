package example

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{PostMapping, RequestMapping, RequestParam, RestController}
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(Array("/api/v1/recipes")) class RecipeResource(private val recipeService: RecipeService) {
  @PostMapping(Array("upload")) def addRecipeDocumentsForRag(@RequestParam("file") file: MultipartFile, @RequestParam(required = false, defaultValue = "0") pageTopMargin: Int, @RequestParam(required = false, defaultValue = "0") pageBottomMargin: Int): ResponseEntity[Void] = {
    recipeService.addRecipeDocumentForRag(file.getResource, pageTopMargin, pageBottomMargin)
    ResponseEntity.noContent.build
  }
}