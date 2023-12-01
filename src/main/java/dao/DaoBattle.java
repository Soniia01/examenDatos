package dao;

import io.vavr.control.Either;
import model.Battle;
import model.Sale;
import model.error.Error;

import java.util.List;

public interface DaoBattle {

    Either<Error, List<Battle>> getAll();
    Either<Error, Sale>get(int id);
    Either<Error, Integer> add(Sale sale);
    Either<Error, Integer> update(Sale sale);
    Either<Error, Integer> delete(int id);

}
