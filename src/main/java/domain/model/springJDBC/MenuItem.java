package domain.model.springJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItem {
    private Integer id;
    private String name;
    private String description;
    private Double price;

    public MenuItem() {}
}
