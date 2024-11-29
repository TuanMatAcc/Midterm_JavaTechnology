package com.example.SpringWeb.Service;

import com.example.SpringWeb.Model.ImageProduct;
import com.example.SpringWeb.Model.Product;
import com.example.SpringWeb.Repository.ImageProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageProductService {
    @Autowired
    ImageProductRepository imageProductRepository;

    public ImageProductService() {
    }

    public ImageProductService(ImageProductRepository imageProductRepository) {
        this.imageProductRepository = imageProductRepository;
    }

    public void saveImagesProduct(List<ImageProduct> imagesProduct) {
        imageProductRepository.saveAll(imagesProduct);
    }

    public List<ImageProduct> getImagesProductById(Product product) {
        return imageProductRepository.findByProduct(product);
    }

    public void deleteImagesProduct(List<Integer> imageProductIds) {
        imageProductRepository.deleteAllById(imageProductIds);
    }

    public void deleteImagesProductByProduct(Product product) {
        imageProductRepository.deleteByProduct(product);
    }
}
