package ee.mannikust.carselector.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ee.mannikust.carselector.BaseWebIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

class GlobalExceptionHandlerTest extends BaseWebIntegrationTest {

  @Test
  @WithMockUser
  void testBusinessExceptionHandled() throws Exception {
    mockMvc
        .perform(get("/throw-business-error"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"))
        .andExpect(flash().attributeExists("error"));
  }

  @Test
  void testGenericExceptionHandled() throws Exception {
    mockMvc
        .perform(get("/throw-generic-error"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"))
        .andExpect(model().attributeExists("error"));
  }
}
