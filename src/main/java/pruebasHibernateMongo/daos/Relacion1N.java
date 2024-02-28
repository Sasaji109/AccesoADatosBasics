package pruebasHibernateMongo.daos;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import common.configuration.JPAUtil;
import common.configuration.MongoDBConfig;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import org.bson.Document;
import pruebasHibernateMongo.modelo.Departamento;
import pruebasHibernateMongo.modelo.Empleado;
import pruebasHibernateMongo.modelo.MongoDepartamento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Relacion1N {

    public interface DepartamentoDAO {
        Either<ErrorC, List<Departamento>> getAllDepartamentos();
    }

    public interface DepartamentoMongoDAO {
        Either<ErrorC, List<MongoDepartamento>> getAllDepartamentosMongo();
    }

    public interface EmpleadoDAO {
        Either<ErrorC, List<Empleado>> getAllEmpleados();
    }

    public static class DepartamentoDAOImpl implements DepartamentoDAO {
        private final JPAUtil jpaUtil;
        private EntityManager em;

        @Inject
        public DepartamentoDAOImpl(JPAUtil jpaUtil) {
            this.jpaUtil = jpaUtil;
        }

        @Override
        public Either<ErrorC, List<Departamento>> getAllDepartamentos() {
            Either<ErrorC, List<Departamento>> either;

            List<Departamento> departamentos;
            em = jpaUtil.getEntityManager();

            try {
                departamentos = em.createQuery("FROM Departamento", Departamento.class).getResultList();
                either = Either.right(departamentos);
            } catch (Exception e) {
                either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
            }
            return either;
        }
    }

    public static class EmpleadoDAOImpl implements EmpleadoDAO {
        private final JPAUtil jpaUtil;
        private EntityManager em;

        @Inject
        public EmpleadoDAOImpl(JPAUtil jpaUtil) {
            this.jpaUtil = jpaUtil;
        }

        @Override
        public Either<ErrorC, List<Empleado>> getAllEmpleados() {
            Either<ErrorC, List<Empleado>> either;

            List<Empleado> empleados;
            em = jpaUtil.getEntityManager();

            try {
                empleados = em.createQuery("FROM Empleado", Empleado.class).getResultList();
                either = Either.right(empleados);
            } catch (Exception e) {
                either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
            }
            return either;
        }
    }

    public static class DepartamentosDAOMImpl implements DepartamentoMongoDAO {

        private final MongoDatabase mongoDatabase;

        @Inject
        public DepartamentosDAOMImpl() {
            this.mongoDatabase = MongoDBConfig.getMongoDatabase();
        }

        @Override
        public Either<ErrorC, List<MongoDepartamento>> getAllDepartamentosMongo() {
            Either<ErrorC, List<MongoDepartamento>> either;

            try {
                MongoCollection<Document> est = mongoDatabase.getCollection("departamentos");
                List<MongoDepartamento> departamentos = new ArrayList<>();
                List<Document> documents = est.find().into(new ArrayList<>());

                for (Document departamento : documents) {
                    departamentos.add(new Gson().fromJson(departamento.toJson(), MongoDepartamento.class));
                }
                either = Either.right(departamentos);

            } catch(Exception e) {
                either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
            }
            return either;
        }
    }
}
