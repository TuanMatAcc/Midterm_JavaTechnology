package com.example.SpringWeb.Repository;

import com.example.SpringWeb.Model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    public Brand getByBrandId(Integer brandId);
}
