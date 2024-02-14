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
    private CredentialsMongo credentialsMongo;

    public CustomersMongo(ObjectId _id, String first_name, String last_name, String email, String phone, String date_of_birth, List<OrderMongo> orders) {
        this._id = _id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.date_of_birth = date_of_birth;
        this.orders = orders;
    }
}

