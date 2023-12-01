package ui.exercises;

import common.Configuration;
import common.DBConnection;
import dao.DaoWeapon;
import dao.imp.DaoWeaponImp;
import model.Weapon;
import services.ServWeapon;

import java.util.List;

public class MainExercise5 {

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoWeapon daoWeapon = new DaoWeaponImp(Configuration.getInstance(), dbConnection);
        ServWeapon servWeapon = new ServWeapon(daoWeapon);
        List<Weapon> weaponListName = servWeapon.getByName("Empire").get();
        System.out.println(weaponListName);
    }

}
