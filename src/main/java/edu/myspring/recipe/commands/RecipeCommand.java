package edu.myspring.recipe.commands;

import edu.myspring.recipe.domain.Category;
import edu.myspring.recipe.domain.Difficulty;
import edu.myspring.recipe.domain.Ingredient;
import edu.myspring.recipe.domain.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Difficulty difficulty;
    private Set<IngredientCommand> ingredients=new HashSet<>();
//    private Byte[] image;
    private NotesCommand notes;
    Set<CategoryCommand> categories=new HashSet<>();
}
