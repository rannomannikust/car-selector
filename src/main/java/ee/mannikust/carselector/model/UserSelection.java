package ee.mannikust.carselector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "user_selections")
@Getter
@Setter
public class UserSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private boolean hasLicense;

    @ManyToMany
    @JoinTable(
        name = "user_selection_car_brands",
        joinColumns = @JoinColumn(name = "user_selection_id"),
        inverseJoinColumns = @JoinColumn(name = "car_brand_id")
    )
    private List<CarBrand> selectedBrands;
}
