package pruebasHibernateMongo.mains;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import pruebasHibernateMongo.servicios.CursoServiceImpl;
import pruebasHibernateMongo.servicios.EstudianteCursoServiceImpl;

public class MainRelacionNN {

    private final CursoServiceImpl cursoService;
    private final EstudianteCursoServiceImpl estudianteCursoService;

    @Inject
    public MainRelacionNN(CursoServiceImpl cursoService, EstudianteCursoServiceImpl estudianteCursoService) {
        this.cursoService = cursoService;
        this.estudianteCursoService = estudianteCursoService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        MainRelacionNN mainRelacionNN = container.select(MainRelacionNN.class).get();
        System.out.println(mainRelacionNN.cursoService.getAllCursos());
        System.out.println(mainRelacionNN.estudianteCursoService.getAllEstudiantesCursos());
    }
}


