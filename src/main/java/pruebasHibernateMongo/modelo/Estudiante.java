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
    @Table(name = "estudiantes", schema = "example_exam_2eva")
    public class Estudiante {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String nombre;

    @ManyToMany(mappedBy = "estudiantes")
    private List<Curso> cursos;
    }