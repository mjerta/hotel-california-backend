package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "meals")
public class Meal extends MenuItem {
  @OneToMany(mappedBy = "meal")
  List<Ingredient> ingredients;
}
