package services;

import dao.imp.FactionsImplSONIA;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Faction;

import java.util.List;

public class FactionsImplService {
    private final FactionsImplSONIA factionsImplJDBC;

    @Inject
    public FactionsImplService(FactionsImplSONIA factionsImplJDBC) {
        this.factionsImplJDBC = factionsImplJDBC;
    }
    public Either<Error, List<Faction>> getAll() {
        return factionsImplJDBC.getAll();
    }
    public Either<Error, Faction> get(String name) {
        return factionsImplJDBC.get(name);
    }
    public Either<Error, Integer> update(Faction f, String nombre) {
        return  factionsImplJDBC.update(f,nombre);
    }
    public Either<Error, Integer> delete(Faction c) {
        return  factionsImplJDBC.delete(c);
    }
    public Either<Error, Integer> save(Faction c) {
        return factionsImplJDBC.save(c);
    }
}
