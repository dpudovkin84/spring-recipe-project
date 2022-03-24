package edu.myspring.recipe.converters;

import edu.myspring.recipe.commands.UnitOfMeasureCommand;
import edu.myspring.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommand unitOfMeasureCommand;
    UnitOfMeasureCommandToUnitOfMeasure converter;
    private final Long ID=1L;
    private final String DESCRIPTION="description";


    @BeforeEach
    void setUp() {
        converter=new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNull(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
        unitOfMeasureCommand=new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);
        //when
        UnitOfMeasure unitOfMeasure=converter.convert(unitOfMeasureCommand);

        //then
        assertNotNull(unitOfMeasure);
        assertEquals(ID,unitOfMeasure.getId());
        assertEquals(DESCRIPTION,unitOfMeasure.getDescription());
    }
}