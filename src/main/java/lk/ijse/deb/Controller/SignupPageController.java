package lk.ijse.deb.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.deb.model.Signup;
import lk.ijse.deb.repository.SignupRepo;

import java.io.IOException;
import java.sql.SQLException;

public class SignupPageController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) {
        String Type =txtType.getText();
        String firstName =  txtFirstname.getText();
        String lastName = txtLastname.getText();
        String nic = txtNIC.getText();
        String email = txtEmailAddress.getText();
        String phonenumber = txtPhoneNumber.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        Signup signup = new Signup(Type,firstName,lastName,nic,email,phonenumber,username,password);
        try {
            boolean isSaved = SignupRepo.register(signup);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"User Not Saved...").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void hyperLoginHereOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));

        Scene scene = new Scene(rootNode);

        root.getScene().getWindow();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");

    }

}
