package pruebas.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebas.daos.RelacionNN;
import pruebas.modelo.Curso;
import java.util.List;

public class CursoServiceImpl {
        private final RelacionNN.CursoDAO cursoDAO;

        @Inject
        public CursoServiceImpl(RelacionNN.CursoDAO cursoDAO) {
            this.cursoDAO = cursoDAO;
        }
        
        public Either<ErrorC, List<Curso>> getAllCursos() {
            return cursoDAO.getAllCursos();
        }
    }