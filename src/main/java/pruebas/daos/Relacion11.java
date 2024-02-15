package pruebas.daos;

import common.Constants;
import common.configuration.JPAUtil;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import pruebas.modelo.Estudiante;
import pruebas.modelo.Libro;
import java.time.LocalDate;
import java.util.List;

public class Relacion11 {

    public interface EstudianteDAO {
        Either<ErrorC, List<Estudiante>> getAllEstudiantes();
    }

    public interface LibroDAO {
        Either<ErrorC, List<Libro>> getAllLibros();
    }

    public static class EstudianteDAOImpl implements EstudianteDAO {
        private final JPAUtil jpaUtil;
        private EntityManager em;

        @Inject
        public EstudianteDAOImpl(JPAUtil jpaUtil) {
            this.jpaUtil = jpaUtil;
        }

        @Override
        public Either<ErrorC, List<Estudiante>> getAllEstudiantes() {
            Either<ErrorC, List<Estudiante>> either;

            List<Estudiante> estudiantes;
            em = jpaUtil.getEntityManager();

            try {
                estudiantes = em.createQuery("FROM Estudiante", Estudiante.class).getResultList();
                either = Either.right(estudiantes);
            } catch (Exception e) {
                either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
            }
            return either;
        }
    }

    public static class LibroDAOImpl implements LibroDAO {
        private final JPAUtil jpaUtil;
        private EntityManager em;

        @Inject
        public LibroDAOImpl(JPAUtil jpaUtil) {
            this.jpaUtil = jpaUtil;
        }

        @Override
        public Either<ErrorC, List<Libro>> getAllLibros() {
            Either<ErrorC, List<Libro>> either;

            List<Libro> libros;
            em = jpaUtil.getEntityManager();

            try {
                libros = em.createQuery("FROM Libro", Libro.class).getResultList();
                either = Either.right(libros);
            } catch (Exception e) {
                either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
            }
            return either;
        }
    }

}
