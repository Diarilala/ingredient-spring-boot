package td.spring_boot.ingredient_spring_boot.repository;

import lombok.Data;
import org.springframework.stereotype.Repository;
import td.spring_boot.ingredient_spring_boot.entity.Ingredient;
import td.spring_boot.ingredient_spring_boot.enums.CategoryEnum;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class IngredientRepository {

    private final DataSource dataSource;

    public List<Ingredient> findIngredients() {
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
}
