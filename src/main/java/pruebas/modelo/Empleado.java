package pruebas.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Entity
    @Table(name = "empleados", schema = "example_exam_2eva")
    public class Empleado {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "nombre")
        private String nombre;

        @Column(name = "departamento_id")
        private Integer departamento_id;

    public Empleado(Integer id, String nombre, Integer departamento_id) {
        this.id = id;
        this.nombre = nombre;
        this.departamento_id = departamento_id;
    }
}