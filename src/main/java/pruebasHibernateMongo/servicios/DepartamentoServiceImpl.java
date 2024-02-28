package pruebasHibernateMongo.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebasHibernateMongo.daos.Relacion1N;
import pruebasHibernateMongo.modelo.Departamento;
import java.util.List;

public class DepartamentoServiceImpl {
        private final Relacion1N.DepartamentoDAO departamentoDAO;

        @Inject
        public DepartamentoServiceImpl(Relacion1N.DepartamentoDAO departamentoDAO) {
            this.departamentoDAO = departamentoDAO;
        }

        public Either<ErrorC, List<Departamento>> getAllDepartamentos() {
            return departamentoDAO.getAllDepartamentos();
        }
    }