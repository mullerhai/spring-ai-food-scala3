package example

class FetchRecipeData {
  private var ingredientsStr: String = "wine,salt,pepper,olive oil,broth,rice"
  private var preferAvailableIngredients = false
  private var preferOwnRecipes = false

  def ingredients: Seq[String] = if ingredientsStr.isEmpty then "wine,salt,pepper,olive oil,broth,rice".split(",") else ingredientsStr.split("\\s*,\\s*") // util.Arrays.asList(ingredientsStr.split("\\s*,\\s*")).asScala.toSeq

  def getIngredientsStr: String = ingredientsStr

  def setIngredientsStr(ingredientsStr: String): Unit = {
    this.ingredientsStr = ingredientsStr
  }

  def isPreferAvailableIngredients: Boolean = preferAvailableIngredients

  def setPreferAvailableIngredients(preferAvailableIngredients: Boolean): Unit = {
    this.preferAvailableIngredients = preferAvailableIngredients
  }

  def isPreferOwnRecipes: Boolean = preferOwnRecipes

  def setPreferOwnRecipes(preferOwnRecipes: Boolean): Unit = {
    this.preferOwnRecipes = preferOwnRecipes
  }
}