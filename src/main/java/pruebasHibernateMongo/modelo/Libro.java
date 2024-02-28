package pruebasHibernateMongo.modelo;

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
    @Table(name = "libros", schema = "example_exam_2eva")
    public class Libro {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String titulo;

        @OneToOne(cascade = CascadeType.REMOVE)
        @JoinColumn(name = "estudiante_id", referencedColumnName = "id")
        private Estudiante estudiante;
    }