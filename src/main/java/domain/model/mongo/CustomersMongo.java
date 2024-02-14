package domain.model.mongo;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomersMongo {
    private ObjectId _id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String date_of_birth;
    private List<OrderMongo> orders;
}

