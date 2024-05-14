package lk.ijse.deb.model.tm;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode




public class MembershipFeesTm {
    private String fee_id ;
    private String name;
    private double amount;
    private LocalDate date;
    private String status;




    public Object getPaidDate() {
        return null;
    }
}
