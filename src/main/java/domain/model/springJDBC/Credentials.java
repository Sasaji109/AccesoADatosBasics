package domain.model.springJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {
    private int customerId;
    private String username;
    private String password;

    public Credentials() {}
}
