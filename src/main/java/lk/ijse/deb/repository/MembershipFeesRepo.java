package lk.ijse.deb.repository;

import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.tm.MembershipFeesTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MembershipFeesRepo {
    public static boolean save(MembershipFeesTm membershipFees) throws SQLException {
        String sql = "INSERT INTO membershipFee VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, membershipFees.getId());
        pstm.setObject(2, membershipFees.getName());
        pstm.setObject(3, membershipFees.getAmount());
        pstm.setObject(4, membershipFees.getStatus());
        pstm.setObject(5, membershipFees.getPaidDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM membershipFee WHERE fee_id  = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}

