package dao.imp;

import common.Configuration;
import common.DBConnection;
import dao.DaoFaction;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Faction;
import model.Weapon;
import model.error.Error;
import services.ServWeapon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Log4j2
public class DaoFactionImp implements DaoFaction {

    private final Configuration config;
    private final DBConnection db;
    private ServWeapon serv;

    @Inject
    public DaoFactionImp(Configuration config, DBConnection db, ServWeapon serv) {
        this.config = config;
        this.db = db;
        this.serv = serv;
    }


    @Override
    public Either<Error, List<Faction>> getAll() {
        List<Faction> factionList;
        Either<Error, List<Faction>> res;
        try (Connection connection = db.getConnection()) {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from faction");
            factionList = readRS(rs);
            res = Either.right(factionList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Integer> readXML() {
//        Either<ErrorDb, Integer> res;
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(FactionsXML.class);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            //Path xmlFile = Paths.get("data/Factions.xml");
//            Path xmlFile = Paths.get(Configuration.getInstance().getPathXml());
//            List<Faction> factionList = new ArrayList<>();
//            FactionsXML factionsXml = (FactionsXML) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
//
//            List<FactionXML> factionXmlList = factionsXml.getFactions();
//            for (FactionXML factionXml : factionXmlList) {
//                Faction faction = new Faction(
//                        factionXml.getName(),
//                        factionXml.getContact(),
//                        factionXml.getPlanet(),
//                        factionXml.getNumberOfControlledSystems(),
//                        factionXml.getDateLastPurchase(),
//                        parseWeaponsFromXML(factionXml.getWeapons())
//                );
//                factionList.add(faction);
//            }
//            res = Either.right(factionList.size());
//        } catch (JAXBException | IOException e) {
//            log.error(e.getMessage(), e);
//            res = Either.left(new ErrorDb("There was an error reading the XML file", 0, LocalDateTime.now()));
//        }
//        return res;
//       }

//    private List<Weapon> parseWeaponsFromXML(WeaponsXml weaponsXml) {
//        List<Weapon> weaponList = new ArrayList<>();
//        List<WeaponXml> weaponXmlList = weaponsXml.getWeaponXmlList();
//        for (WeaponXml weaponXml : weaponXmlList) {
//            Weapon weapon = new Weapon(weaponXml.getName(), weaponXml.getPrice());
//            weaponList.add(weapon);
//        }
//        return weaponList;
    return null;
    }

    private List<Faction> readRS(ResultSet rs) throws SQLException {
        List<Faction> factionList = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("fname");
            String contact = rs.getString("contact");
            String planet = rs.getString("planet");
            int numberCS = rs.getInt("number_controlled_systems");
            LocalDate datePurchase = rs.getDate("date_last_purchase").toLocalDate();
            List<Weapon> weaponList = serv.getByName(name).getOrElse(new ArrayList<>());
            if (weaponList.isEmpty()) {
                factionList.add(new Faction(name, contact, planet, numberCS, datePurchase, new ArrayList<>()));
            } else {
                factionList.add(new Faction(name, contact, planet, numberCS, datePurchase, weaponList));
            }
        }
        return factionList;
    }
}
