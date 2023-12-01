package dao.xml;

import common.Configuration;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.Faction;
import model.Weapon;
import model.xml.FactionXML;
import model.xml.FactionsXML;
import model.xml.WeaponXML;
import model.xml.WeaponsXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Log4j2
public class FactionsImpl {
    public Either<java.lang.Error, List<Faction>> getAll() {
        Either<java.lang.Error, List<Faction>> either;
        List<Faction> list = new ArrayList<>();
        Either<Error, FactionsXML> listXml = read();
        List<WeaponsXML> weaponsXMLList = new ArrayList<>();
        List<WeaponXML> weaponXML = new ArrayList<>();
        List<Weapon> weaponsList = new ArrayList<>();
        if (listXml.isRight()) {
            for (FactionXML factionxml : listXml.get().getFactions()) {
                //For each faction get its attributes and convert them into non XML
                if (factionxml.getWeapons() != null) {
                    //If weapons it's not null, convert them to non XML
                    weaponsXMLList.addAll(factionxml.getWeapons());
                    for (WeaponsXML weaponsXML : weaponsXMLList) {
                        weaponXML.addAll(weaponsXML.getWeapons());
                    }
                    for (WeaponXML weaponXML1 : weaponXML) {
                        Weapon weapons = new Weapon();
                        weapons.setName(weaponXML1.getName());
                        weapons.setPrice(weaponXML1.getPrice());
                        weaponsList.add(weapons);
                    }
                    Faction faction = new Faction(factionxml.getName(), factionxml.getContact(), factionxml.getPlanet(), factionxml.getNumberOfControlledSystems(), factionxml.getDateLastPurchase(), weaponsList);
                    list.add(faction);
                } else {
                    Faction faction = new Faction(factionxml.getName(), factionxml.getContact(), factionxml.getPlanet(), factionxml.getNumberOfControlledSystems(), factionxml.getDateLastPurchase(), null);
                    list.add(faction);
                }
            }
            either = Either.right(list);
        } else {
            either = Either.left(new Error());
        }
        return either;
    }

    private Either<Error, FactionsXML> read() {
        Either<Error, FactionsXML> either;
        try {
            JAXBContext context = JAXBContext.newInstance(FactionsXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Path xmlFile = Paths.get(Configuration.getInstance().getPathXml());

            FactionsXML listXml = (FactionsXML) unmarshaller.unmarshal(Files.newInputStream(xmlFile));

            either = Either.right(listXml);

        } catch (JAXBException | IOException e) {
            either = Either.left(new Error());
            log.error(e.getMessage());
        }
        return either;
    }
}
