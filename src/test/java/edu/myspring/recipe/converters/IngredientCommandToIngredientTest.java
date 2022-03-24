package edu.myspring.recipe.converters;

import edu.myspring.recipe.commands.IngredientCommand;
import edu.myspring.recipe.commands.UnitOfMeasureCommand;
import edu.myspring.recipe.domain.Ingredient;
import edu.myspring.recipe.domain.Recipe;
import edu.myspring.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {



    IngredientCommandToIngredient converter;
    IngredientCommand ingredientCommand;
    Ingredient ingredient;

//    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM_ID = 2L;


    @BeforeEach
    public void setUp(){
        converter=new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void convert() {
        //given
        ingredientCommand=new IngredientCommand();
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setId(ID_VALUE);
        UnitOfMeasureCommand uomCommand=new UnitOfMeasureCommand();
        uomCommand.setId(UOM_ID);
        uomCommand.setDescription("UOM command description");
        ingredientCommand.setUom(uomCommand);
        //when
        ingredient=converter.convert(ingredientCommand);
        //then
        assertNotNull(ingredient);
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(AMOUNT,ingredient.getAmount());
        assertEquals(ID_VALUE,ingredient.getId());
    }

    @Test
    public void convertWithNullUOM(){
        //given
        ingredientCommand=new IngredientCommand();
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setId(ID_VALUE);
        //when
        ingredient=converter.convert(ingredientCommand);
        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(AMOUNT,ingredient.getAmount());
        assertEquals(ID_VALUE,ingredient.getId());
    }
}