package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faction {
    private String name;
    private String contact;
    private String planet;
    private int numberControlledSystems;
    private LocalDate datePurchase;
    private List<Weapon> weaponList;
    public Faction(String name, String contact, String planet, int numberOfControlledSystems, LocalDate lastPurchase) {
    }
}
