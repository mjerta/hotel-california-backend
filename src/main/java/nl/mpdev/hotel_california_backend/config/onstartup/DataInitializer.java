package nl.mpdev.hotel_california_backend.config.onstartup;

import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.*;
import nl.mpdev.hotel_california_backend.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {
  private final MealRepository mealRepository;
  @Value("${app.default.user.super}")
  private String superUser;
  @Value("${app.default.user.super.password}")
  private String superUserPassword;

  @Value("${app.default.user.manager}")
  private String managerUser;
  @Value("${app.default.user.manager.password}")
  private String managerUserPassword;

  @Value("${app.default.user.chef}")
  private String chefUser;
  @Value("${app.default.user.chef.password}")
  private String chefUserPassword;

  @Value("${app.default.user.staff}")
  private String staffUser;
  @Value("${app.default.user.staff.password}")
  private String staffUserPassword;

  @Value("${app.default.user.regular}")
  private String regularUser;

  @Value("${app.default.user.regular.password}")
  private String regularUserPassword;

  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;

  public DataInitializer(UserRepository userRepository, ProfileRepository profileRepository, MealImageRepository mealImageRepository,
                         MealRepository mealRepository) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.mealRepository = mealRepository;
  }

  @Bean
  public CommandLineRunner loadData(PasswordEncoder passwordEncoder,
                                    IngredientRepository ingredientRepository) {
    return args -> {
      // Caesar Salad
      Meal caesarSalad = createMealWithImage("ceasar-salad", "Caesar Salad", "Klassieke Caesar salade met romige dressing", 12.99);
      ingredientRepository.save(Ingredient.builder().meal(caesarSalad).name("Romaine sla").build());
      ingredientRepository.save(Ingredient.builder().meal(caesarSalad).name("Croutons").build());
      ingredientRepository.save(Ingredient.builder().meal(caesarSalad).name("Parmezaanse kaas").build());
      ingredientRepository.save(Ingredient.builder().meal(caesarSalad).name("Caesar dressing").build());

      // Falafel
      Meal falafel = createMealWithImage("falafel", "Falafel", "Heerlijke falafel met verse groenten en hummus", 9.99);
      ingredientRepository.save(Ingredient.builder().meal(falafel).name("Falafel balletjes").build());
      ingredientRepository.save(Ingredient.builder().meal(falafel).name("Hummus").build());
      ingredientRepository.save(Ingredient.builder().meal(falafel).name("Tomaat").build());
      ingredientRepository.save(Ingredient.builder().meal(falafel).name("Komkommer").build());

      // Hamburger
      Meal hamburger = createMealWithImage("hamburger", "Hamburger", "Sappige hamburger met verse ingrediënten", 14.99);
      ingredientRepository.save(Ingredient.builder().meal(hamburger).name("Hamburger broodje").build());
      ingredientRepository.save(Ingredient.builder().meal(hamburger).name("Rundvleesburger").build());
      ingredientRepository.save(Ingredient.builder().meal(hamburger).name("Cheddar kaas").build());
      ingredientRepository.save(Ingredient.builder().meal(hamburger).name("Augurken").build());

      // Pad Thai
      Meal padThai = createMealWithImage("pad-thai", "Pad Thai", "Thaise noedels met een combinatie van smaken", 13.99);
      ingredientRepository.save(Ingredient.builder().meal(padThai).name("Rijstnoedels").build());
      ingredientRepository.save(Ingredient.builder().meal(padThai).name("Taugé").build());
      ingredientRepository.save(Ingredient.builder().meal(padThai).name("Pinda's").build());
      ingredientRepository.save(Ingredient.builder().meal(padThai).name("Limoen").build());

      // Steak
      Meal steak = createMealWithImage("steak", "Steak", "Perfect gegrilde steak met een mix van kruiden", 24.99);
      ingredientRepository.save(Ingredient.builder().meal(steak).name("Ribeye steak").build());
      ingredientRepository.save(Ingredient.builder().meal(steak).name("Rozemarijn").build());
      ingredientRepository.save(Ingredient.builder().meal(steak).name("Boter").build());
      ingredientRepository.save(Ingredient.builder().meal(steak).name("Knoflook").build());

      // Chicken Alfredo
      Meal chickenAlfredo = createMealWithImage("chicken-alfredo", "Chicken Alfredo", "Romige pasta met kip en Parmezaanse kaas",
        15.99);
      ingredientRepository.save(Ingredient.builder().meal(chickenAlfredo).name("Pasta").build());
      ingredientRepository.save(Ingredient.builder().meal(chickenAlfredo).name("Kipfilet").build());
      ingredientRepository.save(Ingredient.builder().meal(chickenAlfredo).name("Parmezaanse kaas").build());
      ingredientRepository.save(Ingredient.builder().meal(chickenAlfredo).name("Alfredo saus").build());

      // Fish and Chips
      Meal fishAndChips = createMealWithImage("fish-and-chips", "Fish and Chips", "Krokante vis met frieten", 11.99);
      ingredientRepository.save(Ingredient.builder().meal(fishAndChips).name("Gebakken vis").build());
      ingredientRepository.save(Ingredient.builder().meal(fishAndChips).name("Frieten").build());
      ingredientRepository.save(Ingredient.builder().meal(fishAndChips).name("Tartaarsaus").build());
      ingredientRepository.save(Ingredient.builder().meal(fishAndChips).name("Citroen").build());

      // Lamb Chops
      Meal lambChops = createMealWithImage("lamb-chops", "Lamb Chops", "Gegrilde lamskoteletten met kruiden", 22.99);
      ingredientRepository.save(Ingredient.builder().meal(lambChops).name("Lamskotelet").build());
      ingredientRepository.save(Ingredient.builder().meal(lambChops).name("Rozemarijn").build());
      ingredientRepository.save(Ingredient.builder().meal(lambChops).name("Knoflook").build());
      ingredientRepository.save(Ingredient.builder().meal(lambChops).name("Olijfolie").build());

      // Pizza
      Meal pizza = createMealWithImage("pizza", "Pizza", "Pizza met veel smaak", 19.99);
      ingredientRepository.save(Ingredient.builder().meal(pizza).name("Tomatensaus").build());
      ingredientRepository.save(Ingredient.builder().meal(pizza).name("Mozzarella").build());
      ingredientRepository.save(Ingredient.builder().meal(pizza).name("Pepperoni").build());
      ingredientRepository.save(Ingredient.builder().meal(pizza).name("Olijven").build());

      // Sushi
      Meal sushi = createMealWithImage("sushi", "Sushi", "Verse sushi met rijst en rauwe vis", 18.99);
      ingredientRepository.save(Ingredient.builder().meal(sushi).name("Sushirijst").build());
      ingredientRepository.save(Ingredient.builder().meal(sushi).name("Zalm").build());
      ingredientRepository.save(Ingredient.builder().meal(sushi).name("Nori zeewier").build());
      ingredientRepository.save(Ingredient.builder().meal(sushi).name("Sojasaus").build());

      // Chicken Curry
      Meal chickenCurry = createMealWithImage("chicken-curry", "Chicken Curry", "Pittige kipcurry met een mix van kruiden", 16.99);
      ingredientRepository.save(Ingredient.builder().meal(chickenCurry).name("Kipfilet").build());
      ingredientRepository.save(Ingredient.builder().meal(chickenCurry).name("Kokosmelk").build());
      ingredientRepository.save(Ingredient.builder().meal(chickenCurry).name("Currypoeder").build());
      ingredientRepository.save(Ingredient.builder().meal(chickenCurry).name("Rijst").build());

      // Grilled Salmon
      Meal grilledSalmon = createMealWithImage("grilled-salmon", "Grilled Salmon", "Zalmfilet met gegrilde groente", 20.99);
      ingredientRepository.save(Ingredient.builder().meal(grilledSalmon).name("Zalmfilet").build());
      ingredientRepository.save(Ingredient.builder().meal(grilledSalmon).name("Groene asperges").build());
      ingredientRepository.save(Ingredient.builder().meal(grilledSalmon).name("Dille").build());
      ingredientRepository.save(Ingredient.builder().meal(grilledSalmon).name("Citroen").build());

      // Lasagna
      Meal lasagna = createMealWithImage("lasagna", "Lasagna", "Traditionele lasagne met lagen van pasta en saus", 14.99);
      ingredientRepository.save(Ingredient.builder().meal(lasagna).name("Pastabladen").build());
      ingredientRepository.save(Ingredient.builder().meal(lasagna).name("Bolognesesaus").build());
      ingredientRepository.save(Ingredient.builder().meal(lasagna).name("Ricotta").build());
      ingredientRepository.save(Ingredient.builder().meal(lasagna).name("Parmezaanse kaas").build());

      // Ribeye
      Meal ribeye = createMealWithImage("ribeye", "Ribeye", "Sappige ribeye steak met kruidenboter", 26.99);
      ingredientRepository.save(Ingredient.builder().meal(ribeye).name("Ribeye steak").build());
      ingredientRepository.save(Ingredient.builder().meal(ribeye).name("Kruidenboter").build());
      ingredientRepository.save(Ingredient.builder().meal(ribeye).name("Peper").build());
      ingredientRepository.save(Ingredient.builder().meal(ribeye).name("Zeezout").build());

      // Tacos
      Meal tacos = createMealWithImage("tacos", "Tacos", "Taco's met gehakt en frisse salsa", 10.99);
      ingredientRepository.save(Ingredient.builder().meal(tacos).name("Taco schelpen").build());
      ingredientRepository.save(Ingredient.builder().meal(tacos).name("Gehakt").build());
      ingredientRepository.save(Ingredient.builder().meal(tacos).name("Salsa").build());
      ingredientRepository.save(Ingredient.builder().meal(tacos).name("IJsbergsla").build());

      createUserWithRoles(superUser, passwordEncoder.encode(superUserPassword), "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(managerUser, passwordEncoder.encode(managerUserPassword), "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(chefUser, passwordEncoder.encode(chefUserPassword), "ROLE_STAFF", "ROLE_CHEF", "ROLE_USER");
      createUserWithRoles(staffUser, passwordEncoder.encode(staffUserPassword), "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(regularUser, passwordEncoder.encode(regularUserPassword),
        profileRepository.findById(1).orElseThrow(() -> new RecordNotFoundException("No profile found")), "ROLE_USER");
    };
  }

  private Meal createMealWithImage(String fileName, String nameMeal, String description, Double price) {
    Meal meal = Meal.builder()
      .name(nameMeal)
      .description(description)
      .price(price)
      .image(createMealImage("images/" + fileName + ".jpg"))
      .build();
    return mealRepository.save(meal);
  }

  private ImageMeal createMealImage(String imagePath) {
    ClassPathResource imageResource = new ClassPathResource(imagePath);
    if (imageResource.exists()) {
      try (InputStream inputStream = imageResource.getInputStream()) {
        byte[] imageBytes = inputStream.readAllBytes();
        return buildImageMael(imageBytes, imageBytes.length, Files.probeContentType(Paths.get(imagePath)),
          Paths.get(imagePath).getFileName().toString());
      } catch (Exception e) {
        System.err.println("Failed to load image: " + imagePath);
        e.printStackTrace();
      }
    }
    else {
      System.err.println("Image not found: " + imagePath);
    }
    return null;
  }

  private static ImageMeal buildImageMael(byte[] imageBytes, long size, String contentType, String filename) {
    contentType = (contentType != null) ? contentType : "image/jpeg";
    return ImageMeal.builder()
      .data(imageBytes)
      .size(size)
      .contentType(contentType)
      .name(filename)
      .build();
  }

  public void createUserWithRoles(String user, String password, String... roles) {
    Set<Authority> authorities = new HashSet<>();
    for (String role : roles) {
      authorities.add(createAuthority(user, role));
    }
    saveUser(user, password, authorities);
  }

  public void createUserWithRoles(String user, String password, Profile profile, String... roles) {
    Set<Authority> authorities = new HashSet<>();
    for (String role : roles) {
      authorities.add(createAuthority(user, role));
    }
    saveUser(user, password, authorities, profile);
  }

  private void saveUser(String userName, String password, Set<Authority> superUserAuthorities) {
    User adminUser = User.builder()
      .username(userName)
      .password(password)
      .enabled(true)
      .authorities(superUserAuthorities)
      .build();
    userRepository.save(adminUser);
  }

  private void saveUser(String userName, String password, Set<Authority> superUserAuthorities, Profile profile) {
    User adminUser = User.builder()
      .username(userName)
      .password(password)
      .enabled(true)
      .authorities(superUserAuthorities)
      .profile(profile)
      .build();
    userRepository.save(adminUser);
  }

  private Authority createAuthority(String username, String authorityType) {
    return Authority.builder()
      .username(username)
      .authority(authorityType)
      .build();
  }
}