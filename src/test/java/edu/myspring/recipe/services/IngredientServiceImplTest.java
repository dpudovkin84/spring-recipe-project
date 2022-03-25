package edu.myspring.recipe.services;

import edu.myspring.recipe.commands.IngredientCommand;
import edu.myspring.recipe.converters.IngredientCommandToIngredient;
import edu.myspring.recipe.converters.IngredientToIngredientCommand;
import edu.myspring.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import edu.myspring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import edu.myspring.recipe.domain.Ingredient;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.repositories.RecipeRepository;
import edu.myspring.recipe.repositories.UnitOfMeasureRepository;
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
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientToIngredientCommand=new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService=new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);

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

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
    @Test
    void deleteIngredientById(){
        Recipe recipe=new Recipe();
        Ingredient ingredient=new Ingredient();
        ingredient.setId(3L);
        recipe.getIngredients().add(ingredient);
        //given
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        //when
        ingredientService.deleteIngredientById(1L,3L);

        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(recipe);
    }


}