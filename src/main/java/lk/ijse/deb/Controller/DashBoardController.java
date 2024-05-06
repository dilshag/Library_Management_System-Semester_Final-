package lk.ijse.deb.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {

    @FXML
    private Label lblAuthorCount;

    @FXML
    private Label lblBookCount;

    @FXML
    private Label lblBorrowCount;

    @FXML
    private Label lblMemberCount;

    @FXML
    private Label lblSupplierCount;

    @FXML
    private AnchorPane root;

    @FXML
    void btnAuthorsOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/view/author.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage)this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("BookAuthers Form");

    }

    @FXML
    void btnBookOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/book.fxml"));
        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Book Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnBookrackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/bookRack.fxml"));
        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Book Rack Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnMemberOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/memberForm.fxml"));
        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Member Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnMembershipFeeOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/view/membershipFees.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage)this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MembershipFees Form");

    }

    @FXML
    void btnReservationOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/reservation.fxml"));
        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Reservation Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/bookSupplier.fxml"));
        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Book Rack Form");
        stage.centerOnScreen();
    }

}
