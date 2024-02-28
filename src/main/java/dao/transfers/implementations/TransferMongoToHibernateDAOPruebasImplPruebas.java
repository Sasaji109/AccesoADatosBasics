package dao.transfers.implementations;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import common.configuration.JPAUtil;
import common.configuration.MongoDBConfig;
import dao.transfers.TransferMongoToHibernateDAOPruebas;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.bson.Document;
import pruebasHibernateMongo.modelo.Departamento;
import pruebasHibernateMongo.modelo.Empleado;
import pruebasHibernateMongo.modelo.MongoDepartamento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransferMongoToHibernateDAOPruebasImplPruebas implements TransferMongoToHibernateDAOPruebas {

    private final JPAUtil jpaUtil;
    private final MongoDatabase mongoDatabase;
    private EntityManager em;

    @Inject
    public TransferMongoToHibernateDAOPruebasImplPruebas(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    public Either<ErrorC, Integer> transferMongoToHibernate() {
        Either<ErrorC, Integer> either;

        try {
            List<MongoDepartamento> mongoDepartamentos = getAllDepartamentosMongo().get();

            List<Departamento> departamentos = mongoDepartamentos.stream()
                    .map(this::mapToDepartamento)
                    .collect(Collectors.toList());

            persistDepartamentos(departamentos);
            either = Either.right(departamentos.size());

        } catch (Exception e) {
            either = Either.left(new ErrorC(6, Constants.MAPPING_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    private Departamento mapToDepartamento(MongoDepartamento mongoDepartamento) {
        Departamento departamento = new Departamento();
        departamento.setNombre(mongoDepartamento.getNombre());

        List<Empleado> empleados = mongoDepartamento.getEmpleados().stream()
                .map(mongoEmpleado -> {
                    Empleado empleado = new Empleado();
                    empleado.setId(0);
                    empleado.setNombre(mongoEmpleado.getNombre());
                    empleado.setDepartamento_id(0);
                    return empleado;
                })
                .collect(Collectors.toList());

        departamento.setEmpleados(empleados);
        return departamento;
    }

    private void persistDepartamentos(List<Departamento> departamentos) {
        em = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        try {
            entityTransaction.begin();

            for (Departamento departamento : departamentos) {
                Departamento departamento1 = new Departamento(departamento.getId(),departamento.getNombre());
                em.persist(departamento1);

                for (Empleado empleado : departamento.getEmpleados()) {
                    Integer id = departamento1.getId();
                    empleado.setDepartamento_id(id);
                    em.merge(empleado);
                }
            }

            entityTransaction.commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

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
