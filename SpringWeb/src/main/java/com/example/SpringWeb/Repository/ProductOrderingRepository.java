package com.example.SpringWeb.Repository;

import com.example.SpringWeb.Model.ProductOrdering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderingRepository extends JpaRepository<ProductOrdering, Integer> {
}
