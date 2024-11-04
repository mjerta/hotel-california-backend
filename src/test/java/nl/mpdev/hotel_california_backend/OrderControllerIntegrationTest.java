package nl.mpdev.hotel_california_backend;

import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.OrderRepository;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
//@ActiveProfiles("test")
class OrderControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("addOrder_whenNotLoggedIn - when no user is logged in")
  public void addOrder_whenNotLoggedIn() throws Exception {
    String orderRequestJson = """
                              {
                                  "meals": [
                                      1
                                  ],
                                  "drinks": [
                                      1,
                                      2
                                  ], 
                                  "destination": 5
                              }
                              """;

    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
        .contentType(APPLICATION_JSON)
        .content(orderRequestJson))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @DisplayName("addOrder_loggedin - when logged in")
  @WithMockUser(username = "regular", roles = {"USER"})
  public void addOrder_loggedin() throws Exception {
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
  @DisplayName("getOrders - get all order based on if staff is logged in")
  @WithMockUser(username = "staff", roles = {"STAFF"})
  void getOrders() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("getOrderByOrderReference - get an order only based on a orderreference passed in the params")
  void getOrderByOrderReference() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/orderreference")
        .param("orderReference", "1728595232306-7497")
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("getOrderByIdByUserLoggedIn - get a order based on the ID and if they are logged in")
  @WithMockUser(username = "pietje", roles = {"USER"})
  void getOrderByIdByUserLoggedIn() throws Exception {

    User user = User.builder()
      .username("pietje")
      .password("password")
      .build();
    userRepository.save(user);

    Drink drink = Drink.builder()
      .name("Heineken")
      .price(29.00)
      .isAlcoholic(false)
      .description("lekker")
      .build();
    Order order = Order.builder()
      .drinks(List.of(drink))
      .user(user)
      .build();

    Order savedOrder = orderRepository.save(order);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", savedOrder.getId())
        .header("Authorization", BEARER_TOKEN)
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk());
  }



  @Test
  @DisplayName("deleteOrder_notAuthorized - delete the user with the role of user")
  @WithMockUser(username = "regular",roles = {"USER"})
  void deleteOrder_notAuthorized() throws Exception {


    Drink drink = Drink.builder()
      .name("Heineken")
      .price(29.00)
      .isAlcoholic(false)
      .description("lekker")
      .build();
    Order order = Order.builder()
      .drinks(List.of(drink))
      .build();

    Order savedOrder = orderRepository.save(order);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/{id}",savedOrder.getId())
        .header("Authorization", BEARER_TOKEN)
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @DisplayName("deleteOrder_authorized - delete the user with the role of staff")
  @WithMockUser(username = "staff",roles = {"STAFF"})
  void deleteOrder_authorized() throws Exception {


    Drink drink = Drink.builder()
      .name("Heineken")
      .price(29.00)
      .isAlcoholic(false)
      .description("lekker")
      .build();
    Order order = Order.builder()
      .drinks(List.of(drink))
      .build();

    Order savedOrder = orderRepository.save(order);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/{id}",savedOrder.getId())
        .header("Authorization", BEARER_TOKEN)
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
