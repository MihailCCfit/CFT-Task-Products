package tsukanov.mikhail.products.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.ProductRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.ProductDTO;
import tsukanov.mikhail.products.entity.Attribute;
import tsukanov.mikhail.products.entity.AttributeType;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.utils.ReturnBack;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;

    public ReturnBack<Product> addProduct(ProductDTO productDTO) {
        Optional<ProductType> type = productTypeRepository.findByName(productDTO.getProductType().getName());
        if (type.isEmpty()) {
            return new ReturnBack<>("There is no type " + productDTO.getProductType(),
                    HttpStatus.NOT_FOUND);
        }
        ProductType productType = type.get();
        productType.


        Product product = productDTO.toProduct();
        productRepository.save(product);
    }


}
