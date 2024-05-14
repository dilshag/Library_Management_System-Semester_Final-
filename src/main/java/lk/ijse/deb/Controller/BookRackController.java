package lk.ijse.deb.Controller;

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
import lk.ijse.deb.model.BookRack;
import lk.ijse.deb.model.tm.BookRackTm;
import lk.ijse.deb.repository.BookRackRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookRackController {

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colNameOfBooks;

    @FXML
    private TableColumn<?, ?> colQuantityBooks;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BookRackTm> tblBookrack;

    @FXML
    private TextField txtCategoryOfBooks;

    @FXML
    private TextField txtCode;

    @FXML
    private TextArea txtNameOfBooks;

    @FXML
    private TextField txtQuantity;

    public void initialize() {
        setCellValueFactory();
        loadAllBookRack();
    }


    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("rackCode"));
        colQuantityBooks.setCellValueFactory(new PropertyValueFactory<>("qtyBooks"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryOfBooks"));
        colNameOfBooks.setCellValueFactory(new PropertyValueFactory<>("nameOfBooks"));
    }

    private void loadAllBookRack() {
        ObservableList<BookRackTm> obList = FXCollections.observableArrayList();

        try {
            List<BookRackTm> bookRackList = BookRackRepo.getAll();
            for (BookRackTm bookRack : bookRackList) {
                BookRackTm tm = new BookRackTm(
                        bookRack.getRackCode(),
                        bookRack.getQtyBooks(),
                        bookRack.getCategoryOfBooks(),
                        bookRack.getNameOfBooks()
                );

                obList.add(tm);
            }

            tblBookrack.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();
        String qtyBooks = txtQuantity.getText();
        String categoryOfBooks = txtCategoryOfBooks.getText();
        String nameOfBooks = txtNameOfBooks.getText();


        BookRackTm bookRack = new BookRackTm(rackCode, qtyBooks, categoryOfBooks, nameOfBooks);

        try {
            boolean isSaved = BookRackRepo.save(bookRack);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                loadAllBookRack();
                clearFields();
                loadAllBookRack();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();
        String qtyBooks = txtQuantity.getText();
        String categoryOfBooks = txtCategoryOfBooks.getText();
        String nameOfBooks = txtNameOfBooks.getText();


        BookRack bookRack = new BookRack(rackCode,qtyBooks,categoryOfBooks,nameOfBooks);

        try {
            boolean isUpdated = BookRackRepo.update(bookRack);

            if (isUpdated) {
               // System.out.println(isUpdated);
                new Alert(Alert.AlertType.CONFIRMATION, "updated!").show();
                clearFields();
                loadAllBookRack();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();
    }

    private void clearFields() {
        txtCode.setText("");
        txtNameOfBooks.setText("");
        txtQuantity.setText("");
        txtCategoryOfBooks.setText("");

    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/view/dashBoardPage.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();

        try {
            boolean isDeleted = BookRackRepo.delete(rackCode);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                clearFields();
                loadAllBookRack();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void txtCodeOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();

        try {
            BookRack bookRack = BookRackRepo.searchBookRack(rackCode);
            if (bookRack != null) {
                txtCode.setText(bookRack.getRackCode());
                txtNameOfBooks.setText(bookRack.getNameOfBooks());
                txtQuantity.setText(bookRack.getQtyBooks());
                txtCategoryOfBooks.setText(bookRack.getCategoryOfBooks());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Error..Not found!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}