package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.AttributeTypeDTO;
import tsukanov.mikhail.products.dto.ProductTypeDTO;
import tsukanov.mikhail.products.entity.AttributeType;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.utils.ReturnBack;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductTypeService {
    private ProductTypeRepository productTypeRepository;

    public ReturnBack<ProductType> addProductType(ProductTypeDTO productTypeDTO) {
        Optional<ProductType> productType = getProductType(productTypeDTO);
        if (productType.isPresent()) {
            return new ReturnBack<>(productType.get());
        }
        return new ReturnBack<>(productTypeRepository.save(productTypeDTO.toProductType()));
    }

    @Transactional
    public ReturnBack<ProductType> addRequiredAttributeType(String productTypeName, AttributeTypeDTO attributeTypeDTO) {
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        productType.get().getRequiredAttributeTypes().add(attributeTypeDTO.toAttributeType());
        return new ReturnBack<>(productType.get());
    }

    @Transactional
    public ReturnBack<ProductType> addRequiredAttributeType(String productTypeName,
                                                            Collection<AttributeTypeDTO> attributeTypeDTOCollection) {
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        productType.get().getRequiredAttributeTypes()
                .addAll(attributeTypeDTOCollection
                        .stream()
                        .map(AttributeTypeDTO::toAttributeType)
                        .toList()
                );
        return new ReturnBack<>(productType.get());
    }


    @Transactional
    public ReturnBack<ProductType> removeRequiredAttributeType(String productTypeName,
                                                               AttributeTypeDTO attributeTypeDTO) {
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        productType.get().getRequiredAttributeTypes().remove(attributeTypeDTO.toAttributeType());
        return new ReturnBack<>(productType.get());
    }

    @Transactional
    public ReturnBack<ProductType> removeRequiredAttributeType(String productTypeName,
                                                               Collection<AttributeTypeDTO>
                                                                       attributeTypeDTOCollection) {
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        attributeTypeDTOCollection
                .stream()
                .map(AttributeTypeDTO::toAttributeType)
                .toList()
                .forEach(productType.get().getRequiredAttributeTypes()::remove
                );
        return new ReturnBack<>(productType.get());
    }

    public ReturnBack<List<ProductType>> getAll() {
        return new ReturnBack<>(productTypeRepository.findAll());
    }

    public ReturnBack<ProductType> remove(String productTypeName) {
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

    public ReturnBack<Set<AttributeType>> requiredAttribute(String productTypeName) {
        var productType = getProductType(productTypeName);
        if (productType.isEmpty()) {
            return new ReturnBack<>("There is no such product type: " + productTypeName,
                    HttpStatus.NOT_FOUND);
        }
        return new ReturnBack<>(productType.get().getRequiredAttributeTypes());
    }


}
