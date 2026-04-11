package ee.mannikust.carselector;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ee.mannikust.carselector.dto.UserSelectionDto;
import ee.mannikust.carselector.exception.CarSelectorException;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

class CarSelectorApplicationTests extends BaseWebIntegrationTest {

  @Test
  void testIndexPage() throws Exception {
    mockMvc
        .perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("carBrands"));
  }

  @WithMockUser
  @Test
  void testSelectCar() throws Exception {
    mockMvc
        .perform(
            post("/save")
                .with(csrf())
                .param("firstName", "Jaan")
                .param("lastName", "Tamm")
                .param("hasLicense", "true")
                .param("selectedCarBrandIds", "30")
                .param("selectedCarBrandIds", "10"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  void testSaveCarSelectionValidationError() throws Exception {
    mockMvc
        .perform(post("/save").with(csrf()).param("firstName", "").param("lastName", "Tamm"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().hasErrors());
  }

  @Test
  @WithMockUser
  void testSaveSelectionWithErrors() throws Exception {
    mockMvc
        .perform(post("/save").with(csrf()).param("firstName", "").param("lastName", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("carBrands"))
        .andExpect(model().hasErrors());
  }

  @Test
  void testShowIndexPageWhenAttributeAlreadyExists() throws Exception {
    UserSelectionDto existingDto = new UserSelectionDto();
    existingDto.setFirstName("Eeltäidetud");

    mockMvc
        .perform(get("/").flashAttr("userSelectionDto", existingDto))
        .andExpect(status().isOk())
        .andExpect(model().attribute("userSelectionDto", existingDto))
        .andExpect(view().name("index"));
  }

  @Test
  void testShowIndexPageCreatesDtoWhenMissing() throws Exception {
    mockMvc
        .perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("userSelectionDto"));
  }

  @Test
  void testInitialLoad() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk());
  }

  @Test
  void testWhenAlreadyExists() throws Exception {
    mockMvc
        .perform(get("/").flashAttr("userSelectionDto", new UserSelectionDto()))
        .andExpect(status().isOk());
  }

  @Test
  void testCarSelectorExceptionWithCause() {
    RuntimeException cause = new RuntimeException("Algne viga");

    CarSelectorException exception = new CarSelectorException("Äriloogika viga", cause);

    assertEquals("Äriloogika viga", exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
