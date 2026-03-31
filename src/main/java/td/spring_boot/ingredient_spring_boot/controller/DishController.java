package td.spring_boot.ingredient_spring_boot.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import td.spring_boot.ingredient_spring_boot.entity.Dish;

@Data
@RestController
@RequestMapping("/dishes")
public class DishController {

    @GetMapping
    public ResponseEntity<?> getDishes() {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateIngredients(@PathVariable String id, @RequestBody Dish dish) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<?> getIngredients(@PathVariable String id, @RequestParam String ingredientName, @RequestParam int priceAround) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
