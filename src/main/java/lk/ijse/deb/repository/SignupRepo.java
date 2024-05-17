package lk.ijse.deb.repository;

import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.Signup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupRepo {
    public static boolean register(Signup signup) throws SQLException {

        String sql = "INSERT INTO Admin VALUES(?, ?, ?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, signup.getType());
        pstm.setObject(2, signup.getFirstName());
        pstm.setObject(3, signup.getLastName());
        pstm.setObject(4, signup.getNic());
        pstm.setObject(5, signup.getEmail());
        pstm.setObject(6, signup.getPhonenumber());
        pstm.setObject(7, signup.getUsername());
        pstm.setObject(8, signup.getPassword());


        return pstm.executeUpdate() > 0;
    }

}
