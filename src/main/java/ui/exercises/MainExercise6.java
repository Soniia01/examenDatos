package ui.exercises;

import common.Configuration;
import common.DBConnection;
import dao.DaoSpy;
import dao.imp.DaoSpyImp;
import services.ServSpy;

public class MainExercise6 {

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoSpy daoSpy = new DaoSpyImp(Configuration.getInstance(), dbConnection);
        ServSpy servSpy = new ServSpy(daoSpy);
        Integer res = servSpy.delete(3).get();
        System.out.println(res);
    }
}
