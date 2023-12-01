package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    private int id;
    private int units;
    private int idWeaponsFaction;
    private LocalDate date;
}
