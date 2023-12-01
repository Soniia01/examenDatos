package ui.pruebas;
import common.Configuration;
import common.DBConnection;
import dao.DaoSpy;
import dao.imp.DaoSpyImp;
import model.Spy;
import services.ServSpy;

import java.util.List;
public class PruebaSpy {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(Configuration.getInstance());
        DaoSpy daoSpy = new DaoSpyImp(Configuration.getInstance(), dbConnection);
        ServSpy servSpy = new ServSpy(daoSpy);
        List<Spy> spyList = servSpy.getAll().get();
        System.out.println(spyList);
        Spy spy = servSpy.get(1).get();
        System.out.println(spy);
        //Spy nuevoSpy = new Spy("Hera", "Syndulla");
        //Integer res = servSpy.add(nuevoSpy).get();
        //System.out.println(res);
        //Spy actualizadoSpy = new Spy(3,"Hera Syndulla", "Twi'lek");
        //Integer res2 = servSpy.update(actualizadoSpy).get();
        //System.out.println(res2);
        Integer res = servSpy.delete(3).get();
        System.out.println(res);
    }
}
