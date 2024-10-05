package nl.mpdev.hotel_california_backend.helpers;

import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class ServiceHelper {
  public void setFieldsIfNotNUll(Object existingObject, Object incomingObject) {
    Field[] fields = incomingObject.getClass().getDeclaredFields();
    for (Field dtoField : fields) {
      dtoField.setAccessible(true);
      try {
        Object newValue = dtoField.get(incomingObject);
        if (newValue != null && !List.class.isAssignableFrom(dtoField.getType())) {
          Field existingField = existingObject.getClass().getDeclaredField(dtoField.getName());
          existingField.setAccessible(true);
          existingField.set(existingObject, newValue);
        }
      } catch (IllegalAccessException e) {
        throw new GeneralException("Cannot access fields: " + dtoField.getName());
      } catch (NoSuchFieldException e) {
        throw new GeneralException("Field not found: " + dtoField.getName());
      }
    }
  }
}
