package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ErrorC extends Throwable {
    private int nError;
    private String message;
    private LocalDate date;
}
