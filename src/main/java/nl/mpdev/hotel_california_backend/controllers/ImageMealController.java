package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import nl.mpdev.hotel_california_backend.models.ImageMeal;
import nl.mpdev.hotel_california_backend.services.ImageMealService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/image-meals")
public class ImageMealController {

  private final ImageMealService imageMealService;

  public ImageMealController(ImageMealService imageMealService) {
    this.imageMealService = imageMealService;
  }

  @Operation(summary = "public", description = "Send a get request with an id")
  @ApiResponse(responseCode = "200", description = "Image data returned successfully as binary content, with appropriate Content-Type based on the image format.")
  @ApiResponse(responseCode = "404", description = "No image found with the provided ID.")
  @GetMapping("/{id}")
  public ResponseEntity<ByteArrayResource> getImage(@PathVariable Integer id) {
    ImageMeal imageMeal = imageMealService.getImageById(id);
    if (imageMeal == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_TYPE, imageMeal.getContentType())
      .body(new ByteArrayResource(imageMeal.getData()));
  }
}
