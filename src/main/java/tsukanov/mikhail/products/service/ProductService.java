package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.ProductRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.ProductDTO;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.utils.ReturnBack;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;


    @Transactional
    public ReturnBack<Product> addProduct(ProductDTO productDTO) {
        Optional<ProductType> type = productTypeRepository.findByName(productDTO.getProductType());
        if (type.isEmpty()) {
            return new ReturnBack<>("There is no type " + productDTO.getProductType(),
                    HttpStatus.NOT_FOUND);
        }
        ProductType productType = type.get();
        Product product = productDTO.toProduct();
        product.setProductType(productType);
        productRepository.save(product);
        return null;
    }

    public ReturnBack<List<Product>> findAll() {
        return new ReturnBack<>(productRepository.findAll());
    }


}
