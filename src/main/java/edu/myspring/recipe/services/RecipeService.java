package edu.myspring.recipe.services;

import edu.myspring.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
