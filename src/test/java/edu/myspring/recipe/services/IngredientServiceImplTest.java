package edu.myspring.recipe.services;

import edu.myspring.recipe.commands.IngredientCommand;
import edu.myspring.recipe.converters.IngredientToIngredientCommand;
import edu.myspring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import edu.myspring.recipe.domain.Ingredient;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientToIngredientCommand=new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService=new IngredientServiceImpl(recipeRepository,ingredientToIngredientCommand);

    }

    @Test
    void findByRecipeIdAndIngredientId() {
        //given
        Long recipeId=1L;
        Long ingredientId=2L;
        Ingredient ingredient=new Ingredient();
        ingredient.setId(ingredientId);
        Recipe recipe=new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(ingredient);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        IngredientCommand ingredientCommand=ingredientService.findByRecipeIdAndIngredientId(recipeId,ingredientId);
        //then
        assertEquals(ingredientId,ingredientCommand.getId());
        assertEquals(recipeId,ingredientCommand.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyLong());

    }
}