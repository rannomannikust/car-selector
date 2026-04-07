package ee.mannikust.carselector;

import static org.junit.jupiter.api.Assertions.*;

import ee.mannikust.carselector.dto.UserSelectionDto;
import java.util.List;
import org.junit.jupiter.api.Test;

class UserSelectionDtoTest extends BaseIntegrationTest {

  @Test
  void testDtoGettersAndSetters() {
    UserSelectionDto dto = new UserSelectionDto();
    dto.setFirstName("Kalle");
    dto.setLastName("Karu");
    dto.setSelectedCarBrandIds(List.of(1L, 2L));

    assertEquals("Kalle", dto.getFirstName());
    assertEquals("Karu", dto.getLastName());
    assertEquals(2, dto.getSelectedCarBrandIds().size());
  }
}
