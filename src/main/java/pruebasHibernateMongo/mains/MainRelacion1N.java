package pruebasHibernateMongo.mains;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import pruebasHibernateMongo.servicios.DepartamentoServiceImpl;
import pruebasHibernateMongo.servicios.EmpleadoServiceImpl;
import pruebasHibernateMongo.servicios.MongoDepartamentoServiceImpl;

public class MainRelacion1N {

    private final DepartamentoServiceImpl departamentoService;
    private final MongoDepartamentoServiceImpl mongoDepartamentoService;
    private final EmpleadoServiceImpl empleadoService;

    @Inject
    public MainRelacion1N(DepartamentoServiceImpl departamentoService, MongoDepartamentoServiceImpl mongoDepartamentoService, EmpleadoServiceImpl empleadoService) {
        this.departamentoService = departamentoService;
        this.mongoDepartamentoService = mongoDepartamentoService;
        this.empleadoService = empleadoService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        MainRelacion1N mainRelacion1N = container.select(MainRelacion1N.class).get();
        System.out.println(mainRelacion1N.departamentoService.getAllDepartamentos());
        System.out.println(mainRelacion1N.mongoDepartamentoService.getAllDepartamentos());
        System.out.println(mainRelacion1N.empleadoService.getAllEmpleados());
    }
}


