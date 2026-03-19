package ee.mannikust.carselector.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class UserSelectionDto {

    @NotBlank(message = "Eesnimi on kohustuslik")
    private String firstName;

    @NotBlank(message = "Perenimi on kohustuslik")
    private String lastName;

    @NotEmpty(message = "Vali vähemalt üks automark")
    private List<Long> selectedCarBrandIds;
}