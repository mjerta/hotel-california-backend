package nl.mpdev.hotel_california_backend;

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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class OrderControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void addOrder() throws Exception {
    String orderRequestJson = """
    // Example JSON request for OrderNewRequestDto
    {
        "orderDetails": "Sample order details",
        "quantity": 3
    }
    """;

    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
        .contentType(APPLICATION_JSON)
        .content(orderRequestJson)
        .characterEncoding("utf-8"))
      .andExpect(status().isCreated())
      .andExpect(header().string("Location", "/orders/")) // Confirm the URI format for authenticated users
      .andExpect(contentType(APPLICATION_JSON)
      .andExpect(jsonPath("$.propertyInResponseDto").exists()); // Replace with actual property checks
  }
}
