package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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

        productType.getProducts().add(product);

        productRepository.save(product);
        //TODO: check required attributes
        return new ReturnBack<>(product);
    }

    public ReturnBack<Product> findById(Long id) {
        if (id == null) {
            return new ReturnBack<>("There is no id", HttpStatus.BAD_REQUEST);
        }
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ReturnBack<>("There is no product with id: " + id, HttpStatus.NOT_FOUND);
        }

        return new ReturnBack<>(optionalProduct.get());
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public ReturnBack<Product> removeProduct(Long id) {
        assert id != null;
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return new ReturnBack<>("There is no product by id: " + id,
                    HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();
        product.getProductType().getProducts().remove(product);
        productRepository.delete(product);
        return new ReturnBack<>(product);
    }

    public ReturnBack<Product> updateProduct() {
        //TODO: smart system for updating attributes, maybe with map and another for remove/add attributes
        return null;
    }


    public ReturnBack<Product> removeProduct(Product product) {
        return removeProduct(product.getId());
    }


}
