package services;

import dao.DaoFaction;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Faction;
import model.error.Error;

import java.util.List;

public class ServFaction {
    private final DaoFaction daoFaction;

    @Inject
    public ServFaction(DaoFaction daoFaction) {
        this.daoFaction = daoFaction;
    }

    public Either<Error, List<Faction>> getAll() {
        return daoFaction.getAll();
    }

}
