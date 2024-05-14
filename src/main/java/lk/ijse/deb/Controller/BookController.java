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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.deb.model.Author;
import lk.ijse.deb.model.Book;
import lk.ijse.deb.model.BookRack;
import lk.ijse.deb.model.tm.BookTm;
import lk.ijse.deb.repository.AuthorRepo;
import lk.ijse.deb.repository.BookRackRepo;
import lk.ijse.deb.repository.BookRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookController {

    @FXML
    private JFXComboBox<String> cmbAuthorId;

    @FXML
    private JFXComboBox<String> cmbRackCode;

    @FXML
    private TableColumn<?, ?> colAuthorId;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colISBN;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private Label lblAuthorName;

    @FXML
    private Label lblCategoryType;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BookTm> tblBook;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtQtyOnHand;
    @FXML
    private TableColumn<?, ?> colRackCode;

    public void initialize() {
        setCellValueFactory();
        loadAllBook();
        getAutherId();
        getRackCode();
    }

    private void getRackCode() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = BookRackRepo.getCodes() ;

            for(String id : idList) {
                obList.add(id);
            }

            cmbRackCode.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAutherId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = AuthorRepo.getCodes() ;

            for(String id : idList) {
                obList.add(id);
            }

            cmbAuthorId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllBook() throws RuntimeException {
        ObservableList<BookTm> obList = FXCollections.observableArrayList();

        try {
            List<Book> bookList = BookRepo.getAllBooks();
            for (Book book : bookList) {
                BookTm tm = new BookTm(
                        book.getISBN(),
                        book.getBookName(),
                        book.getCategory(),
                        book.getQtyOnHand(),
                        book.getRackCode(),
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
        colISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colRackCode.setCellValueFactory(new PropertyValueFactory<>("rackCode"));
       colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));



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
    void btnClearOnAction(ActionEvent event) {

        clearFields();
    }

    private void clearFields() {
        txtISBN.setText("");
        txtBookName.setText("");
        txtCategory.setText("");
        txtQtyOnHand.setText("");
   cmbRackCode.setValue(null);
   cmbAuthorId.setValue(null);



    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();

        try {
            boolean isDeleted = BookRepo.delete(ISBN);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Book Deleted!").show();
                clearFields();
                loadAllBook();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        String bookName = txtBookName.getText();
        String category = txtCategory.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String rackCode = cmbRackCode.getValue();
        String authorId = cmbAuthorId.getValue();

        Book book = new Book(ISBN, bookName, category, qtyOnHand, rackCode, authorId);

        try {
            boolean isSaved = BookRepo.save(book);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Book saved!").show();
                clearFields();
                setCellValueFactory();
                loadAllBook();
            }else{
                new Alert(Alert.AlertType.ERROR,"ohh,Book not Saved!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        String bookName = txtBookName.getText();
        String category = txtCategory.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String rackCode = cmbRackCode.getValue();
        String authorId = cmbAuthorId.getValue();

        Book book = new Book(ISBN, bookName, category, qtyOnHand, rackCode, authorId);
        try {
            boolean isUpdated = BookRepo.update(book);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Book Updated!").show();
                clearFields();
                loadAllBook();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbAuthorIdOnAction(ActionEvent event) {
        String code = cmbAuthorId.getValue();

        try {
            Author author = AuthorRepo.searchAuthorId(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @FXML
    void cmbRackCodeOnAction(ActionEvent event) {
        String code = cmbRackCode.getValue();

        try {
            BookRack bookRack = BookRackRepo.searchBookRack(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @FXML
    void txtISBNOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        try {
            Book book = BookRepo.searchBook(ISBN);
            if (book != null) {
                txtISBN.setText(book.getISBN());
                txtBookName.setText(book.getBookName());
                txtCategory.setText(book.getCategory());
                txtQtyOnHand.setText(book.getQtyOnHand());
                cmbRackCode.setValue(book.getRackCode());
                cmbAuthorId.setValue(book.getAuthorId());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Book not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
