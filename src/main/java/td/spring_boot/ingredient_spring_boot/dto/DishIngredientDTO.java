package td.spring_boot.ingredient_spring_boot.dto;

import lombok.Data;
import td.spring_boot.ingredient_spring_boot.enums.UnitTypeEnum;
import java.math.BigDecimal;
@Data
public class DishIngredientDTO {
    private String ingredientName;
    private BigDecimal quantity;
    private UnitTypeEnum unit;
}
