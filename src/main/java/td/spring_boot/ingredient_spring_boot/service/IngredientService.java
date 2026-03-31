package td.spring_boot.ingredient_spring_boot.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import td.spring_boot.ingredient_spring_boot.entity.Ingredient;
import td.spring_boot.ingredient_spring_boot.repository.IngredientRepository;

import java.sql.SQLException;
import java.util.List;

@Data
@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() throws SQLException {
        return ingredientRepository.findIngredients();
    }

    public Ingredient findIngredientById(Integer id) throws SQLException {
            return ingredientRepository.findIngredientById(id);
    }
}
