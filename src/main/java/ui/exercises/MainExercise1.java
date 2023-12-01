package ui.exercises;

import common.Configuration;
import common.DBConnection;
import dao.imp.FactionsImplSONIA;
import dao.xml.FactionsImpl;
import model.Faction;
import services.FactionsImplService;
import services.FactionsXMLService;

import java.util.List;

public class MainExercise1 {
    public static void main(String[] args) {
        FactionsImplService factionsImplService= new FactionsImplService(new FactionsImplSONIA(new DBConnection(Configuration.getInstance())));
        FactionsXMLService factionsXMLService = new FactionsXMLService(new FactionsImpl());
        List<Faction> factions=factionsXMLService.getAll().getOrNull();
        System.out.println(factions);
        for (Faction faction:factions) {
            factionsImplService.save(faction);
            System.out.println(faction);
        }
    }
}
