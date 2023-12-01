package dao;

import io.vavr.control.Either;
import model.Sale;
import model.error.Error;

import java.util.List;

public interface DaoSale {

    Either<Error, List<Sale>> getAll();
    Either<Error, Sale>get(int id);
    Either<Error, Integer> add(Sale sale);
    Either<Error, Integer> update(Sale sale);
    Either<Error, Integer> delete(int id);

}
