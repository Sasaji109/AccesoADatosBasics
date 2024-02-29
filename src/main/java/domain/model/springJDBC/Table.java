package domain.model.springJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Table {
    private int id;
    private int numSeats;
}
