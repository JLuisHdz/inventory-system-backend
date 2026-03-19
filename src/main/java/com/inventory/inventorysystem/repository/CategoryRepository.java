package com.inventory.inventorysystem.repository;

import com.inventory.inventorysystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public class CategoryRepository extends JpaRepository<Category, Long> {
}
