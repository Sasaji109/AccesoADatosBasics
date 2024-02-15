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
    @Table(name = "estudiantesCurso", schema = "example_exam_2eva")
    public class EstudianteCurso {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String nombre;
    }