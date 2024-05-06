package lk.ijse.deb.model.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BookTm {
    private String ISBN;
    private String bookName;
    private String category;
    private String qtyOnHand;
    private String authorId;

}
