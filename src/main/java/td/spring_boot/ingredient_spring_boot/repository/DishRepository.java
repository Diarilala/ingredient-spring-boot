package td.spring_boot.ingredient_spring_boot.repository;

import lombok.Data;
import org.springframework.stereotype.Repository;
import td.spring_boot.ingredient_spring_boot.entity.Dish;
import td.spring_boot.ingredient_spring_boot.entity.DishIngredient;
import td.spring_boot.ingredient_spring_boot.entity.Ingredient;
import td.spring_boot.ingredient_spring_boot.enums.DishTypeEnum;
import td.spring_boot.ingredient_spring_boot.enums.UnitTypeEnum;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Repository
public class DishRepository {
    private final DataSource dataSource;

    public List<Dish> getDishList() throws SQLException {
        List<Dish> dishList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            SELECT d.id AS id_dish, d.name AS dish_name, d.dish_type AS dish_type,
                                   d.price AS dish_price, i.name AS ingredient_name, di.quantity_required AS ingredient_quantity_required, di.unit
                                    AS ingredient_unit FROM dish d JOIN dishingredient di
                                ON d.id = di.id_dish JOIN ingredient i ON di.id_ingredient = i.id;
"""
            );
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Dish> existingDish = dishList.stream()
                            .filter(d -> {
                                try {
                                    return d.getId().equals(resultSet.getInt("id_dish"));
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            })
                            .findFirst();
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(resultSet.getString("ingredient_name"));
                    DishIngredient dishIngredient = new DishIngredient();
                    dishIngredient.setIngredient(ingredient);
                    dishIngredient.setQuantity(resultSet.getBigDecimal("ingredient_quantity_required"));
                    String unit = resultSet.getString("ingredient_unit");
                    dishIngredient.setUnit(unit == null ? null : UnitTypeEnum.valueOf(unit));
                    if (existingDish.isPresent()) {
                        existingDish.get().getDishIngredients().add(dishIngredient);
                    } else {
                        Dish dish = new Dish();
                        String type = resultSet.getString("dish_type");
                        dish.setId(resultSet.getInt("id_dish"));
                        dish.setName(resultSet.getString("dish_name"));
                        dish.setDishType(type == null ? null : DishTypeEnum.valueOf(type));
                        dish.setPrice(resultSet.getBigDecimal("dish_price"));
                        List<DishIngredient> dishIngredientList = new ArrayList<>();
                        dishIngredientList.add(dishIngredient);
                        dish.setDishIngredients(dishIngredientList);
                        dishList.add(dish);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return dishList;
        }
    }
}
