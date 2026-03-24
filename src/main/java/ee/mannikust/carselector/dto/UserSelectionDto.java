package ee.mannikust.carselector.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class UserSelectionDto {

    @NotBlank(message = "{validation.firstname.empty}")
    private String firstName;

    @NotBlank(message = "{validation.lastname.empty}")
    private String lastName;

    @NotEmpty(message = "{validation.brands.empty}")
    private List<Long> selectedCarBrandIds;

    private boolean hasLicense; // Uus väli, mis näitab, kas kasutajal on juhiluba
}