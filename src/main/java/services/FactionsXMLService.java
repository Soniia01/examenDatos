package services;

import dao.xml.FactionsImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Faction;

import java.util.List;

public class FactionsXMLService {
    private final FactionsImpl factions;

    @Inject
    public FactionsXMLService(FactionsImpl factions) {
        this.factions = factions;
    }

    public Either<Error, List<Faction>> getAll() {
        return factions.getAll();
    }

}
