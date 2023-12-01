package dao;

import io.vavr.control.Either;
import model.Spy;
import model.error.Error;

import java.util.List;

public interface DaoSpy {

    Either<Error, List<Spy>> getAll();
    Either<Error, Spy> get(int id);
    Either<Error, Integer> add(Spy spy);
    Either<Error, Integer> update(Spy spy);
    Either<Error, Integer> delete(int id);

}
