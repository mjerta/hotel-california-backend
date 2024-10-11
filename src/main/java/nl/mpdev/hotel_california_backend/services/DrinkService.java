package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkService {

  private final DrinkRepository drinkRepository;
  private final ServiceHelper serviceHelper;

  public DrinkService(DrinkRepository drinkRepository, ServiceHelper serviceHelper) {
    this.drinkRepository = drinkRepository;
    this.serviceHelper = serviceHelper;
  }

  public Drink getDrinkById(Integer id) {
    return drinkRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
  }

  public List<Drink> getDrinks() {
    return drinkRepository.findAll();
  }

  public Drink addDrink(Drink entity) {
   return drinkRepository.save(entity);
  }

  public Drink updateDrink(Integer id, DrinkCompleteRequestDto responseDto) {
    Drink existingDrink = drinkRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    existingDrink = existingDrink.toBuilder()
      .name(responseDto.getName())
      .description(responseDto.getDescription())
      .price(responseDto.getPrice())
      .image(responseDto.getImage())
      .isAlcoholic(responseDto.getIsAlcoholic())
      .size(responseDto.getSize())
      .measurement(responseDto.getMeasurement())
      .build();
    return drinkRepository.save(existingDrink);
  }

  public Drink updateDrinkFields(Integer id, DrinkCompleteRequestDto requestDto) {
    Drink existingDrink = drinkRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    serviceHelper.setFieldsIfNotNUll(existingDrink, requestDto);
    return drinkRepository.save(existingDrink);
  }

  public void deleteDrink(Integer id) {
    drinkRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    drinkRepository.deleteById(id);
  }
}
