package nl.mpdev.hotel_california_backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class OrderControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("addOrder")
  public void addOrder() throws Exception {
    String orderRequestJson = """
                              {
                                  "meals": [
                                      1
                                  ],
                                  "drinks": [
                                      1,
                                      2
                                  ],
                                  "destination": 6
                              }
                              """;

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
        .contentType(APPLICATION_JSON)
        .content(orderRequestJson))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void getOrderByOrderReference() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/orderreference")
        .param("orderReference", "1728595232306-7497")
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
