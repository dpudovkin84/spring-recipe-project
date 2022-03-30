package edu.myspring.recipe.services;

import edu.myspring.recipe.commands.RecipeCommand;
import edu.myspring.recipe.converters.RecipeCommandToRecipe;
import edu.myspring.recipe.converters.RecipeToRecipeCommand;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.exceptions.NotFoundException;
import edu.myspring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;


    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> recipes=new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional=recipeRepository.findById(id);
        if (!recipeOptional.isPresent()){
//            throw new RuntimeException("Recipe Not Found");
                throw new NotFoundException("Recipe not found. For id:"+id);
        }
//        return recipeRepository.findById(id).orElse(null);
        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe=recipeCommandToRecipe.convert(command);
        Recipe savedRecipe=recipeRepository.save(detachedRecipe);
        log.debug("Saved recipeId:"+savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findByCommandId(Long id) {
        return  recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
