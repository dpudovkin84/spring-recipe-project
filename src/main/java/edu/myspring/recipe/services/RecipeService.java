package edu.myspring.recipe.services;

import edu.myspring.recipe.commands.RecipeCommand;
import edu.myspring.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findByCommandId(Long id);
    void deleteById(Long id);
}
