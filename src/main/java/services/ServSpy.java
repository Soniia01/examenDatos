package services;

import dao.DaoSpy;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Spy;
import model.error.Error;
import java.util.List;
public class ServSpy {
    private final DaoSpy daoSpy;
    @Inject
    public ServSpy(DaoSpy daoSpy) {
        this.daoSpy = daoSpy;
    }
    public Either<Error, List<Spy>> getAll() {
        return daoSpy.getAll();
    }

    public Either<Error, Spy> get(int id){
        return daoSpy.get(id);
    }

    public Either<Error, Integer>add(Spy spy){
        return daoSpy.add(spy);
    }

    public Either<Error, Integer>update(Spy spy){
        return daoSpy.update(spy);
    }

    public Either<Error, Integer>delete(int id){
        return daoSpy.delete(id);
    }

}
