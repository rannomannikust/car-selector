package ee.mannikust.carselector;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ee.mannikust.carselector.dto.UserSelectionDto;
import org.junit.jupiter.api.Test;

class UserSelectionDtoTest {

  @Test
  void testDto() {
    UserSelectionDto dto = new UserSelectionDto();
    dto.setFirstName("Kalle");
    dto.setLastName("Suslik");
    dto.setHasLicense(true);
    assertAll(
        () -> assertEquals("Kalle", dto.getFirstName()),
        () -> assertEquals("Suslik", dto.getLastName()),
        () -> assertTrue(dto.isHasLicense()));
  }
}
