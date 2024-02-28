package pruebasHibernateMongo.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MongoDepartamento {
    private ObjectId _id;
    private String nombre;
    private List<MongoEmpleado> empleados;
}
