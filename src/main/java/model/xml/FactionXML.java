package model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "faction")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactionXML {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "contact")
    private String contact;
    @XmlElement(name = "planet")
    private String planet;
    @XmlElement(name = "numberCS")
    private int numberOfControlledSystems;
    @XmlElement(name = "dateLastPurchase")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateLastPurchase;
    @XmlElement(name = "weapons")
    private List<WeaponsXML> weapons;
}
