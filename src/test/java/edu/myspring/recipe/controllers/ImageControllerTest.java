package edu.myspring.recipe.controllers;

import edu.myspring.recipe.commands.RecipeCommand;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.services.ImageService;
import edu.myspring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    private ImageService imageService;
    @Mock
    private RecipeService recipeService;



    @InjectMocks
    private ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void showUploadForm() throws Exception {
        //when
        when(recipeService.findByCommandId(anyLong())).thenReturn(new RecipeCommand());
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
        //then
        verify(recipeService,times(1)).findByCommandId(1L);

    }

    @Test
    void handleImagePost() throws Exception {
        //given
        MockMultipartFile multipartFile=
                new MockMultipartFile("imagefile","testing.txt","text/plain","Spring Framework Guru".getBytes());
        //when
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        //then
        verify(imageService,times(1)).saveImageFile(anyLong(),any());
    }
}