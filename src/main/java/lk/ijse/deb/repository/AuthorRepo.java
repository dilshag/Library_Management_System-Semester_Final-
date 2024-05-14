package lk.ijse.deb.repository;

import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.Author;
import lk.ijse.deb.model.tm.AuthorTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepo {

    public static List<Author> getAllAuthor() throws SQLException {
        String sql = "SELECT * FROM author";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Author> authorList = new ArrayList<>();

        while (resultSet.next()) {
            String authorId = resultSet.getString(1);
            String authorName = resultSet.getString(2);
            String text = resultSet.getString(3);
            String nationality = resultSet.getString(4);
            int currentlyBooksWrittenQty= resultSet.getInt(5);


            Author author = new Author(authorId,authorName,text,nationality,currentlyBooksWrittenQty);
            authorList.add(author);

        }
        return authorList;
    }

    public static boolean delete(String authorId) throws SQLException {
        String sql = "DELETE FROM author WHERE authorId  = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, authorId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Author author) throws SQLException {
        String sql = "INSERT INTO author VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, author.getAuthorId());
        pstm.setObject(2, author.getAuthorName());
        pstm.setObject(3, author.getText());
        pstm.setObject(4, author.getNationality());
        pstm.setObject(5, author.getCurrentlyBooksWrittenQty());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Author author) throws SQLException {
        String sql = "UPDATE author SET authorName   = ?, text = ?, nationality   = ?, currentlyBooksWrittenQty = ? WHERE authorId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, author.getAuthorName());
        pstm.setObject(2, author.getText());
        pstm.setObject(3, author.getNationality());
        pstm.setObject(4, author.getCurrentlyBooksWrittenQty());
        pstm.setObject(5, author.getAuthorId());

        return pstm.executeUpdate() > 0;
    }

    public static Author searchAuthorId(String authorid) throws SQLException {
        String sql = "SELECT * FROM author WHERE authorId  = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, authorid);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String authorId = resultSet.getString(1);
            String authorName = resultSet.getString(2);
            String text = resultSet.getString(3);
            String nationality = resultSet.getString(4);
            int currentlyBooksWrittenQty = Integer.parseInt(resultSet.getString(5));

            Author author = new Author(authorId, authorName, text, nationality, currentlyBooksWrittenQty);

            return author;
        }

        return null;
    }

    public static AuthorTm searchAuthor(String id) {

        return null;
    }




    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT authorId     FROM author  ";
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
