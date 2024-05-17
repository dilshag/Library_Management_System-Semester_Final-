package lk.ijse.deb.repository;

import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.BookRack;
import lk.ijse.deb.model.Member;
import lk.ijse.deb.model.tm.BookRackTm;
import lk.ijse.deb.model.tm.MemberTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberRepo {
    public static boolean delete(String mid) throws SQLException {
        String sql = "DELETE FROM member WHERE  mid    = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, mid);

        return pstm.executeUpdate() > 0;

    }

    public static boolean save(Member member) throws SQLException {
        String sql = "INSERT INTO member VALUES(?, ?, ?, ?, ?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,member.getMid());
        pstm.setObject(2,member.getName());
        pstm.setObject(3,member.getAddress());
        pstm.setObject(4,member.getGender());
        pstm.setObject(5,member.getTel());
        pstm.setObject(6,member.getEmailAddress());
        pstm.setObject(7,member.getIDNumber());
        pstm.setObject(8,member.getFeeId());

        return pstm.executeUpdate() > 0;
    }

    public static List<MemberTm> getAllMember() throws SQLException {
        String sql = "SELECT * FROM member";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MemberTm> memberList = new ArrayList<>();

        while (resultSet.next()) {
            String mid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String gender = resultSet.getString(4);
            String tel = resultSet.getString(5);
            String EmailAddress = resultSet.getString(6);
            String IDNumber = resultSet.getString(7);
            String feeId = resultSet.getString(8);

            MemberTm member = new MemberTm(mid,name,address,gender,tel,EmailAddress,IDNumber,feeId);
            memberList.add(member);
        }
        return memberList;
    }

    public static boolean update(Member member) throws SQLException {
        String sql = "UPDATE member SET name=?, address=?, gender=? ,tel =? ,EmailAddress =?,IDNumber=?, feeId=? WHERE  mid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1, member.getName());
        pstm.setObject(2, member.getAddress());
        pstm.setObject(3, member.getGender());
        pstm.setObject(4, member.getTel());
        pstm.setObject(5, member.getEmailAddress());
        pstm.setObject(6, member.getIDNumber());
        pstm.setObject(7, member.getFeeId());
        pstm.setObject(8, member.getMid());

        boolean isUpdated = pstm.executeUpdate() > 0;

        return isUpdated;

    }

    public static Member searchMember(String mId) throws SQLException {
        String sql = "SELECT * FROM member WHERE mid = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, mId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String mid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String gender = resultSet.getString(4);
            String tel = resultSet.getString(5);
            String EmailAddress = resultSet.getString(6);
            String IDNumber = resultSet.getString(7);
            String feeId = resultSet.getString(8);

            Member member= new Member(mid,name,address,gender,tel,EmailAddress,IDNumber,feeId);

            return member;
        }

        return null;
    }


}


  