package com.api.bonobo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<String> images;
    private Long categoryId;

    public ProductDto(String name, String description, Double price, List<String> images, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.categoryId = categoryId;
    }


    public Product parseToEntity() {
        return Product.builder()
                .name(this.name)
                .description(this.description)
                .category(Category.builder()
                        .id(this.categoryId)
                        .build())
                .price(this.price)
                .images(parseImages())
                .build();
    }

    private List<Image> parseImages() {
        List<Image> listImage = new ArrayList<>();

        this.images.forEach(i -> listImage.add(new Image(i, null)));

        return listImage;
    }
}
