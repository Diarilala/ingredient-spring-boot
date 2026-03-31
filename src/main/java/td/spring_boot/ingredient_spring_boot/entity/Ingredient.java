package td.spring_boot.ingredient_spring_boot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import td.spring_boot.ingredient_spring_boot.enums.CategoryEnum;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor

public class Ingredient {
    private Integer id;
    private String name;
    private BigDecimal price;
    private CategoryEnum category;
    private List<StockMovement> stockMovementList;
}
