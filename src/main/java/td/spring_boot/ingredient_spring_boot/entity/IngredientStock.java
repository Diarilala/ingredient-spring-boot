package td.spring_boot.ingredient_spring_boot.entity;

import lombok.Data;
import td.spring_boot.ingredient_spring_boot.enums.CategoryEnum;
import td.spring_boot.ingredient_spring_boot.enums.UnitTypeEnum;

import java.math.BigDecimal;

@Data
public class IngredientStock {
    private Integer id;
    private String name;
    private BigDecimal price;
    private CategoryEnum category;
    private BigDecimal quantity;
    private UnitTypeEnum unit;
}
