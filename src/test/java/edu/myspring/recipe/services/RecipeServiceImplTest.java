package edu.myspring.recipe.services;

import edu.myspring.recipe.converters.RecipeCommandToRecipe;
import edu.myspring.recipe.converters.RecipeToRecipeCommand;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.exceptions.NotFoundException;
import edu.myspring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    Set<Recipe> sourceRecipes;

    Recipe testRecipe;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    recipeService =new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);
    testRecipe=new Recipe();
    testRecipe.setId(1L);
    testRecipe.setDescription("Test Recipe");
    sourceRecipes=new HashSet<>();
    sourceRecipes.add(testRecipe);
    }

    @Test
    void getRecipeById() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(testRecipe));
        Recipe returnedRecipe= recipeService.findById(1L);
        assertEquals(1,returnedRecipe.getId());
        assertNotNull(returnedRecipe);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }

    @Test
    void getRecipes() {
        when(recipeRepository.findAll()).thenReturn(sourceRecipes);
        assertEquals(1, recipeService.getRecipes().size());
        verify(recipeRepository,times(1)).findAll();
        verify(recipeRepository,never()).findById(anyLong());
    }

    @Test
    void deleteById(){
        //given
        Long id=1L;
        //when
        recipeService.deleteById(id);
        //then
        verify(recipeRepository,times(1)).deleteById(id);
    }

    @Test()
    public void getRecipeByIdTestNotFound(){
        Optional<Recipe> recipeOptional=Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
//        Recipe recipeReturned=recipeService.findById(1L);
        Exception exception=assertThrows(NotFoundException.class,()->{
            recipeService.findById(1L);});
    }




}