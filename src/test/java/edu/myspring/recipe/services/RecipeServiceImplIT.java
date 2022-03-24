package edu.myspring.recipe.services;

import edu.myspring.recipe.commands.RecipeCommand;
import edu.myspring.recipe.converters.RecipeCommandToRecipe;
import edu.myspring.recipe.converters.RecipeToRecipeCommand;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RecipeServiceImplIT {

    @Autowired
    RecipeServiceImpl recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    private String NEW_DESCRIPTION="New Description";

    @BeforeEach
    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//        recipeService=new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);

    }




    @Test
    @Transactional
    public void testSaveOfDescription(){
        //given
        Iterable<Recipe> recipes=recipeRepository.findAll();
        Recipe testRecipe=recipes.iterator().next();
        RecipeCommand testRecipeCommand=recipeToRecipeCommand.convert(testRecipe);
        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand=recipeService.saveRecipeCommand(testRecipeCommand);
        //then
        assertEquals(NEW_DESCRIPTION,savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(),testRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(),testRecipeCommand.getCategories().size());
    }


}