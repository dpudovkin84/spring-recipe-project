package edu.myspring.recipe.services;

import edu.myspring.recipe.commands.RecipeCommand;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    ImageService imageService;
    @Mock
    RecipeRepository recipeRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService=new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws IOException {
        //given
        Long id=1L;
        MultipartFile multipartFile=new MockMultipartFile("imagefile",
                "testing.txt","text/plain","Srping Guru".getBytes());

        Recipe recipe=new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional= Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ArgumentCaptor<Recipe> argumentCaptor=ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id,multipartFile);

        //then
        verify(recipeRepository,times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe=argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length,savedRecipe.getImage().length);

    }



}