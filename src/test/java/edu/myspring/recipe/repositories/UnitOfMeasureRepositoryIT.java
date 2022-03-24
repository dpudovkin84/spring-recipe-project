package edu.myspring.recipe.repositories;

import edu.myspring.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DirtiesContext
    void findByDescription() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure=unitOfMeasureRepository
                .findByDescription("Cup");
        assertEquals("Cup",optionalUnitOfMeasure.get().getDescription());
    }
    @Test
    void findByDescriptionDash() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure=unitOfMeasureRepository
                .findByDescription("Dash");
        assertEquals("Dash",optionalUnitOfMeasure.get().getDescription());
    }

}