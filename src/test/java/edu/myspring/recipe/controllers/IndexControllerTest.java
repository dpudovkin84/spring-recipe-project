package edu.myspring.recipe.controllers;

import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    IndexController indexController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        indexController =new IndexController(recipeService);

    }

    @Test
    void getIndexPage() {
//        Recipe recipe=new Recipe();
//        given
        Set<Recipe> recipes=new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe=new Recipe();
        recipe.setId(10L);
        recipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipes);

        Set<Recipe> recipes1=new HashSet<>();
        ArgumentCaptor<Set<Recipe>> argumentCaptor=
                ArgumentCaptor.forClass(Set.class);

//        when
        String result=indexController.getIndexPage(model);

//        then
        assertEquals("index",result);
        verify(recipeService,times(1))
                .getRecipes();
        verify(model,times(1))
                .addAttribute(eq("recipes"),argumentCaptor.capture());
        Set<Recipe> setInController=argumentCaptor.getValue();
        System.out.println(setInController);
        assertEquals(2,setInController.size());
    }

    @Test
    void testMockMvc() {
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(indexController).build();

        try {
            mockMvc.perform(get("/")).andExpect(status().isOk())
                    .andExpect(view().name("index"))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}











