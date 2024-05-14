package lk.ijse.deb.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.deb.model.Book;
import lk.ijse.deb.model.tm.BookSupplierTm;
import lk.ijse.deb.repository.BookRepo;
import lk.ijse.deb.repository.BookSupplierRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookSupplierController {

    @FXML
    private JFXComboBox<String> cmbBookISBN;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colBookISBN;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblBookName1;

    @FXML
    private Label lblSupplierDate;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblSupplierDetail;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplyQuantity;
    @FXML
    private Label lblQtyOnHand;

    @FXML
    void PlaceBooksOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddSupplierCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    private void clearAllFields() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtContactNumber.setText("");
        txtEmail.setText("");
        lblBookName.setText("");
        lblQtyOnHand.setText("");
        cmbBookISBN.setValue("");
        txtSupplyQuantity.setText("");

    }

    private void clearFields() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtContactNumber.setText("");
        txtEmail.setText("");
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        try {
            boolean isDeleted = BookSupplierRepo.deleteSupplier(supplierId);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted Successfully!!!").show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"supplier not deleted!!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btnNewBookOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();

        var dto = new BookSupplierTm(supplierId,supplierName,contactNumber,email);

        try {
            boolean isUpdated = BookSupplierRepo.updateSupplier(dto);

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"supplier updated successfully!!!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"supplier not updated!!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbBookOnAction(ActionEvent event) {

    }

    @FXML
    void txtSupplierIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtSuppliyQuantityOnAction(ActionEvent event) {

    }
    public  void initialize() {
        generateNextSupplierId();
       loadAllBookISBNs();
        setDate();
        setCellValueFactory();

    }

    private void setCellValueFactory() {

        colBookISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void setDate() {
        lblSupplierDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadAllBookISBNs() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<Book> ISBNList= BookRepo.getAllBooks();

            for(Book dto : ISBNList){
                obList.add(dto.getISBN());
            }
            cmbBookISBN.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextSupplierId() {
        try {
            String supplierId = BookSupplierRepo.generateNextSupplierId(txtSupplierId.getText());
            txtSupplierId.setText(supplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();
        generateNextSupplierId();
    }


}
