package pruebas.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebas.daos.RelacionNN;
import pruebas.modelo.EstudianteCurso;
import java.util.List;

public class EstudianteCursoServiceImpl {
        private final RelacionNN.EstudianteCursoDAO estudianteCursoDAO;

        @Inject
        public EstudianteCursoServiceImpl(RelacionNN.EstudianteCursoDAO estudianteCursoDAO) {
            this.estudianteCursoDAO = estudianteCursoDAO;
        }
        
        public Either<ErrorC, List<EstudianteCurso>> getAllEstudiantesCursos() {
            return estudianteCursoDAO.getAllEstudiantesCursos();
        }
    }

