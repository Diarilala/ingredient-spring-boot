package td.spring_boot.ingredient_spring_boot.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import td.spring_boot.ingredient_spring_boot.service.IngredientService;

import java.sql.SQLException;

@Data
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<?> getIngredients() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ingredientService.getAllIngredients());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ingredientService.findIngredientById(Integer.valueOf(id)));
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getIngredientStock(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
