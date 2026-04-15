package td.spring_boot.ingredient_spring_boot.dto;

import lombok.Data;
import td.spring_boot.ingredient_spring_boot.enums.DishTypeEnum;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DishDTO {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private BigDecimal price;
    private List<DishIngredientDTO> dishIngredients;
}
