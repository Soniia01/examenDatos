package dao.imp;

import common.Configuration;
import common.DBConnection;
import dao.DaoSale;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Sale;
import model.error.Error;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoSaleImp implements DaoSale {

    private final Configuration config;
    private final DBConnection db;

    @Inject
    public DaoSaleImp(Configuration config, DBConnection db) {
        this.config = config;
        this.db = db;
    }

    @Override
    public Either<Error, List<Sale>> getAll() {
        List<Sale> saleList;
        Either<Error, List<Sale>> res;
        try (Connection connection = db.getConnection()) {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from sales");
            saleList = readRS(rs);
            res = Either.right(saleList);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Sale> get(int id) {
        List<Sale> saleList;
        Either<Error, Sale> res;
        try (Connection connection = db.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("select * from sales where id =?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            saleList = readRS(rs);
            if (!saleList.isEmpty()) {
                res = Either.right(saleList.get(0));
            } else {
                res = Either.left(new Error("There's no spies with that id", 0, LocalDateTime.now()));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Integer> add(Sale sale) {
        int rowsAffected;
        Either<Error, Integer> res;
        try (Connection connection = db.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("insert into sales (units, id_weapons_faction, sldate) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, sale.getUnits());
            pstmt.setInt(2, sale.getIdWeaponsFaction());
            pstmt.setDate(3, Date.valueOf(sale.getDate()));
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected != 1) {
                connection.rollback();
                return Either.left(new Error("There was an error adding the new spy", 0, LocalDateTime.now()));
            }
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int generatedSpyId;
            if (generatedKeys.next()) {
                generatedSpyId = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return Either.left(new Error("There was an error obtaining the id", 0, LocalDateTime.now()));
            }
            pstmt.setInt(1, generatedSpyId);
            connection.commit();
            res = Either.right(rowsAffected);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            res = Either.left(new Error("There was an error", 0, LocalDateTime.now()));
        }
        return res;
    }

    @Override
    public Either<Error, Integer> update(Sale sale) {
        return null;
    }

    @Override
    public Either<Error, Integer> delete(int id) {
        return null;
    }

    private List<Sale> readRS(ResultSet rs) throws SQLException {
        List<Sale> weaponList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int units = rs.getInt("units");
            int idWeaponsFaction = rs.getInt("id_weapons_faction");
            LocalDate date = rs.getDate("sldate").toLocalDate();
            weaponList.add(new Sale(id, units, idWeaponsFaction, date));
        }
        return weaponList;
    }

}
