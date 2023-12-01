package ui.exercises;

import common.Configuration;
import common.DBConnection;
import dao.DaoWeapon;
import dao.imp.DaoFactionImp;
import dao.imp.DaoWeaponImp;
import model.Faction;
import services.ServFaction;
import services.ServWeapon;
import dao.*;

import java.util.List;

public class MainExercise4 {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoWeapon daoWeapon = new DaoWeaponImp(Configuration.getInstance(), dbConnection);
        ServWeapon servWeapon = new ServWeapon(daoWeapon);
        DaoFaction daoFaction = new DaoFactionImp(Configuration.getInstance(), dbConnection, servWeapon);
        ServFaction servFaction = new ServFaction(daoFaction);
        List<Faction> factionList = servFaction.getAll().get();
        System.out.println(factionList);
    }
}
