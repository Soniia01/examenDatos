package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spy {
    private int id;
    private String name;
    private String race;

    public Spy(String name, String race) {
        this.name = name;
        this.race = race;
    }
}
