package nl.mpdev.hotel_california_backend;

import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.OrderRepository;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
class OrderControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private UserRepository userRepository;
  private Order order;


  @AfterEach
  public void cleanUp() {
    if(order != null) {
      orderRepository.delete(order);
    }
  }

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
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").isNotEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.user").isEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_QUEUE"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.id").value(5))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.locationNumber").value(105))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.isOccupied").value(true))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.locationType").value("Hotel Room"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].name").value("Caesar Salad"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].price").value(12.99))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].ingredients[0].id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].ingredients[0].name").value("Romaine sla"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].name").value("Fanta"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].price").value(2.5))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].isAlcoholic").value(false))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].size").value(330))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].id").value(2))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].name").value("Heineken"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].price").value(3.75))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].isAlcoholic").value(true))
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderReference").isNotEmpty());
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
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").isNotEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("regular"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_QUEUE"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.id").value(6))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.locationNumber").value(201))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.isOccupied").value(true))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.locationType").value("table"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].name").value("Caesar Salad"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].price").value(12.99))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].ingredients[0].id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals[0].ingredients[0].name").value("Romaine sla"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].name").value("Fanta"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].price").value(2.5))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].isAlcoholic").value(false))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].size").value(330))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].id").value(2))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].name").value("Heineken"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].price").value(3.75))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[1].isAlcoholic").value(true))
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderReference").isNotEmpty());
  }

  @Test
  @DisplayName("getOrders - get all order based on if staff is logged in")
  @WithMockUser(username = "staff", roles = {"STAFF"})
  void getOrders() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderDate").value("2024-10-05T12:30:00"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("PREPARING_ORDER"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination.id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderReference").value("1728595232306-7497"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("getOrderByOrderReference - get an order only based on a orderreference passed in the params")
  void getOrderByOrderReference() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/orderreference")
        .param("orderReference", "1728595232306-7497")
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").value("2024-10-05T12:30:00"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PREPARING_ORDER"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination.id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderReference").value("1728595232306-7497"))
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
      .price(28.00)
      .isAlcoholic(false)
      .description("lekker")
      .build();
    order = Order.builder()
      .drinks(List.of(drink))
      .user(user)
      .build();

    Order savedOrder = orderRepository.save(order);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", savedOrder.getId())
        .header("Authorization", BEARER_TOKEN)
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("pietje"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").isEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.meals").isEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderReference").isEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks").isNotEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.destination").isEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].name").value("Heineken"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].price").value(28.00))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].isAlcoholic").value(false))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].description").value("lekker"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.drinks[0].size").isEmpty())
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
    order = Order.builder()
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
    order = Order.builder()
      .drinks(List.of(drink))
      .build();

    Order savedOrder = orderRepository.save(order);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/{id}",savedOrder.getId())
        .header("Authorization", BEARER_TOKEN)
        .contentType(APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isNoContent());

    Optional<Order> deletedOrder = orderRepository.findById(savedOrder.getId());
    assertFalse(deletedOrder.isPresent());
  }
}
