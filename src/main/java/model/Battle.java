package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battle {
    private int id;
    private String bName;
    private String firstFaction;
    private String secondFaction;
    private String place;
    private LocalDate date;
    private int idSpy;
}
