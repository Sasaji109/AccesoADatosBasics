package domain.model.mongo;

import lombok.*;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CredentialsMongo {
    private ObjectId _id;
    private String user_name;
    private String password;
}
