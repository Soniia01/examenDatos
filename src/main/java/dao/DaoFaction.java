package dao;

import io.vavr.control.Either;
import model.Faction;
import model.error.Error;

import java.util.List;

public interface DaoFaction {

    Either<Error, List<Faction>> getAll();
    Either<Error, Integer>readXML();

}
