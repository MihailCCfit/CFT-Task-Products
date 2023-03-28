package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.ProductRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.AttributeDTO;
import tsukanov.mikhail.products.dto.ProductDTO;
import tsukanov.mikhail.products.entity.Attribute;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.entity.RequiredAttribute;
import tsukanov.mikhail.products.utils.Maybe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    //TODO: Validating and sending more information about somethings
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;


    @Transactional
    public Maybe<Product> addProduct(ProductDTO productDTO) {
        log.info("Adding product {}", productDTO);

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
        log.info("Find product by id: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new Maybe<>("There is no product with id: " + id, HttpStatus.NOT_FOUND);
        }

        return new Maybe<>(optionalProduct.get());
    }

    public List<Product> findAll() {
        log.info("Find all products");
        return productRepository.findAll();
    }

    @Transactional
    public Maybe<Product> removeProduct(Long id) {
        if (id == null) {
            return new Maybe<>("There is no id", HttpStatus.BAD_REQUEST);
        }
        log.info("Remove product by id: {}", id);
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


    @Transactional
    public Maybe<Product> updateProduct(ProductUpdate productUpdate) {
        log.info("update product: {}", productUpdate);
        if (productUpdate.id() == null) {
            return new Maybe<>("There is no id", HttpStatus.BAD_REQUEST);
        }
        Optional<Product> productOptional = productRepository.findById(productUpdate.id());
        if (productOptional.isEmpty()) {
            return new Maybe<>("There is no product with id: " + productUpdate.id(), HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();
        if (productUpdate.amount() != null) {
            product.setAmount(productUpdate.amount());
        }
        if (productUpdate.price() != null) {
            product.setPrice(productUpdate.price());
        }
        if (productUpdate.manufacturer() != null) {
            product.setManufacturer(productUpdate.manufacturer());
        }
        if (productUpdate.serialNumber() != null) {
            product.setSerialNumber(product.getSerialNumber());
        }

        if (productUpdate.attributeUpdates() != null) {
            Set<AttributeUpdate> updates = productUpdate.attributeUpdates();

            for (AttributeUpdate update : updates) {
                if (update.attribute() == null
                        || update.attribute().getAttributeName() == null
                        || update.changeType() == null) {
                    continue;
                }

                if (update.changeType().equalsIgnoreCase("remove")) {
                    product.getAttributes()
                            .removeIf(attribute -> attribute.getAttributeName()
                                    .equals(update.attribute().getAttributeName()
                                    ) && !product.getProductType().getRequiredAttributes()
                                    .contains(new RequiredAttribute(attribute.getAttributeName())));
                }
                if (update.changeType().equalsIgnoreCase("change")) {
                    AttributeDTO attributeDTO = update.attribute();
                    product.getAttributes().stream()
                            .filter(attribute -> attribute.getAttributeName().equals(attributeDTO.getAttributeName()))
                            .forEach(attribute -> {
                                if (attributeDTO.getValue() != null) {
                                    attribute.setAValue(attributeDTO.getValue());
                                }
                                if (attributeDTO.getAttributeType() != null) {
                                    attribute.setAttributeType(attributeDTO.getAttributeType());
                                }
                            });
                }
                if (update.changeType().equalsIgnoreCase("add")) {
                    AttributeDTO attributeDTO = update.attribute();
                    product.getAttributes().add(attributeDTO.toAttribute());
                }
            }
        }


        if (productUpdate.productType() != null) {
            Optional<ProductType> productTypeOptional = productTypeRepository.findByName(productUpdate.productType());
            if (productTypeOptional.isEmpty()) {
                return new Maybe<>("There is no such productType: " + productUpdate.productType(),
                        HttpStatus.NOT_FOUND);
            }
            ProductType productType = productTypeOptional.get();
            product.getProductType().getProducts().remove(product);
            productType.getProducts().add(product);
        }


        return new Maybe<>(product);
    }


}
