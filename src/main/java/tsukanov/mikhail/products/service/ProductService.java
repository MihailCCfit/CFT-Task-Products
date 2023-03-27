package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.ProductRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.ProductDTO;
import tsukanov.mikhail.products.entity.Attribute;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.entity.RequiredAttribute;
import tsukanov.mikhail.products.utils.Maybe;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;


    @Transactional
    public Maybe<Product> addProduct(ProductDTO productDTO) {

        if (productDTO.getProductType() == null) {
            return new Maybe<>("There is no type",
                    HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getPrice() == null) {
            return new Maybe<>("There is no price",
                    HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getAttributes() == null) {
            return new Maybe<>("There is no attributes",
                    HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getAmount() == null) {
            return new Maybe<>("There is no amount",
                    HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getManufacturer() == null) {
            return new Maybe<>("There is no manufacturer",
                    HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getSerialNumber() == null) {
            return new Maybe<>("There is no serial number",
                    HttpStatus.BAD_REQUEST);
        }

        Optional<ProductType> type = productTypeRepository.findByName(productDTO.getProductType());
        if (type.isEmpty()) {
            return new Maybe<>("There is no type " + productDTO.getProductType(),
                    HttpStatus.NOT_FOUND);
        }
        ProductType productType = type.get();


        Product product = productDTO.toProduct();


        Set<String> productAttributes = product.getAttributes().stream().map(Attribute::getAttributeName)
                .collect(Collectors.toSet());
        Set<RequiredAttribute> requiredAttributes = productType.getRequiredAttributes().stream()
                .filter(requiredAttribute -> !productAttributes.contains(requiredAttribute.getAttributeName()))
                .collect(Collectors.toSet());
        if (requiredAttributes.isEmpty()) {
            productType.getProducts().add(product);
            product.setProductType(productType);
            productRepository.save(product);
            return new Maybe<>(product);
        }
        return new Maybe<>("There is no next requiredAttributes: " + requiredAttributes, HttpStatus.BAD_REQUEST);
    }

    public Maybe<Product> findById(Long id) {
        if (id == null) {
            return new Maybe<>("There is no id", HttpStatus.BAD_REQUEST);
        }
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new Maybe<>("There is no product with id: " + id, HttpStatus.NOT_FOUND);
        }

        return new Maybe<>(optionalProduct.get());
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Maybe<Product> removeProduct(Long id) {
        assert id != null;
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return new Maybe<>("There is no product by id: " + id,
                    HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();
        product.getProductType().getProducts().remove(product);
        productRepository.delete(product);
        return new Maybe<>(product);
    }

    public Maybe<Product> updateProduct() {
        //TODO: smart system for updating attributes, maybe with map and another for remove/add attributes
        return null;
    }


    public Maybe<Product> removeProduct(Product product) {
        if (product == null) {
            return new Maybe<>("There is no product",
                    HttpStatus.BAD_REQUEST);
        }
        return removeProduct(product.getId());
    }


}
