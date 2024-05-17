package lk.ijse.deb.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.deb.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private JFXButton btnCreateAccount;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane root;

    @FXML
    private Text txtForgetPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String userId = this.txtUserName.getText();
        String pw = this.txtPassword.getText();

        userId = "dilsha";
        pw = "dilsha123";


        try {
            this.checkCredential(userId, pw);
        } catch (SQLException var5) {
            (new Alert(Alert.AlertType.ERROR, var5.getMessage(), new ButtonType[0])).show();
        }
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT username, password FROM Admin WHERE username  = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String dbPw = resultSet.getString("password");
            if (pw.equals(dbPw)) {
                this.navigateToTheDashboard();
            } else {
                (new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!", new ButtonType[0])).show();
            }
        } else {
            (new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!", new ButtonType[0])).show();
        }

    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/view/dashBoardPage.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage)this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void signUpOnAction(ActionEvent event) {

    }

    @FXML
    void txtForgetPasswordAction(MouseEvent event) {

    }

    public void hyperSignUpOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signupPage.fxml"));

        Scene scene = new Scene(rootNode);

        root.getScene().getWindow();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Signup Form");
        primaryStage.show();
    }
}
