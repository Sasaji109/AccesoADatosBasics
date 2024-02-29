package domain.model.springJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateBirth;
    private Credentials credentials;

    public Customer() {}
}

