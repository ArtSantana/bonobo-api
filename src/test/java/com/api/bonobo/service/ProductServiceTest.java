package com.api.bonobo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.quality.Strictness;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import com.api.bonobo.exception.InvalidFieldsException;
import com.api.bonobo.model.Category;
import com.api.bonobo.model.Product;
import com.api.bonobo.repository.ProductRepository;

@ExtendWith({MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    CategoryService categoryService;

    @BeforeEach
    void setup() {
        Mockito.when(categoryService.findById(Mockito.anyLong())).thenReturn(new Category(1L, "Category test"));
    }

    @Test
    void shouldFailCreateProductWithoutName() {
        Product product = createMockProduct();
        product.setName("");

        Assertions.assertThatThrownBy(() -> productService.create(product)).isInstanceOf(InvalidFieldsException.class);
    }

    @Test
    void shouldFailCreateProductWithoutDescription() {
        Product product = createMockProduct();
        product.setDescription("");

        Assertions.assertThatThrownBy(() -> productService.create(product)).isInstanceOf(InvalidFieldsException.class);
    }

    @Test
    void shouldFailCreateProductWithoutPrice() {
        Product product = createMockProduct();
        product.setPrice(null);

        Assertions.assertThatThrownBy(() -> productService.create(product)).isInstanceOf(InvalidFieldsException.class);
    }

    @Test
    void shouldFailCreateProductWithoutCategory() {
        Product product = createMockProduct();
        product.setCategory(null);

        Assertions.assertThatThrownBy(() -> productService.create(product)).isInstanceOf(InvalidFieldsException.class);
    }

    @Test
    void shouldCreateProduct() {
        Assertions.assertThatCode(() -> productService.create(createMockProduct())).doesNotThrowAnyException();
    }
    private Product createMockProduct() {
       return Product.builder()
               .id(1L)
               .name("Product")
               .categoryId(1L)
               .description("Description")
               .price(1.99)
               .build();
    }
}
