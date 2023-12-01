package dao.imp;

import common.Configuration;
import common.DBConnection;
import dao.DaoWeapon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Weapon;
import model.error.Error;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoWeaponImp implements DaoWeapon {
    private final Configuration config;
    private final DBConnection db;

    @Inject
    public DaoWeaponImp(Configuration config, DBConnection db) {
        this.config = config;
        this.db = db;
    }


    @Override
    public Either<Error, List<Weapon>> getAll() {
        List<Weapon> weaponList;
        Either<Error, List<Weapon>> res;
        try (Connection connection = db.getConnection()){
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from weapons");
            weaponList = readRS(rs);
            res = Either.right(weaponList);
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Weapon> get(int id) {
        List<Weapon> weaponList;
        Either<Error, Weapon> res;
        try (Connection connection = db.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("select * from weapons where id =?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            weaponList = readRS(rs);
            if (!weaponList.isEmpty()){
                res = Either.right(weaponList.get(0));
            } else {
                res = Either.left(new Error("There's no weapon with that id", 0, LocalDateTime.now()));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, List<Weapon>> getByName(String name) {
        List<Weapon> weaponList;
        Either<Error, List<Weapon>> res;
        try (Connection connection = db.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("select * from weapons w join weapons_factions wf on w.id = wf.id_weapon join faction f on wf.name_faction = f.fname where f.fname =?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            weaponList = readRS(rs);
            if (!weaponList.isEmpty()){
                res = Either.right(weaponList);
            } else {
                res = Either.left(new Error("There's no weapon with that id", 0, LocalDateTime.now()));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Integer> add(Weapon newWeapon) {
        int rowsAffected;
        Either<Error, Integer> res;
        try (Connection connection = db.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("insert into weapons (wname, wprice) values (?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newWeapon.getName());
            pstmt.setDouble(2, newWeapon.getPrice());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected != 1) {
                connection.rollback();
                return Either.left(new Error("There was an error adding the new weapon", 0, LocalDateTime.now()));
            }
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int generatedId;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return Either.left(new Error("There was an error obtaining the id", 0, LocalDateTime.now()));
            }
            pstmt.setInt(1, generatedId);
            connection.commit();
            res = Either.right(rowsAffected);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Integer> update(Weapon updatedWeapon) {
        int rowsAffected;
        Either<Error, Integer> res;
        try (Connection connection = db.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("update weapons set wname=?, wprice=? where id=?");
            pstmt.setString(1, updatedWeapon.getName());
            pstmt.setDouble(2, updatedWeapon.getPrice());
            pstmt.setInt(3, updatedWeapon.getId());
            rowsAffected = pstmt.executeUpdate();
            connection.commit();
            res = Either.right(rowsAffected);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Integer> delete(int id) {
        int rowsAffected;
        Either<Error, Integer> res;
        try (Connection connection = db.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement("delete from weapons where id =?");
            pstmt.setInt(1, id);
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected !=1){
                res = Either.left(new Error("There was an error deleting the weapon", 0, LocalDateTime.now()));
            } else {
                res = Either.right(rowsAffected);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    private List<Weapon> readRS(ResultSet rs) throws SQLException {
        List<Weapon> weaponList = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("wname");
            double price = rs.getDouble("wprice");
            weaponList.add(new Weapon(id, name, price));
        }
        return weaponList;
    }
}
