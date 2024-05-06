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
    private String id;
    private String name;
    private String status;
    private double amount;
    private LocalDate date;


    public Object getPaidDate() {
        return null;
    }
}
