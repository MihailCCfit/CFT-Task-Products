package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.AttributeTypeRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.AttributeTypeDTO;
import tsukanov.mikhail.products.dto.ProductTypeDTO;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.entity.RequiredAttribute;
import tsukanov.mikhail.products.utils.ReturnBack;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductTypeService {
    private ProductTypeRepository productTypeRepository;
    private AttributeTypeRepository attributeTypeRepository;

    public ReturnBack<ProductType> addProductType(ProductTypeDTO productTypeDTO) {
        if (productTypeDTO == null) {
            return new ReturnBack<>("There is no productType", HttpStatus.BAD_REQUEST);
        }
        Optional<ProductType> productType = getProductType(productTypeDTO);
        if (productType.isPresent()) {
            return new ReturnBack<>(productType.get());
        }
        return new ReturnBack<>(productTypeRepository.save(productTypeDTO.toProductType()));
    }

    @Transactional
    public ReturnBack<ProductType> addRequiredAttributeType(String productTypeName, AttributeTypeDTO attributeTypeDTO) {
        if (productTypeName == null) {
            return new ReturnBack<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeDTO == null) {
            return new ReturnBack<>("There is no attributeType", HttpStatus.BAD_REQUEST);
        }
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        productType.get().getRequiredAttributes().add(attributeTypeDTO.toAttributeType());
        return new ReturnBack<>(productType.get());
    }

    @Transactional
    public ReturnBack<ProductType> addRequiredAttributeType(String productTypeName,
                                                            Collection<AttributeTypeDTO> attributeTypeDTOCollection) {
        if (productTypeName == null) {
            return new ReturnBack<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeDTOCollection == null) {
            return new ReturnBack<>("There is no attributeType", HttpStatus.BAD_REQUEST);
        }
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        productType.get().getRequiredAttributes()
                .addAll(attributeTypeDTOCollection
                        .stream()
                        .map(AttributeTypeDTO::toAttributeType)
                        .toList()
                );
        return new ReturnBack<>(productType.get());
    }


    @Transactional
    public ReturnBack<ProductType> removeRequiredAttributeType(String productTypeName,
                                                               String attributeTypeName) {
        if (productTypeName == null) {
            return new ReturnBack<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeName == null) {
            return new ReturnBack<>("There is no attributeTypeName", HttpStatus.BAD_REQUEST);
        }
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        Optional<RequiredAttribute> attributeTypeOptional = attributeTypeRepository
                .findByAttributeName(attributeTypeName);
        if (attributeTypeOptional.isEmpty()) {
            return new ReturnBack<>("There is no such attributeTypeName", HttpStatus.NOT_FOUND);
        }
        RequiredAttribute requiredAttribute = attributeTypeOptional.get();
        productType.get().getRequiredAttributes().remove(requiredAttribute);
        return new ReturnBack<>(productType.get());
    }

    @Transactional
    private ReturnBack<ProductType> removeRequiredAttributeType(ProductType productType,
                                                                String attributeTypeName) {
        if (productType == null) {
            return new ReturnBack<>("There is no productType", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeName == null) {
            return new ReturnBack<>("There is no attributeTypeName", HttpStatus.BAD_REQUEST);
        }
        Optional<RequiredAttribute> attributeTypeOptional = attributeTypeRepository
                .findByAttributeName(attributeTypeName);
        if (attributeTypeOptional.isEmpty()) {
            return new ReturnBack<>("There is no such attributeTypeName", HttpStatus.NOT_FOUND);
        }
        RequiredAttribute requiredAttribute = attributeTypeOptional.get();
        productType.getRequiredAttributes().remove(requiredAttribute);
        return new ReturnBack<>(productType);
    }

    @Transactional
    public ReturnBack<ProductType> removeRequiredAttributeType(String productTypeName,
                                                               Collection<String>
                                                                       attributeTypeNames) {
        if (productTypeName == null) {
            return new ReturnBack<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        if (attributeTypeNames == null) {
            return new ReturnBack<>("There is no attributes", HttpStatus.BAD_REQUEST);
        }


        Optional<ProductType> productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        for (String attributeTypeName : attributeTypeNames) {
            removeRequiredAttributeType(productTypeName, attributeTypeName);
        }
        return new ReturnBack<>(productType.get());
    }

    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }

    @Transactional
    public ReturnBack<ProductType> remove(String productTypeName) {
        if (productTypeName == null) {
            return new ReturnBack<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        var pt = getProductType(productTypeName);
        if (pt.isEmpty()) {
            return new ReturnBack<>("There is no " + productTypeName, HttpStatus.ACCEPTED);
        }
        productTypeRepository.delete(pt.get());
        return new ReturnBack<>(pt.get());
    }


    private Optional<ProductType> getProductType(String name) {
        return productTypeRepository.findByName(name);
    }

    private Optional<ProductType> getProductType(ProductTypeDTO productTypeDTO) {
        return productTypeRepository.findByName(productTypeDTO.getName());
    }

    public ReturnBack<Set<RequiredAttribute>> findRequiredAttributes(String productTypeName) {
        if (productTypeName == null) {
            return new ReturnBack<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no such product type: " + productTypeName,
                    HttpStatus.NOT_FOUND);
        }
        return new ReturnBack<>(productType.get().getRequiredAttributes());
    }

    public ReturnBack<Set<Product>> findAllProductsByType(String productTypeName) {
        if (productTypeName == null) {
            return new ReturnBack<>("There is no productTypeName", HttpStatus.BAD_REQUEST);
        }
        Optional<ProductType> productTypeOptional = getProductType(productTypeName);
        if (productTypeOptional.isEmpty()) {
            return new ReturnBack<>("There is no such product type: " + productTypeName,
                    HttpStatus.NOT_FOUND);
        }
        return new ReturnBack<>(productTypeOptional.get().getProducts());
    }


}
