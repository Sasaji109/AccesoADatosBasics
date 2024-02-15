package pruebas.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebas.daos.Relacion1N;
import pruebas.modelo.MongoDepartamento;
import java.util.List;

public class MongoDepartamentoServiceImpl {
        private final Relacion1N.DepartamentoMongoDAO departamentoDAO;

        @Inject
        public MongoDepartamentoServiceImpl(Relacion1N.DepartamentoMongoDAO departamentoDAO) {
            this.departamentoDAO = departamentoDAO;
        }

        public Either<ErrorC, List<MongoDepartamento>> getAllDepartamentos() {
            return departamentoDAO.getAllDepartamentosMongo();
        }
    }