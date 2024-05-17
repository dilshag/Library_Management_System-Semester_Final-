package lk.ijse.deb.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.deb.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static sun.management.MemoryNotifInfoCompositeData.getCount;

public class DashBoardController {

    public AnchorPane ChildRoot;
    @FXML
    public Label lblDate;

    public Label lblTime;
    @FXML
    private int lblAuthorCount;

    @FXML
    private Label lblBookCount;

    @FXML
    private Label lblBorrowCount;

    @FXML
    private int lblMemberCount;

    @FXML
    private Label lblSupplierCount;

    @FXML
    private AnchorPane root;

    public void initialize() {
        setDate();
        setLTime();
       /* try {
            lblMemberCount = String.parseInt(getMemberCount());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setLblMemberCount(String.valueOf(lblMemberCount));
        try {
            lblAuthorCount = Integer.parseInt(getAuthorCount());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setLblAuthorCount(String.valueOf(lblAuthorCount));*/
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }
    private void setLTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

  /*  private void setLblAuthorCount(String authorCount) {

        lblAuthorCount.setText(authorCount);
    }

    private String getAuthorCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS author_count FROM author";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString("author_count");
        }
        return 0;
    }


    private void setLblMemberCount(String memberCount) {
        lblMemberCount.setText(String.valueOf(memberCount));
    }

    private String getMemberCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS member_count FROM member";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString("member_count");
        }
        return 0;
    }*/

    @FXML
    void btnAuthorsOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/author.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);

    }

    @FXML
    void btnBookOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/book.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);
    }


    @FXML
    void btnBookrackOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bookRack.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);

    }

    @FXML
    void btnMemberOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/memberForm.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);
    }

    @FXML
    void btnMembershipFeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/membershipFees.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);

    }

    @FXML
    void btnReservationOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reservation.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bookSupplier.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);
    }
    @FXML
    void btnEmailOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/emailForm.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(root);
    }

}
