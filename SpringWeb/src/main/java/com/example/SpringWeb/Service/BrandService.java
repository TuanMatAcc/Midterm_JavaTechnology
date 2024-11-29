package com.example.SpringWeb.Service;

import com.example.SpringWeb.Model.Brand;
import com.example.SpringWeb.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Integer brandId) {
        return brandRepository.getByBrandId(brandId);
    }
}
