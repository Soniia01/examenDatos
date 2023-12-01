package dao.imp;

import common.DBConnection;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Faction;
import model.Weapon;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FactionsImplSONIA {
    private final DBConnection db;

    @Inject
    public FactionsImplSONIA(DBConnection db) {
        this.db = db;
    }

    public Either<Error, List<Faction>> getAll() {
        Either<Error, List<Faction>> either;
        try (Connection myConnection = db.getConnection();
             Statement statement = myConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM faction");
            either = Either.right(readRS(resultSet));
        } catch (SQLException e) {
            either = Either.left(new Error());
        }
        return either;
    }

    public Either<Error, Faction> get(String name) {
        Either<Error, Faction> either;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM faction where fname = ?")) {
            preparedStatement.setString(1, String.valueOf(name));
            ResultSet rs = preparedStatement.executeQuery();
            either = Either.right(readRS(rs).stream().filter(faction -> faction.getName().equalsIgnoreCase(name)).findFirst().orElse(null));
        } catch (SQLException e) {
            either = Either.left(new Error());
        }
        return either;
    }

    public Either<Error, Integer> update(Faction f, String nombre) {
        Either<Error, Integer> either;
        int rowsAffected;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("UPDATE faction SET fname = ? WHERE fname = ?")) {

            preparedStatement.setString(2, f.getName());
            preparedStatement.setString(1, nombre);
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                either = Either.right(rowsAffected);
            } else {
                either = Either.left(new Error());
            }
        } catch (SQLException e) {
            throw new Error();
        }
        return either;
    }


    public Either<Error, Integer> delete(Faction c) {
        Either<Error, Integer> either = null;
        int rowsAffected;
        try (Connection con = db.getConnection()) {
            try (
                    PreparedStatement deleteFaction = con.prepareStatement("DELETE FROM faction WHERE fname=?");
                    PreparedStatement deleteWeaponsFactions = con.prepareStatement("DELETE FROM weapons_factions WHERE name_faction=?");
                    PreparedStatement deleteBattles = con.prepareStatement("DELETE FROM battles WHERE faction_one = ? OR faction_two = ? OR (faction_one = ? AND faction_two = ?);")) {

                deleteFaction.setString(1, c.getName());
                con.setAutoCommit(false);
                deleteWeaponsFactions.setString(1, c.getName());
                deleteWeaponsFactions.executeUpdate();
                deleteBattles.setString(1, c.getName());
                deleteBattles.setString(2, c.getName());
                deleteBattles.setString(3, c.getName());
                deleteBattles.setString(4, c.getName());
                deleteBattles.executeUpdate();
                rowsAffected = deleteFaction.executeUpdate();
                con.commit();

                if (rowsAffected > 0) {
                    either = Either.right(rowsAffected);
                } else {
                    either = Either.left(new Error("no se ha borrao por la forein ki"));
                }
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
                if (e.getErrorCode() == 1451) {
                    either = Either.left(new Error("no se ha borrao por la forein ki"));
                } else {
                    either = Either.left(new Error("no se ha borrao por idk why"));
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return either;
    }

    public Either<Error, Integer> save(Faction c) {
        Either<Error, Integer> either = null;
        int rowsAffected;
        try(Connection con = db.getConnection()){
        try (
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO faction (fname,contact,planet,number_controlled_systems,date_last_purchase) VALUES (?,?,?,?,?)");
             PreparedStatement weaponsStatement = con.prepareStatement("INSERT INTO weapons (wname,wprice) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, c.getName());
            preparedStatement.setString(2, c.getContact());
            preparedStatement.setString(3, c.getPlanet());
            preparedStatement.setInt(4, c.getNumberControlledSystems());
            preparedStatement.setDate(5, Date.valueOf(c.getDatePurchase()));
            con.setAutoCommit(false);
            if (c.getWeaponList() != null) {
                for (Weapon weapons : c.getWeaponList()) {
                    weaponsStatement.setString(1, weapons.getName());
                    weaponsStatement.setDouble(2, weapons.getPrice());
                    weaponsStatement.executeUpdate();
                    ResultSet rs = weaponsStatement.getGeneratedKeys();
                    if (rs.next()) {
                        weapons.setId(rs.getInt(1));
                    }
                }
            }
            rowsAffected = preparedStatement.executeUpdate();
            con.commit();
            if (rowsAffected > 0) {
                either = Either.right(rowsAffected);
            } else {
                either = Either.left(new Error("Error adding Faction"));
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                either = Either.left(new Error("Error save de orders"));
            }
        }
        } catch (SQLException e) {
            either = Either.left(new Error("Error adding Faction"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return either;
    }

    private List<Faction> readRS(ResultSet resultSet) throws SQLException {
        List<Faction> factionList = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("fname");
            String contact = resultSet.getString("contact");
            String planet = resultSet.getString("planet");
            int numberOfControlledSystems = resultSet.getInt("number_controlled_systems");
            ;
            LocalDate lastPurchase = resultSet.getDate("date_last_purchase").toLocalDate();
            factionList.add(new Faction(name, contact, planet, numberOfControlledSystems, lastPurchase));
        }
        return factionList;
    }
}
