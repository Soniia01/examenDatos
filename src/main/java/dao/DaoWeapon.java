package dao;

import io.vavr.control.Either;
import model.Weapon;
import model.error.Error;

import java.util.List;

public interface DaoWeapon {

    Either<Error, List<Weapon>>getAll();
    Either<Error, Weapon>get(int id);
    Either<Error, List<Weapon>> getByName(String name);
    Either<Error, Integer> add(Weapon newWeapon);
    Either<Error, Integer> update(Weapon updatedWeapon);
    Either<Error, Integer> delete(int id);

}
