package pruebas.daos;

import common.Constants;
import common.configuration.JPAUtil;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import pruebas.modelo.Curso;
import pruebas.modelo.EstudianteCurso;
import java.time.LocalDate;
import java.util.List;

public class RelacionNN {

    public interface CursoDAO {
        Either<ErrorC, List<Curso>> getAllCursos();
    }

    public interface EstudianteCursoDAO {
        Either<ErrorC, List<EstudianteCurso>> getAllEstudiantesCursos();
    }

    public static class CursoDAOImpl implements CursoDAO {
        private final JPAUtil jpaUtil;
        private EntityManager em;

        @Inject
        public CursoDAOImpl(JPAUtil jpaUtil) {
            this.jpaUtil = jpaUtil;
        }

        @Override
        public Either<ErrorC, List<Curso>> getAllCursos() {
            Either<ErrorC, List<Curso>> either;

            List<Curso> cursos;
            em = jpaUtil.getEntityManager();

            try {
                cursos = em.createQuery("FROM Curso", Curso.class).getResultList();
                either = Either.right(cursos);
            } catch (Exception e) {
                either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
            }
            return either;
        }
    }

    public static class EstudianteCursoDAOImpl implements EstudianteCursoDAO {
        private final JPAUtil jpaUtil;
        private EntityManager em;

        @Inject
        public EstudianteCursoDAOImpl(JPAUtil jpaUtil) {
            this.jpaUtil = jpaUtil;
        }

        @Override
        public Either<ErrorC, List<EstudianteCurso>> getAllEstudiantesCursos() {
            Either<ErrorC, List<EstudianteCurso>> either;

            List<EstudianteCurso> estudiantesCursos;
            em = jpaUtil.getEntityManager();

            try {
                estudiantesCursos = em.createQuery("FROM EstudianteCurso", EstudianteCurso.class).getResultList();
                either = Either.right(estudiantesCursos);
            } catch (Exception e) {
                either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
            }
            return either;
        }
    }

}
