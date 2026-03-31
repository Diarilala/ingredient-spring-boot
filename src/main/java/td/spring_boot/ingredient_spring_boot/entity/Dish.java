package td.spring_boot.ingredient_spring_boot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import td.spring_boot.ingredient_spring_boot.enums.DishTypeEnum;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor

public class Dish {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private BigDecimal price;
    private List<DishIngredient> dishIngredients;
}
