package pruebas.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebas.daos.Relacion11;
import pruebas.modelo.Estudiante;
import java.util.List;

public class EstudianteServiceImpl {
        private final Relacion11.EstudianteDAO estudianteDAO;

        @Inject
        public EstudianteServiceImpl(Relacion11.EstudianteDAO estudianteDAO) {
            this.estudianteDAO = estudianteDAO;
        }

        public Either<ErrorC, List<Estudiante>> getAllEstudiantes() {
            return estudianteDAO.getAllEstudiantes();
        }
    }