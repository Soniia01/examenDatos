package ui.exercises;

import common.Configuration;
import common.DBConnection;
import dao.DaoWeapon;
import dao.imp.DaoWeaponImp;
import model.Weapon;
import services.ServWeapon;

public class MainExercise3 {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoWeapon daoWeapon = new DaoWeaponImp(Configuration.getInstance(),dbConnection);
        ServWeapon servWeapon = new ServWeapon(daoWeapon);
        Weapon actualizadoWeapon = new Weapon(2, "Prueba", 50.40);
        Integer res = servWeapon.update(actualizadoWeapon).get();
        System.out.println(res);
    }


}
