package pruebasHibernateMongo.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebasHibernateMongo.daos.Relacion11;
import pruebasHibernateMongo.modelo.Estudiante;
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