package lk.ijse.deb.repository;

import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.Author;
import lk.ijse.deb.model.BookSupplier;
import lk.ijse.deb.model.tm.BookSupplierTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSupplierRepo {

    public static List<BookSupplier> getAllBookSupplier() throws SQLException {

        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<BookSupplier> bookSupplierList = new ArrayList<>();

        while (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String supplierName = resultSet.getString(2);
            String contactNumber = resultSet.getString(3);
            String email = resultSet.getString(4);


            BookSupplier bookSupplier = new BookSupplier(supplierId, supplierName, contactNumber, email);
            bookSupplierList.add(bookSupplier);

        }
        return bookSupplierList;
    }

    public static String generateNextSupplierId(String text) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT supplierId FROM supplier ORDER BY supplierId DESC LIMIT 1");

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return splitSupplierId(resultSet.getString(1));

        }
        return splitSupplierId(null);
    }


    private static String splitSupplierId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("SP0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2) {
                return "SP00" + id;
            } else {
                if (length < 3) {
                    return "SP0" + id;
                } else {
                    return "SP" + id;
                }
            }
        }
        return "SP001";
    }

    public static boolean deleteSupplier(String supplierId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE  FROM  supplier WHERE  supplierId=?");
        pstm.setString(1,supplierId);

        boolean isDeleted = pstm.executeUpdate()>0;
        return  isDeleted;
    }

    public static boolean updateSupplier(BookSupplierTm dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE supplier SET supplierName=?,contactNumber=?,email=? WHERE supplierId=?");

        pstm.setString(1, dto.getSupplierName());
        pstm.setString(2, dto.getContactNumber());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getSupplierId());

        boolean isUpdated = pstm.executeUpdate()>0;
        return  isUpdated;
    }

}

