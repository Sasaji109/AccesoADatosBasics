package pruebasHibernateMongo.mains;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import pruebasHibernateMongo.servicios.*;

public class MainRelacion11 {

    private final LibroServiceImpl libroService;
    private final EstudianteServiceImpl estudianteService;

    @Inject
    public MainRelacion11(LibroServiceImpl libroService, EstudianteServiceImpl estudianteService) {
        this.libroService = libroService;
        this.estudianteService = estudianteService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        MainRelacion11 mainRelacion11 = container.select(MainRelacion11.class).get();
        System.out.println(mainRelacion11.libroService.getAllLibros());
        System.out.println(mainRelacion11.estudianteService.getAllEstudiantes());
    }
}


