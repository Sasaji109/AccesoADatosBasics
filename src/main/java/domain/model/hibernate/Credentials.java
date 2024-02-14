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
@Table(name = "credentials", schema = "example_exam_2eva")
@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_CREDENTIALS", query = "from Credentials") })
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    public Credentials(Integer customerId, String username, String password) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
    }
}
