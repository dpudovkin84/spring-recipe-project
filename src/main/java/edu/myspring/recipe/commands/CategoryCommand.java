package edu.myspring.recipe.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;

}
