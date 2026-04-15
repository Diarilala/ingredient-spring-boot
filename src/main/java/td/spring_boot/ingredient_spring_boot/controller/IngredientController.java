package td.spring_boot.ingredient_spring_boot.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import td.spring_boot.ingredient_spring_boot.enums.UnitTypeEnum;
import td.spring_boot.ingredient_spring_boot.service.IngredientService;

import java.sql.SQLException;
import java.time.Instant;

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
            return  ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ingredientService.findIngredientById(Integer.valueOf(id)));
        } catch (SQLException e) {
           return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getIngredientStock(@PathVariable String id, @RequestParam Instant at, @RequestParam UnitTypeEnum unit) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ingredientService.findIngredientByIdAt(Integer.valueOf(id), at, unit));
        } catch (SQLException e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
