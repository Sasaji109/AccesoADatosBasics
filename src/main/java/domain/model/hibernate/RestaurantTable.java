package domain.model.hibernate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "restaurant_tables", schema = "example_exam_2eva")
public class RestaurantTable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "table_number_id")
    private int id;

    @Column(name = "number_of_seats")
    private int numSeats;

    public RestaurantTable(int id, int numSeats) {
        this.id = id;
        this.numSeats = numSeats;
    }
}
