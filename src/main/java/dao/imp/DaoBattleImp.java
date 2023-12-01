package dao.imp;

import common.Configuration;
import common.DBConnection;
import dao.DaoBattle;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Battle;
import model.Sale;
import model.error.Error;

import java.util.List;

public class DaoBattleImp implements DaoBattle {

    private final Configuration config;
    private final DBConnection db;

    @Inject
    public DaoBattleImp(Configuration config, DBConnection db) {
        this.config = config;
        this.db = db;
    }

    @Override
    public Either<Error, List<Battle>> getAll() {
        return null;
    }

    @Override
    public Either<Error, Sale> get(int id) {
        return null;
    }

    @Override
    public Either<Error, Integer> add(Sale sale) {
        return null;
    }

    @Override
    public Either<Error, Integer> update(Sale sale) {
        return null;
    }

    @Override
    public Either<Error, Integer> delete(int id) {
        return null;
    }
}
