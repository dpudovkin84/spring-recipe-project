package edu.myspring.recipe.services;

import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;


    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        try {
            Recipe recipe=recipeRepository.findById(recipeId).get();
            Byte [] byteObject=new Byte[file.getBytes().length];
            int i=0;
            for(byte b:file.getBytes()){
                byteObject[i++]=b;
            }
            recipe.setImage(byteObject);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error happend",e);
        }

    }
}
