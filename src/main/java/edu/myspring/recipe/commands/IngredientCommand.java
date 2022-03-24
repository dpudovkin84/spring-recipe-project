package edu.myspring.recipe.commands;

import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private Long id;

    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
//    private Recipe recipe;

}
