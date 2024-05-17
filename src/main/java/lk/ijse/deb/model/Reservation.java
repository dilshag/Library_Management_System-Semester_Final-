package lk.ijse.deb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
    private String reservationId;
    private String borrowedDate;
    private  String  dueDate;
    private String bookReturnDate;
    private  String fineStatus;
    private double fineAmount;
    private String mid;
    private  String ISBN;
}
