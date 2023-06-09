package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.AttributeTypeRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.AttributeTypeDTO;
import tsukanov.mikhail.products.dto.ProductTypeDTO;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.entity.RequiredAttribute;
import tsukanov.mikhail.products.utils.Maybe;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ProductTypeService {
    private ProductTypeRepository productTypeRepository;
    private AttributeTypeRepository attributeTypeRepository;

    /**
     * Add new product type. If it's exists then do nothing
     *
     * @param productTypeDTO productType
     * @return maybe product type, that contained or was saved
     */
    public Maybe<ProductType> addProductType(ProductTypeDTO productTypeDTO) {
        log.info("add product type {}", productTypeDTO);
        if (productTypeDTO == null) {
            return new Maybe<>("There is no productType", HttpStatus.BAD_REQUEST);
        }
        if (productTypeDTO.getName() == null) {
            return new Maybe<>("There is no name for product type", HttpStatus.BAD_REQUEST);
        }
        return getProductType(productTypeDTO)
                .map(Maybe::new)
                .orElseGet(() -> new Maybe<>(
                        productTypeRepository.save(productTypeDTO.toProductType())
                ));
    }

    @Transactional
    public Maybe<ProductType> addRequiredAttribute(String productTypeName, AttributeTypeDTO attributeTypeDTO) {
        log.info("Add to {} the attribute: {}", productTypeName, attributeTypeDTO);
        if (productTypeName == null) {
            return new Maybe<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeDTO == null || attributeTypeDTO.getAttributeName() == null) {
            return new Maybe<>("There is no attributeType", HttpStatus.BAD_REQUEST);
        }
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new Maybe<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        productType.get().getRequiredAttributes().add(attributeTypeDTO.toAttributeType());
        return new Maybe<>(productType.get());
    }

    @Transactional
    public Maybe<ProductType> addRequiredAttribute(String productTypeName,
                                                   Collection<AttributeTypeDTO> attributeTypeDTOCollection) {
        if (productTypeName == null) {
            return new Maybe<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeDTOCollection == null) {
            return new Maybe<>("There is no attributeType", HttpStatus.BAD_REQUEST);
        }
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new Maybe<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        productType.get().getRequiredAttributes()
                .addAll(attributeTypeDTOCollection
                        .stream()
                        .map(AttributeTypeDTO::toAttributeType)
                        .toList()
                );
        return new Maybe<>(productType.get());
    }


    @Transactional
    public Maybe<ProductType> removeRequiredAttribute(String productTypeName,
                                                      String attributeTypeName) {
        if (productTypeName == null) {
            return new Maybe<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeName == null) {
            return new Maybe<>("There is no attributeTypeName", HttpStatus.BAD_REQUEST);
        }
        log.info("remove required attribute");
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new Maybe<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        Optional<RequiredAttribute> attributeTypeOptional = attributeTypeRepository
                .findByAttributeName(attributeTypeName);
        if (attributeTypeOptional.isEmpty()) {
            return new Maybe<>("There is no such attributeTypeName", HttpStatus.NOT_FOUND);
        }
        RequiredAttribute requiredAttribute = attributeTypeOptional.get();
        productType.get().getRequiredAttributes().remove(requiredAttribute);
        return new Maybe<>(productType.get());
    }

    @Transactional
    private Maybe<ProductType> removeRequiredAttribute(ProductType productType,
                                                       String attributeTypeName) {
        if (productType == null) {
            return new Maybe<>("There is no productType", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeName == null) {
            return new Maybe<>("There is no attributeTypeName", HttpStatus.BAD_REQUEST);
        }
        Optional<RequiredAttribute> attributeTypeOptional = attributeTypeRepository
                .findByAttributeName(attributeTypeName);
        if (attributeTypeOptional.isEmpty()) {
            return new Maybe<>("There is no such attributeTypeName", HttpStatus.NOT_FOUND);
        }
        RequiredAttribute requiredAttribute = attributeTypeOptional.get();
        productType.getRequiredAttributes().remove(requiredAttribute);
        return new Maybe<>(productType);
    }

    @Transactional
    public Maybe<ProductType> removeRequiredAttribute(String productTypeName,
                                                      Collection<String>
                                                                  attributeTypeNames) {
        if (productTypeName == null) {
            return new Maybe<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeNames == null) {
            return new Maybe<>("There is no attributes", HttpStatus.BAD_REQUEST);
        }


        Optional<ProductType> productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new Maybe<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        for (String attributeTypeName : attributeTypeNames) {
            removeRequiredAttribute(productTypeName, attributeTypeName);
        }
        return new Maybe<>(productType.get());
    }

    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }

    @Transactional
    public Maybe<ProductType> remove(String productTypeName) {
        if (productTypeName == null) {
            return new Maybe<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        var pt = getProductType(productTypeName);
        if (pt.isEmpty()) {
            return new Maybe<>("There is no " + productTypeName, HttpStatus.ACCEPTED);
        }
        productTypeRepository.delete(pt.get());
        return new Maybe<>(pt.get());
    }


    private Optional<ProductType> getProductType(String name) {
        log.info("find product type by name: {}", name);
        return productTypeRepository.findByName(name);
    }

    private Optional<ProductType> getProductType(ProductTypeDTO productTypeDTO) {
        return productTypeRepository.findByName(productTypeDTO.getName());
    }

    public Maybe<Set<RequiredAttribute>> findRequiredAttributes(String productTypeName) {
        if (productTypeName == null) {
            return new Maybe<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        log.info("find all required attributes by type: {}", productTypeName);
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new Maybe<>("There is no such product type: " + productTypeName,
                    HttpStatus.NOT_FOUND);
        }
        return new Maybe<>(productType.get().getRequiredAttributes());
    }

    public Maybe<Set<Product>> findAllProductsByType(String productTypeName) {
        if (productTypeName == null) {
            return new Maybe<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        log.info("find all products by type: {}", productTypeName);
        Optional<ProductType> productTypeOptional = getProductType(productTypeName);
        if (productTypeOptional.isEmpty()) {
            return new Maybe<>("There is no such product type: " + productTypeName,
                    HttpStatus.NOT_FOUND);
        }
        return new Maybe<>(productTypeOptional.get().getProducts());
    }


}