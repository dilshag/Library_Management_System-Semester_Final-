package lk.ijse.deb.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.deb.model.Author;
import lk.ijse.deb.model.Book;
import lk.ijse.deb.model.tm.AuthorTm;
import lk.ijse.deb.model.tm.BookTm;
import lk.ijse.deb.repository.AuthorRepo;
import lk.ijse.deb.repository.BookRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AuthorController {

    @FXML
    private TableColumn<?, ?> colAuthorId;

    @FXML
    private TableColumn<?, ?> colAuthorName;

    @FXML
    private TableColumn<?, ?> colCurrentlyBooksWrittenQty;

    @FXML
    private TableColumn<?, ?> colNationality;

    @FXML
    private TableColumn<?, ?> colText;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<AuthorTm> tblAuthor;

    @FXML
    private TextField txtAuthorId;

    @FXML
    private TextField txtAuthorName;

    @FXML
    private TextField txtCurrentlyBooksWrittenQty;

    @FXML
    private TextField txtNationality;

    @FXML
    private TextField txtText;
    public void initialize() {
        setCellValueFactory();
        loadAllAuthor();
    }

    private void loadAllAuthor() {
        ObservableList<AuthorTm> obList = FXCollections.observableArrayList();

        try {
            List<Author> authorList = AuthorRepo.getAllAuthor();
            for (Author author: authorList) {
                AuthorTm tm = new AuthorTm(
                        author.getAuthorId(),
                        author.getAuthorName(),
                        author.getText(),
                        author.getNationality(),
                        author.getCurrentlyBooksWrittenQty()
                );

                obList.add(tm);
            }

            tblAuthor.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

            colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
            colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colText.setCellValueFactory(new PropertyValueFactory<>("text"));
            colNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
            colCurrentlyBooksWrittenQty.setCellValueFactory(new PropertyValueFactory<>("currentlyBooksWrittenQty"));



        }



    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();
    }

    private void clearFields() {
        txtAuthorId.setText("");
        txtAuthorName.setText("");
        txtText.setText("");
        txtNationality.setText("");
        txtCurrentlyBooksWrittenQty.setText("");


    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtAuthorId.getText();

        try {
            boolean isDeleted = AuthorRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText();
        String authorName = txtAuthorName.getText();
        String text = txtText.getText();
        String nationality  = txtNationality.getText();
        int currentlyBookWrittenQyt= Integer.parseInt(txtCurrentlyBooksWrittenQty.getText());


        Author author = new Author(authorId, authorName,text,nationality,currentlyBookWrittenQyt);

        try {
            boolean isSaved = AuthorRepo.save(author);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                loadAllAuthor();
                clearFields();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        System.out.println("RUN");
        String authorId = txtAuthorId.getText();
        String authorName = txtAuthorName.getText();
        String text =txtText.getText();
        String nationality =txtNationality.getText();
        int bookWrittenQty= Integer.parseInt(txtCurrentlyBooksWrittenQty.getText());

        Author author = new Author(authorId, authorName,text,nationality,bookWrittenQty);

        try {
            boolean isUpdated = AuthorRepo.update(author);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "author updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void txtAuthorIdOnAction/* author Id search*/(ActionEvent event) throws SQLException {

        String authorId = txtAuthorId.getText();

        Author author = AuthorRepo.searchAuthorId(authorId);
        if (author != null) {
            txtAuthorId.setText(author.getAuthorId());
            txtAuthorName.setText(author.getAuthorName());
            txtText.setText(author.getText());
            txtNationality.setText(author.getNationality());
            txtCurrentlyBooksWrittenQty.setText(String.valueOf(author.getCurrentlyBooksWrittenQty()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
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

}
