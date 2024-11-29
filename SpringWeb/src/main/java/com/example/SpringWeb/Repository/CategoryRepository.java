package com.example.SpringWeb.Repository;

import com.example.SpringWeb.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category getByCategoryId(Integer categoryId);
}
