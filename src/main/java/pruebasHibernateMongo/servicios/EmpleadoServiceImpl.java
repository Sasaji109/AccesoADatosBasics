package pruebasHibernateMongo.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebasHibernateMongo.daos.Relacion1N;
import pruebasHibernateMongo.modelo.Empleado;
import java.util.List;

public class EmpleadoServiceImpl {
        private final Relacion1N.EmpleadoDAO empleadoDAO;

        @Inject
        public EmpleadoServiceImpl(Relacion1N.EmpleadoDAO empleadoDAO) {
            this.empleadoDAO = empleadoDAO;
        }

        public Either<ErrorC, List<Empleado>> getAllEmpleados() {
            return empleadoDAO.getAllEmpleados();
        }
    }