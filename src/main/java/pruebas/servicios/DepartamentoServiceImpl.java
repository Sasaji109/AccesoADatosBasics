package pruebas.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebas.daos.Relacion1N;
import pruebas.modelo.Departamento;
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