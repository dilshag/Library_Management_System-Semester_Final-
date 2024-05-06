package lk.ijse.deb.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SupplierTm {
    private String supplierId;
    private String supplierName;
    private String contactNumber;
    private String email;
}
