package pruebasHibernateMongo.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Entity
    @Table(name = "departamentos", schema = "example_exam_2eva")
    public class Departamento {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "nombre")
        private String nombre;

        @OneToMany(mappedBy = "departamento_id", cascade = CascadeType.PERSIST, orphanRemoval = true)
        private List<Empleado> empleados;

    public Departamento(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}