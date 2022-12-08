package com.api.bonobo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.bonobo.exception.InternalServerErrorException;
import com.api.bonobo.exception.InvalidFieldsException;
import com.api.bonobo.exception.NotFoundException;
import com.api.bonobo.model.Image;
import com.api.bonobo.model.Product;
import com.api.bonobo.repository.ImageRepository;
import com.api.bonobo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
   private final ProductRepository productRepository;
   private final CategoryService categoryService;
   private final ImageRepository imageRepository;
   private final Logger logger = LogManager.getLogger(ProductService.class.getName());

   public Optional<Product> findById(Long id) {
       return productRepository.findById(id);
   }

   public List<Product> findAll() {
      return productRepository.findAll();
   }

   public void create(Product product) {
      validateFields(product);

      try {
         product.setHasStock(true);
         productRepository.save(product);
      } catch(Exception e) {
         logger.error(e.getMessage());
         throw new InternalServerErrorException();
      }
      logger.info(String.format("Created product %s, with ID: %s", product.getName(), product.getId()));
   }

   private void validateFields(Product product) {
      List<String> messages = new ArrayList<>();

      if (product.getCategory() == null) {
         messages.add("Product must have a category!");
      } else if (categoryService.findById(product.getCategory().getId()) == null) {
         messages.add("This category does not exist!");
      }

      if (product.getPrice() == null || product.getPrice() <= 0) {
         messages.add("Price must be greather than zero!");
      }

      if (product.getName() == null || product.getName().isBlank()) {
         messages.add("Products must have a name!");
      }

      if (product.getDescription() == null || product.getDescription().isBlank()) {
         messages.add("Products must have a description!");
      }

      if (!messages.isEmpty()) {
         throw new InvalidFieldsException(messages);
      }
   }

   private List<Image> handleImagesForCreation(Product product) {
     return product.getImages().stream().map(i -> new Image(null, i.getValue(), product)).toList();
   }

   public void deleteById(Long id) {
      validateExists(id);

      try {
         productRepository.deleteById(id);
      } catch (Exception ex) {
         throw new InternalServerErrorException();
      }
   }

   public void update(Product product) {
      validateExists(product.getId());
      validateFields(product);

      try {
         productRepository.save(product);
      } catch (Exception ex) {
         throw new InternalServerErrorException();
      }
   }

   private void validateExists(Long id) {
      if (!productRepository.existsById(id)) {
         throw new NotFoundException("This product does not exists");
      }
   }

   public Boolean outOfStock(Long id) {
     Product product = productRepository.getReferenceById(id);

     return !product.getHasStock();
   }

    public List<Product> findByCategory(Long categoryId) {
      return productRepository.findByCategoryId(categoryId);
    }
}
