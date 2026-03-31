package td.spring_boot.ingredient_spring_boot.entity;

import td.spring_boot.ingredient_spring_boot.enums.MovementTypeEnum;

import java.time.Instant;

public class StockMovement {
    private int id;
    private StockValue value;
    private MovementTypeEnum type;
    private Instant creationDatetime;
}
