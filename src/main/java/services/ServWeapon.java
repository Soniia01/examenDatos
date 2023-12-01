package services;

import dao.DaoWeapon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Weapon;
import model.error.Error;

import java.util.List;

public class ServWeapon{

    private final DaoWeapon daoWeapon;

    @Inject
    public ServWeapon(DaoWeapon daoWeapon) {
        this.daoWeapon = daoWeapon;
    }

    public Either<Error, List<Weapon>> getAll() {
        return daoWeapon.getAll();
    }

    public Either<Error, Weapon> get(int id) {
        return daoWeapon.get(id);
    }

    public Either<Error, List<Weapon>> getByName(String name){return daoWeapon.getByName(name);}

    public Either<Error, Integer> add(Weapon weapon){
        return daoWeapon.add(weapon);
    }

    public Either<Error, Integer> update(Weapon weapon){
        return daoWeapon.update(weapon);
    }

}
