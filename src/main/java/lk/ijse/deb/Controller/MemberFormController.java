package lk.ijse.deb.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.deb.Util.Regex;
import lk.ijse.deb.model.Member;
import lk.ijse.deb.model.MembershipFees;
import lk.ijse.deb.model.tm.MemberTm;
import lk.ijse.deb.model.tm.MembershipFeesTm;
import lk.ijse.deb.repository.MemberRepo;
import lk.ijse.deb.repository.MembershipFeesRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MemberFormController {

    @FXML
    private JFXComboBox<String> cmbmembershipFeeId;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmailAddress;

    @FXML
    private TableColumn<?, ?> colFeeId;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colIDNumber;

    @FXML
    private TableColumn<?, ?> colMembername;

    @FXML
    private TableColumn<?, ?> colMid;

    @FXML
    private TableColumn<?, ?> coltelNumber;

    @FXML
    private Label lblPaidDate;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<MemberTm> tblMember;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtIDNumber;

    @FXML
    private TextField txtMid;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    public  void initialize(){
        setCellValueFactory();
        loadAllMember();
        getMemberFeesIds();
        setDate();

    }

    private void setDate() {

        lblPaidDate.setText(String.valueOf(LocalDate.now()));
    }

    private void getMemberFeesIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = MembershipFeesRepo.getCodes() ;

            for(String id : idList) {
                obList.add(id);
            }

            cmbmembershipFeeId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllMember() {
        ObservableList<MemberTm> obList = FXCollections.observableArrayList();

        try {
            List<MemberTm> memberList = MemberRepo.getAllMember();
            for (MemberTm member : memberList) {
                MemberTm tm = new MemberTm(
                        member.getMid(),
                        member.getName(),
                        member.getAddress(),
                        member.getGender(),
                        member.getTel(),
                        member.getEmailAddress(),
                        member.getIDNumber(),
                        member.getFeeId()
                );

                obList.add(tm);
            }

            tblMember.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colMid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colMembername.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        coltelNumber.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("EmailAddress"));
        colIDNumber.setCellValueFactory(new PropertyValueFactory<>("IDNumber"));
        colFeeId.setCellValueFactory(new PropertyValueFactory<>("feeId"));
    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/view/dashBoardPage.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage)this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();
    }

    private void clearFields() {
        txtMid.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtGender.setText("");
        txtTel.setText("");
        txtEmailAddress.setText("");
        txtIDNumber.setText("");
        cmbmembershipFeeId.setValue(null);
        
                
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String mid = txtMid.getText();

        try {
            boolean isDeleted = MemberRepo.delete(mid);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, " deleted!").show();
                clearFields();
                loadAllMember();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnNewMembershipFeeOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/view/membershipFees.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage)this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("membbership Form");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String mid = txtMid.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String gender = txtGender.getText();
        String tel = txtTel.getText();
        String EmailAddress = txtEmailAddress.getText();
        String IDNumber = txtIDNumber.getText();
        String feeId = cmbmembershipFeeId.getValue();

        Member member = new Member(mid, name, address, gender, tel, EmailAddress, IDNumber, feeId);
        if (isValied()) {
            try {
                boolean isSaved = MemberRepo.save(member);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Member saved!").show();
                    clearFields();
                    setCellValueFactory();
                    loadAllMember();
                } else {
                    new Alert(Alert.AlertType.ERROR, "ohh,Member not Saved!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

            }
        } else {
            // Show error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String mid = txtMid.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String gender = txtGender.getText();
        String tel = txtTel.getText();
        String EmailAddress = txtEmailAddress.getText();
        String IDNumber = txtIDNumber.getText();
        String feeId = cmbmembershipFeeId.getValue();


        Member member = new Member(mid, name, address, gender, tel, EmailAddress, IDNumber, feeId);

        if (isValied()) {
            try {
                boolean isUpdated = MemberRepo.update(member);

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Member Updated Successfully!!!").show();
                    clearFields();
                    loadAllMember();
                } else {
                    new Alert(Alert.AlertType.ERROR, "not updated!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        } else {
            // Show error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }
    }
    @FXML
    void cmbMembershipFeeOnAction(ActionEvent event) {
        String code = cmbmembershipFeeId.getValue();
        try {
            MembershipFees membershipFees = MembershipFeesRepo.searchId(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }


    @FXML
    void txtSearchOnAction(ActionEvent event) {

            String mid = txtMid.getText();

            try {
                Member member =MemberRepo.searchMember(mid);
                if (member != null){
                    txtMid.setText(member.getMid());
                    txtName.setText(member.getName());
                    txtAddress.setText(member.getAddress());
                    txtGender.setText(member.getGender());
                    txtTel.setText(member.getTel());
                    txtEmailAddress.setText(member.getEmailAddress());
                    txtIDNumber.setText(member.getIDNumber());
                    cmbmembershipFeeId.setValue(member.getFeeId());

                }else {
                    new Alert(Alert.AlertType.ERROR,"Member not found!!!").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.deb.Util.TextField.NAME, txtName);
    }

    public void txtEmailAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.deb.Util.TextField.EMAIL,txtEmailAddress );
    }

    public void txtMidOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.deb.Util.TextField.MID,txtMid);
    }

    public void txtTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.deb.Util.TextField.CONTACT, txtTel);
    }

    public void txtIDNumberOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.deb.Util.TextField.NIC, txtIDNumber);
    }

    public boolean isValied(){
        boolean mnameValid = Regex.setTextColor(lk.ijse.deb.Util.TextField.NAME,txtName);
        boolean memailValid = Regex.setTextColor(lk.ijse.deb.Util.TextField.EMAIL,txtEmailAddress);
        boolean midValid = Regex.setTextColor(lk.ijse.deb.Util.TextField.MID, txtMid);
        boolean mtelValid = Regex.setTextColor(lk.ijse.deb.Util.TextField.CONTACT, txtTel);
        boolean mnicValid = Regex.setTextColor(lk.ijse.deb.Util.TextField.NIC,txtIDNumber);

        return mnameValid && memailValid && midValid && mtelValid && mnicValid;
    }
}
