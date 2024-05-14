package lk.ijse.deb.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.deb.model.Book;
import lk.ijse.deb.model.MembershipFees;
import lk.ijse.deb.model.Reservation;
import lk.ijse.deb.model.tm.MemberTm;
import lk.ijse.deb.model.tm.ReservationTm;
import lk.ijse.deb.repository.BookRepo;
import lk.ijse.deb.repository.MemberRepo;
import lk.ijse.deb.repository.MembershipFeesRepo;
import lk.ijse.deb.repository.ReservationRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReservationController {

    @FXML
    private JFXComboBox<String> cmbISBN;

    @FXML
    private JFXComboBox<String> cmbMemberId;

    @FXML
    private TableColumn<?, ?> colBookIsbn;

    @FXML
    private TableColumn<?, ?> colBookReturnDate;

    @FXML
    private TableColumn<?, ?> colBorrowedDate;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colFineAmount;

    @FXML
    private TableColumn<?, ?> colFineStatus;

    @FXML
    private TableColumn<?, ?> colMemberId;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblMemberName;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblQtyOnHand1;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ReservationTm> tblReservation;

    @FXML
    private TextField txtBorrowedDate;

    @FXML
    private DatePicker txtDueDate;

    @FXML
    private TextField txtFineAmount;

    @FXML
    private TextField txtFineStatus;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtReturnDate;


    public void initialize() {
        generateNextReservationId();
        setCellValueFactory();
        loadAllReservation();
        loadMemberIds();

        loadBookISBN();
        setDate();

    }

    private void setDate() {
        txtBorrowedDate.setText(String.valueOf(LocalDate.now()));
    }



    private void loadBookISBN() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<Book> ISBNlist = BookRepo.getAllBooks();

            for (Book dto : ISBNlist){
                obList.add(dto.getISBN());
            }
            cmbISBN.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setData(ReservationTm row) {
        txtReservationId.setText(row.getReservationId());
        txtBorrowedDate.setText(row.getBorrowedDate());
        txtDueDate.setValue(LocalDate.parse(row.getDueDate()));
        txtReturnDate.setText(row.getBookReturnDate());
        txtFineStatus.setText(row.getFineStatus());
        txtFineAmount.setText(String.valueOf(row.getFineAmount()));
        cmbMemberId.setValue(row.getMid());
        cmbISBN.setValue(row.getISBN());
    }


    private void loadMemberIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<MemberTm> mIdList = MemberRepo.getAllMember();

            for(MemberTm dto : mIdList){
                obList.add(dto.getMid());
            }
            cmbMemberId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void generateNextReservationId() {
        try {
            String reservationId = ReservationRepo.generateNextReservationId(txtReservationId.getText());
            txtReservationId.setText(reservationId);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void loadAllReservation() {
        ObservableList<ReservationTm> obList = FXCollections.observableArrayList();

        try {
            List<ReservationTm> reservationList = ReservationRepo.getAllReservation();
            for (ReservationTm reservation : reservationList) {
                ReservationTm tm = new ReservationTm(
                        reservation.getReservationId(),
                        reservation.getBorrowedDate(),
                        reservation.getDueDate(),
                        reservation.getBookReturnDate(),
                        reservation.getFineStatus(),
                        reservation.getFineAmount(),
                        reservation.getMid(),
                        reservation.getISBN()

                );

                obList.add(tm);
            }

            tblReservation.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colBookReturnDate.setCellValueFactory(new PropertyValueFactory<>("bookReturnDate"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatus"));
        colFineAmount.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colBookIsbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

    }

    @FXML
    void btnAddReservationOnAction(ActionEvent event) {
        String reservationId = txtReservationId.getText();
        String borrowedDate = txtBorrowedDate.getText();
        String dueDate = String.valueOf(txtDueDate.getValue());
        String bookReturnDate = txtReturnDate.getText();
        String fineStatus = txtFineStatus.getText();
        double fineAmount = Double.parseDouble(txtFineAmount.getText());
        String mid = cmbMemberId.getValue();
        String ISBN = cmbISBN.getValue();


        ReservationTm reservation = new ReservationTm(reservationId, borrowedDate, dueDate, bookReturnDate, fineStatus, fineAmount, mid, ISBN);

        try {
            boolean isAdd = ReservationRepo.addReservation(reservation);
            if (isAdd) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reservation Added Successfully!!!").show();
                clearFields();
                loadAllReservation();
                setCellValueFactory();

            } else {
                new Alert(Alert.AlertType.ERROR, "Reservation failed!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtReservationId.setText("");
        txtBorrowedDate.setText("");
        txtDueDate.setAccessibleText("");
        txtReturnDate.setText("");
        txtFineStatus.setText("");
        txtFineAmount.setText("");
        cmbMemberId.setValue("");
        cmbISBN.setValue("");
        lblMemberName.setText("");
        lblBookName.setText("");
        lblQtyOnHand.setText("");
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

    @FXML
    void btnDeleteReservationOnAction(ActionEvent event) {
        String reservationId = txtReservationId.getText();

        try {
            boolean isDeleted = ReservationRepo.deleteReservation(reservationId);
            if (isDeleted) {

                new Alert(Alert.AlertType.INFORMATION, "reservation deleted successfully!!").showAndWait();
                loadAllReservation();
            } else {
                new Alert(Alert.AlertType.ERROR, "reservation not deleted!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnNewBookOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/book.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Book Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnNewMemberOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/memberForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Member Form");
        stage.centerOnScreen();
        stage.show();
        loadMemberIds();

    }

    @FXML
    void btnUpdateReservationOnAction(ActionEvent event) {
        String reservationId = txtReservationId.getText();
        String borrowedDate = txtBorrowedDate.getText();
        String dueDate = String.valueOf(txtDueDate.getValue());
        String bookReturnDate = txtReturnDate.getText();
        String fineStatus = txtFineStatus.getText();
        double fineAmount = Double.parseDouble(txtFineAmount.getText());
        String mid = cmbMemberId.getValue();
        String ISBN = cmbISBN.getValue();

        Reservation reservation = new Reservation(reservationId, borrowedDate, dueDate, bookReturnDate, fineStatus, fineAmount, mid, ISBN);

        try {
            boolean isUpdated = ReservationRepo.updateReservation(reservation);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Reservation successfully Updated!!!").show();
                loadAllReservation();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Updated !!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbBookOnAction(ActionEvent event) {

        String ISBN = cmbISBN.getValue();
        if(ISBN!=null){
            try {
                Book dto =BookRepo.searchBook(ISBN);
                if(dto!=null){
                    //System.out.println("dto : "+dto);
                    lblBookName.setText(dto.getBookName());
                    lblQtyOnHand.setText(dto.getQtyOnHand());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void cmbMemberOnAction(ActionEvent event) {
        String mid = String.valueOf(cmbMemberId.getValue());
        try {
            MemberTm dto = MemberRepo.searchMember(mid);
            if(dto!=null)           lblMemberName.setText(dto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtReservationIdOnAction(ActionEvent event) {
        String reservationId = txtReservationId.getText();

        try {
            Reservation reservation = ReservationRepo.searchReservation(reservationId);
            if (reservation != null) {
                txtReservationId.setText(reservation.getReservationId());
                txtBorrowedDate.setText(reservation.getBorrowedDate());
                txtDueDate.setValue(LocalDate.parse(reservation.getDueDate()));
                txtReturnDate.setText(reservation.getBookReturnDate());
                txtFineStatus.setText(reservation.getFineStatus());
                txtFineAmount.setText(String.valueOf(reservation.getFineAmount()));
                cmbMemberId.setValue(reservation.getMid());
                cmbISBN.setValue(reservation.getISBN());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Cannot Found Reservation!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}

