package com.api.bonobo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bonobo.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
