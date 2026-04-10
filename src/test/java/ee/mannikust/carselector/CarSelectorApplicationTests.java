package ee.mannikust.carselector;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import ee.mannikust.carselector.dto.UserSelectionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class CarSelectorApplicationTests extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void contextLoads() {}

  @Test
  void testIndexPage() throws Exception {
    mockMvc
        .perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index")) // Kontrollib, et kuvatakse index.html
        .andExpect(model().attributeExists("carBrands")); // Kontrollib, et andmed on olemas
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
        .perform(
            post("/save")
                .with(csrf())
                .param("firstName", "") // Tühi eesnimi tekitab vea
                .param("lastName", "Tamm"))
        .andExpect(status().isOk()) // Jääme samale lehele (200)
        .andExpect(view().name("index"))
        .andExpect(model().hasErrors()); // Kontrollime, et vead on olemas
  }

  @Test
  @WithMockUser
  void testSaveSelectionWithErrors() throws Exception {
    mockMvc
        .perform(
            post("/save")
                .with(csrf())
                .param("firstName", "") // Tühi nimi tekitab valideerimisvea
                .param("lastName", ""))
        .andExpect(
            status().isOk()) // Ootame 200, sest meid ei suunata ümber, vaid näidatakse vormi uuesti
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("carBrands"))
        .andExpect(model().hasErrors());
  }

  @Test
  void testShowIndexPageWhenAttributeAlreadyExists() throws Exception {
    UserSelectionDto existingDto = new UserSelectionDto();
    existingDto.setFirstName("Eeltäidetud");

    mockMvc
        .perform(
            get("/").flashAttr("userSelectionDto", existingDto)) // Paneme objekti mudelisse ette
        .andExpect(status().isOk())
        .andExpect(
            model()
                .attribute(
                    "userSelectionDto", existingDto)) // Kontrollime, et seda ei kirjutatud üle
        .andExpect(view().name("index"));
  }

  @Test
  void testShowIndexPageCreatesDtoWhenMissing() throws Exception {
    mockMvc
        .perform(get("/")) // Tavalise kasutaja esimene GET päring
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(
            model()
                .attributeExists(
                    "userSelectionDto")); // Kontrollime, et kontroller ise lõi mudelisse uue
    // (tühja) objekti
  }

  @Test
  void testInitialLoad() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk());
    // See test läheb IF-lause sisse, sest mudel on tühi.
  }

  @Test
  void testWhenAlreadyExists() throws Exception {
    mockMvc
        .perform(get("/").flashAttr("userSelectionDto", new UserSelectionDto()))
        .andExpect(status().isOk());
    // See test EI LÄHE IF-lause sisse, sest me andsime objekti ette.
  }
}
