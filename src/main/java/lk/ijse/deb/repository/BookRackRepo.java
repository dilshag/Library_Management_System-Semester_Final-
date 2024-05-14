package lk.ijse.deb.repository;

import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.BookRack;
import lk.ijse.deb.model.tm.BookRackTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRackRepo {
    public static boolean delete(String rackCode) throws SQLException {
        String sql = "DELETE FROM bookRack WHERE rackCode  = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, rackCode);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(BookRackTm bookRack) throws SQLException {
        String sql = "INSERT INTO bookRack VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, bookRack.getRackCode());
        pstm.setObject(2, bookRack.getQtyBooks());
        pstm.setObject(3, bookRack.getCategoryOfBooks());
        pstm.setObject(4, bookRack.getNameOfBooks());
      
        return pstm.executeUpdate() > 0;
    }

    public static List<BookRackTm> getAll() throws SQLException {
        String sql = "SELECT * FROM bookRack";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<BookRackTm> bookRackList = new ArrayList<>();

        while (resultSet.next()) {
            String rackCode = resultSet.getString(1);
            String qtyBooks = resultSet.getString(2);
            String categoryOfBooks = resultSet.getString(3);
            String nameOfBook = resultSet.getString(4);


            BookRackTm bookRack = new BookRackTm(rackCode,qtyBooks,categoryOfBooks,nameOfBook);
            bookRackList.add(bookRack);
        }
        return bookRackList;
    }



    public static boolean update(BookRack bookRack) throws SQLException {
        String sql = "UPDATE bookRack SET qtyBooks = ?, categoryOfBooks  = ? , nameOfBooks = ? WHERE rackCode = ?";
//UPDATE bookRack SET qtyBooks = '20', categoryOfBooks  = 'Fiction' , nameOfBooks = 'Book1,Book2,Book3' WHERE rackCode = 'R001';
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1, bookRack.getQtyBooks());
        pstm.setObject(2, bookRack.getCategoryOfBooks());
        pstm.setObject(3, bookRack.getNameOfBooks());
        pstm.setObject(4, bookRack.getRackCode());

        return pstm.executeUpdate() > 0;
    }

    public static BookRack searchBookRack(String rackCode) throws SQLException {
        String sql = "SELECT * FROM bookRack WHERE rackCode = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, rackCode);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Code = resultSet.getString(1);
            String qtyBooks = resultSet.getString(2);
            String categoryOfBooks = resultSet.getString(3);
            String nameOfBook = resultSet.getString(4);

            BookRack bookRack= new BookRack(Code,qtyBooks,categoryOfBooks,nameOfBook);

            return bookRack;
        }

        return null;
    }

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT rackCode    FROM  bookRack";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }


}
