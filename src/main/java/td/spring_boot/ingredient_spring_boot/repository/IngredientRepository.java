package td.spring_boot.ingredient_spring_boot.repository;

import lombok.Data;
import org.springframework.stereotype.Repository;
import td.spring_boot.ingredient_spring_boot.entity.Ingredient;
import td.spring_boot.ingredient_spring_boot.entity.IngredientStock;
import td.spring_boot.ingredient_spring_boot.enums.CategoryEnum;
import td.spring_boot.ingredient_spring_boot.enums.UnitTypeEnum;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Repository
public class IngredientRepository {

    private final DataSource dataSource;

    public List<Ingredient> getIngredientList() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            SELECT i.id AS ingredient_id, i.name AS
                            ingredient_name, i.price, i.category
                            FROM Ingredient i
"""
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("ingredient_id"));
                ingredient.setName(resultSet.getString("ingredient_name"));
                ingredient.setPrice(resultSet.getBigDecimal("price"));
                ingredient.setCategory(category == null ? null : CategoryEnum.valueOf((category)));
                ingredients.add(ingredient);
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ingredient findIngredientById(Integer idIngredient) throws SQLException {
        Ingredient ingredient = new Ingredient();
        try (Connection connection = dataSource.getConnection()) {
            String query = """
                    SELECT i.id AS ingredient_id, i.name AS ingredient_name,
                    i.category AS ingredient_category, i.price AS ingredient_price
                    FROM Ingredient i
                    WHERE i.id = ?;
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idIngredient);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String category = resultSet.getString("ingredient_category");
                        ingredient.setId(resultSet.getInt("ingredient_id"));
                        ingredient.setName(resultSet.getString("ingredient_name"));
                        ingredient.setPrice(resultSet.getBigDecimal("ingredient_price"));
                        ingredient.setCategory(category == null ? null : CategoryEnum.valueOf(category));
                    }
                }
            }
        }
        return ingredient;
    }

    public IngredientStock findIngredientByIdAt(int identifier, Instant datetime, UnitTypeEnum typedUnit) {
        IngredientStock ingredient = new IngredientStock();
        try(Connection connection = dataSource.getConnection()) {
            String query = """
                    SELECT i.id AS ingredient_id, i.name AS ingredient_name,
                    i.price AS ingredient_price, i.category AS ingredient_category,
                    SUM(CASE
                        WHEN s.type = 'IN' THEN s.quantity
                        WHEN s.type = 'OUT' THEN -s.quantity
                        END) as stock, s.unit AS unit
                    FROM Ingredient i
                    JOIN stockmovement s ON i.id = s.id_ingredient
                    WHERE i.id = ?
                    AND s.creation_datetime <= ?
                    AND s.unit = ?::unit_type
                    GROUP BY ingredient_id, ingredient_name, ingredient_price, ingredient_category, unit
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, identifier);
                preparedStatement.setTimestamp(2, Timestamp.from(datetime));
                preparedStatement.setString(3, String.valueOf(typedUnit));
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String category = resultSet.getString("ingredient_category");
                        String unit = resultSet.getString("unit");
                        ingredient.setId(resultSet.getInt("ingredient_id"));
                        ingredient.setName(resultSet.getString("ingredient_name"));
                        ingredient.setPrice(resultSet.getBigDecimal("ingredient_price"));
                        ingredient.setCategory(category == null ? null : CategoryEnum.valueOf(category));
                        ingredient.setQuantity(resultSet.getBigDecimal("stock"));
                        ingredient.setUnit(unit == null ? null : UnitTypeEnum.valueOf(unit));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }
}
