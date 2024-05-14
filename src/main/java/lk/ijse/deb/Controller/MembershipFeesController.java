package lk.ijse.deb.Controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.deb.db.DbConnection;
import lk.ijse.deb.model.MembershipFees;
import lk.ijse.deb.model.tm.MembershipFeesTm;
import lk.ijse.deb.repository.MembershipFeesRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MembershipFeesController {

    @FXML
    private ToggleGroup Status;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colFeeid;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Label lblPaidDate;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private ToggleGroup paidAmount;

    @FXML
    private JFXRadioButton rButtonAmountAnually;

    @FXML
    private JFXRadioButton rButtonAmountSixMonths;

    @FXML
    private JFXRadioButton rButtonAmountmonthly;

    @FXML
    private JFXRadioButton rButtonAnually;

    @FXML
    private JFXRadioButton rButtonMonthly;

    @FXML
    private JFXRadioButton rButtonSixMonths;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<MembershipFeesTm> tblMembershipFee;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashBoardPage.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        setDate();
      //  generateNextMembershipFeeId();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAmount.setText("");
        lblPaidDate.setText("");
        txtStatus.setText("");
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {

            boolean isDeleted = MembershipFeesRepo.delete(id);
            System.out.println(isDeleted);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "MemberFee deleted!").show();
                clearFields();
                setDate();
                setTotalAmount();
                loadAllMembershipFee();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event){
        String id = txtId.getText();
        String name = txtName.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        LocalDate date = LocalDate.parse(lblPaidDate.getText());
        String status = txtStatus.getText();

        MembershipFees membershipFees = new MembershipFees(id, name, amount,date,status);

        try {
            boolean isSaved = MembershipFeesRepo.save(membershipFees);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "membership fees saved!").show();
                clearFields();
                setDate();
                setTotalAmount();
                loadAllMembershipFee();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        LocalDate date = LocalDate.parse(lblPaidDate.getText());
        String status =txtStatus.getText();

        MembershipFees membershipFees = new MembershipFees(id, name, amount,date,status);

        try {
            boolean isUpdated = MembershipFeesRepo.update(membershipFees);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, " updated!").show();
                clearFields();
                setDate();
                setTotalAmount();
                loadAllMembershipFee();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void getAmount(ActionEvent event) {
        if (rButtonAmountmonthly.isSelected()){
            txtAmount.setText(rButtonAmountmonthly.getText());
        } else if (rButtonAmountSixMonths.isSelected()) {
            txtAmount.setText(rButtonAmountSixMonths.getText());
        } else if (rButtonAmountAnually.isSelected()) {
            txtAmount.setText(rButtonAmountAnually.getText());
        }
    }
    @FXML
    void getStatus(ActionEvent event) {
        if (rButtonMonthly.isSelected()){
            txtStatus.setText(rButtonMonthly.getText());
        }else if (rButtonSixMonths.isSelected()){
            txtStatus.setText(rButtonSixMonths.getText());
        } else if (rButtonAnually.isSelected()) {
            txtStatus.setText(rButtonAnually.getText());
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();

        MembershipFees membershipFees = MembershipFeesRepo.searchId(id);
        if (membershipFees != null) {
            txtId.setText(membershipFees.getFee_id());
            txtName.setText(membershipFees.getName());
            txtAmount.setText(String.valueOf(membershipFees.getAmount()));
            lblPaidDate.setText(String.valueOf(membershipFees.getDate()));
            txtStatus.setText(membershipFees.getStatus());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "member fee not found!").show();
        }
    }

    public  void initialize() throws SQLException {
        setDate();
       loadAllMembershipFee();
        setCellValueFactory();
      setTotalAmount();

        //tableListener();

    }


    private void setTotalAmount() {
        try {
            lblTotalAmount.setText(MembershipFeesRepo.getTotalAmount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colFeeid.setCellValueFactory(new PropertyValueFactory<>("fee_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));




    }


    private void loadAllMembershipFee() {
        ObservableList<MembershipFeesTm> obList = FXCollections.observableArrayList();

        try {
            List<MembershipFees> membershipFeesList = MembershipFeesRepo.getAll();
            for (MembershipFees membershipFees : membershipFeesList) {
                MembershipFeesTm tm = new MembershipFeesTm(
                        membershipFees.getFee_id(),
                        membershipFees.getName(),
                        membershipFees.getAmount(),
                        membershipFees.getDate(),
                        membershipFees.getStatus()
                );

                obList.add(tm);
            }

            tblMembershipFee.setItems(obList);
        //    System.out.println(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {

        lblPaidDate.setText(String.valueOf(LocalDate.now()));
    }


}
