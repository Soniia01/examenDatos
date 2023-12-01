package model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "factions")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactionsXML {
        @XmlElement(name = "faction")
        private List<FactionXML> factions;
}
