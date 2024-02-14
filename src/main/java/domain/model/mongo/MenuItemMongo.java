package domain.model.mongo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MenuItemMongo {
    private Integer _id;
    private String name;
    private String description;
    private Double price;
}
