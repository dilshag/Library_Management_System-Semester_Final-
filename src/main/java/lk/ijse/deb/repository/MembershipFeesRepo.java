package lk.ijse.deb.repository;

import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.MembershipFees;
import lk.ijse.deb.model.tm.BookRackTm;
import lk.ijse.deb.model.tm.MembershipFeesTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MembershipFeesRepo {


    public static boolean save(MembershipFees membershipFees) throws SQLException {
        String sql = "INSERT INTO membershipFee VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1, membershipFees.getFee_id());
        pstm.setObject(2, membershipFees.getName());
        pstm.setObject(3, membershipFees.getAmount());
        pstm.setObject(4, membershipFees.getDate());
        pstm.setObject(5, membershipFees.getStatus());

        boolean isSaved = pstm.executeUpdate() > 0;


        return isSaved;
    }

    public static boolean update(MembershipFees membershipFees) throws SQLException {
        String sql = "UPDATE membershipFee SET name = ?, amount = ? ,date = ?, status = ?  WHERE fee_id =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1, membershipFees.getName());
        pstm.setObject(2, membershipFees.getAmount());
        pstm.setObject(3, membershipFees.getDate());
        pstm.setObject(4, membershipFees.getStatus());
        pstm.setObject(5, membershipFees.getFee_id());


        return pstm.executeUpdate() > 0 ;

    }



    public static boolean delete(String id) throws SQLException {
        String sql ="DELETE FROM membershipFee WHERE fee_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0 ;
    }



    public static List<MembershipFees> getAll() throws SQLException {
        String sql = "SELECT * FROM membershipFee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MembershipFees> memFeeList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            double amount = resultSet.getDouble(3);
            LocalDate date= resultSet.getDate(4).toLocalDate();
            String status =resultSet.getString(5);

            MembershipFees mem = new MembershipFees(id, name, amount,date,status);
            memFeeList.add(mem);
        }
        return memFeeList;
    }

    public static String getTotalAmount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT SUM(amount)  FROM membershipFee");
        ResultSet resultSet = pstm.executeQuery();

        String amount = null;
        if (resultSet.next()){
            amount = resultSet.getString(1);
        }
        return amount;
    }


    public static MembershipFees searchId(String code) throws SQLException {
        String sql = "SELECT * FROM membershipFee WHERE fee_id = ?";

        Connection connection =DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, code);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            double amount= resultSet.getDouble(3);
            LocalDate date= resultSet.getDate(4).toLocalDate();
            String status=resultSet.getString(5);

            MembershipFees mp = new MembershipFees(id,name,amount,date,status);

            return mp;
        }
        else {
            return null;
        }
    }

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT fee_id  FROM membershipFee  ";
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

