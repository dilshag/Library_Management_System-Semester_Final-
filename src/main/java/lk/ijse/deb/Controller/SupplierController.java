package lk.ijse.deb.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.deb.model.Book;
import lk.ijse.deb.model.Supplier;
import lk.ijse.deb.model.tm.BookTm;
import lk.ijse.deb.model.tm.SupplierTm;
import lk.ijse.deb.repository.BookRepo;
import lk.ijse.deb.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierController {

    @FXML
    private JFXComboBox<?> cmbBookISBN;

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

    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
    }

    private void loadAllSupplier() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAllSupplier();
            for (Supplier supplier : bookList) {
                BookTm tm = new BookTm(
                        book.getISBN(),
                        book.getBookName(),
                        book.getCategory(),
                        book.getQtyOnHand(),
                        book.getAuthorId()
                );

                obList.add(tm);
            }

            tblBook.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        
    }

    @FXML
    void PlaceBooksOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddSupplierCartOnAction(ActionEvent event) {

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

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewBookOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

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

}
