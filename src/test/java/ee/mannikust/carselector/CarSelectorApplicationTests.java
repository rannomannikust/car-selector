package ee.mannikust.carselector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import ee.mannikust.carselector.dto.UserSelectionDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarSelectorApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
	}
	
	void testIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index")) // Kontrollib, et kuvatakse index.html
                .andExpect(model().attributeExists("carBrands")); // Kontrollib, et andmed on olemas
	}

	@Test
	void testSelectCar() throws Exception {
		mockMvc.perform(post("/save")
						.param("firstName", "Jaan")
						.param("lastName", "Tamm")
						.param("hasLicense", "true")
						// Kuna see on List, võime saata mitu parameetrit sama nimega
						.param("selectedCarBrandIds", "30") 
						.param("selectedCarBrandIds", "10"))
						.andExpect(status().is3xxRedirection()) 
						.andExpect(redirectedUrl("/"));
	}

	@Test
	void testSaveCarSelectionValidationError() throws Exception {
		mockMvc.perform(post("/save")
				.param("firstName", "") // Tühi eesnimi tekitab vea
				.param("lastName", "Tamm"))
				.andExpect(status().isOk()) // Jääme samale lehele (200)
				.andExpect(view().name("index"))
				.andExpect(model().hasErrors()); // Kontrollime, et vead on olemas
	}

	@Test
	void testSaveSelectionWithErrors() throws Exception {
		mockMvc.perform(post("/save")
				.param("firstName", "") // Tühi nimi tekitab valideerimisvea
				.param("lastName", "")
				// Jätame autod valimata
				.with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(status().isOk()) // Ootame 200, sest meid ei suunata ümber, vaid näidatakse vormi uuesti
				.andExpect(view().name("index"))
				.andExpect(model().attributeExists("carBrands"))
				.andExpect(model().hasErrors());
	}

	@Test
	void testShowIndexPageWhenAttributeAlreadyExists() throws Exception {
		UserSelectionDto existingDto = new UserSelectionDto();
		existingDto.setFirstName("Eeltäidetud");

		mockMvc.perform(get("/")
				.flashAttr("userSelectionDto", existingDto)) // Paneme objekti mudelisse ette
				.andExpect(status().isOk())
				.andExpect(model().attribute("userSelectionDto", existingDto)) // Kontrollime, et seda ei kirjutatud üle
				.andExpect(view().name("index"));
	}

	@Test
	void testShowIndexPageWhenDtoAlreadyExists() throws Exception {
		UserSelectionDto existingDto = new UserSelectionDto();
		existingDto.setFirstName("Eeltäidetud");

		mockMvc.perform(get("/")
				.flashAttr("userSelectionDto", existingDto)) // Anname objekti ette
				.andExpect(status().isOk())
				.andExpect(model().attribute("userSelectionDto", existingDto)) // Kontrollime, et seda ei asendatud uue tühja objektiga
				.andExpect(view().name("index"));
	}
	@Test
	void testInitialLoad() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
		// See test läheb IF-lause sisse, sest mudel on tühi.
	}

	@Test
	void testWhenAlreadyExists() throws Exception {
		mockMvc.perform(get("/").flashAttr("userSelectionDto", new UserSelectionDto()))
				.andExpect(status().isOk());
		// See test EI LÄHE IF-lause sisse, sest me andsime objekti ette.
	}
}
