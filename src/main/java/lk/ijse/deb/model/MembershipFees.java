package lk.ijse.deb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembershipFees {
    private String fee_id ;
    private String name;
    private double amount;
    private LocalDate date;
    private String status;
}
