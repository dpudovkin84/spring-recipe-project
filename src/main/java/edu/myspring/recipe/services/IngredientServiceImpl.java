package edu.myspring.recipe.services;

import edu.myspring.recipe.commands.IngredientCommand;
import edu.myspring.recipe.converters.IngredientToIngredientCommand;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (!optionalRecipe.isPresent()) {
            log.error("Recipe id no found. ID: " + recipeId);
        }
        Recipe recipe = optionalRecipe.get();
        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
        if (!optionalIngredientCommand.isPresent()) {
            log.error("Ingredient id not found " + ingredientId);
        }
        return optionalIngredientCommand.get();
    }
}
