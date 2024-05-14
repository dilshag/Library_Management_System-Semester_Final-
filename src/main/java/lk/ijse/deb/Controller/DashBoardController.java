package lk.ijse.deb.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {

    public AnchorPane ChildRoot;
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
