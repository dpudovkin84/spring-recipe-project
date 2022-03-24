package edu.myspring.recipe.controllers;

import edu.myspring.recipe.commands.RecipeCommand;
import edu.myspring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredient(@PathVariable String id, Model model){
        log.debug("Getting ingredients for Recipe: "+id);
        model.addAttribute("recipe",recipeService.findByCommandId(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }
}
