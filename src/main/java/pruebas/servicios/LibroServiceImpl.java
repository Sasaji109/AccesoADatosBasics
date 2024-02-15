package pruebas.servicios;

import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import pruebas.daos.Relacion11;
import pruebas.modelo.Libro;
import java.util.List;

public class LibroServiceImpl {
        private final Relacion11.LibroDAO libroDAO;

        @Inject
        public LibroServiceImpl(Relacion11.LibroDAO libroDAO) {
            this.libroDAO = libroDAO;
        }

        public Either<ErrorC, List<Libro>> getAllLibros() {
            return libroDAO.getAllLibros();
        }
    }