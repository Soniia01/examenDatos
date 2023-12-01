package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {
    private int id;
    private String name;
    private double price;

    public Weapon(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
