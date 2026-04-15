package td.spring_boot.ingredient_spring_boot.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import td.spring_boot.ingredient_spring_boot.dto.DishDTO;
import td.spring_boot.ingredient_spring_boot.dto.DishIngredientDTO;
import td.spring_boot.ingredient_spring_boot.entity.Dish;
import td.spring_boot.ingredient_spring_boot.entity.DishIngredient;
import td.spring_boot.ingredient_spring_boot.repository.DishRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class DishService {
    private final DishRepository dishRepository;

    public List<DishDTO> getDishList() throws SQLException {
        List<Dish> DishList = dishRepository.getDishList();
        List<DishDTO> dishDTOList = new ArrayList<>();
        for (Dish dish : DishList) {
            DishDTO dishDTO = new DishDTO();
            dishDTO.setId(dish.getId());
            dishDTO.setName(dish.getName());
            dishDTO.setDishType(dish.getDishType());
            dishDTO.setPrice(dish.getPrice());
            List<DishIngredientDTO> dishIngredientDTOList = new ArrayList<>();
            for (DishIngredient ingredient : dish.getDishIngredients()) {
                DishIngredientDTO dishIngredientDTO = new DishIngredientDTO();
                dishIngredientDTO.setIngredientName(ingredient.getIngredient().getName());
                dishIngredientDTO.setQuantity(ingredient.getQuantity());
                dishIngredientDTO.setUnit(ingredient.getUnit());
                dishIngredientDTOList.add(dishIngredientDTO);
            }
            dishDTO.setDishIngredients(dishIngredientDTOList);
            dishDTOList.add(dishDTO);
        }
        return dishDTOList;
    }
}
