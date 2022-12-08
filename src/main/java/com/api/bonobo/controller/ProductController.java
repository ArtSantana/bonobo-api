package com.api.bonobo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.bonobo.model.Product;
import com.api.bonobo.model.ProductDto;
import com.api.bonobo.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        if (categoryId != null) {
            return ResponseEntity.ok().body(productService.findByCategory(categoryId));
        }
        return ResponseEntity.ok().body(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductDto productDto) {
        Product product = productDto.parseToEntity();
        productService.create(product);

        return ResponseEntity.created(URI.create("/products/" + productDto.getId())).body("Created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        productService.update(product);

        return ResponseEntity.ok().body("Updated");
    }
}
