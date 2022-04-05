package edu.myspring.recipe.controllers;

import edu.myspring.recipe.commands.RecipeCommand;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.exceptions.NotFoundException;
import edu.myspring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.
import static org.junit.jupiter.api.Assertions.*;

class RecipeControllerTest {

    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController=new RecipeController(recipeService);
         mockMvc=MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showById() throws Exception {
        Recipe recipe=new Recipe();
        recipe.setId(1L);
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void newRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }



    @Test
    public void getUpdateView() throws Exception {
        RecipeCommand сommand=new RecipeCommand();
        сommand.setId(1L);
        when(recipeService.findByCommandId(anyLong())).thenReturn(сommand);
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void deleteAction() throws Exception {
        //given

        //when
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService,times(1)).deleteById(anyLong());
    }

    @Test
    void recipeNotFound() throws Exception {
        Recipe recipe= new Recipe();
        recipe.setId(1L);
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void getRecipeNumberFormatException() throws Exception {
    mockMvc.perform(get("/recipe/asdf/show"))
            .andExpect(status().isBadRequest())
            .andExpect(view().name("400error"));
    }

    @Test
    void postNewRecipeFormValidation() throws Exception {
        RecipeCommand command=new RecipeCommand();
        command.setId(2L);
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);
        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","some string")
                        .param("directions","some value"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("redirect:/recipe/2/show"));
    }
    @Test
    void postNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command=new RecipeCommand();
        command.setId(2L);
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                                .param("cookTime","3000")
                      )

                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));

    }

}