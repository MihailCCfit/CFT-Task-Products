package tsukanov.mikhail.products.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tsukanov.mikhail.products.dao.AttributeTypeRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.dto.AttributeTypeDTO;
import tsukanov.mikhail.products.entity.AttributeType;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.utils.ReturnBack;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AttributeTypeService {
    private AttributeTypeRepository attributeTypeRepository;
    private ProductTypeRepository productTypeRepository;

    public ReturnBack<AttributeType> addAttributeType(AttributeTypeDTO attributeTypeDTO) {
        Optional<AttributeType> attributeType = attributeTypeRepository
                .findByAttributeTypeName(attributeTypeDTO.getAttributeTypeName());
        if (attributeType.isPresent()) {
            return new ReturnBack<>(attributeType.get());
        }
        return new ReturnBack<>(attributeTypeRepository.save(attributeTypeDTO.toAttributeType()));
    }

    public ReturnBack<List<AttributeType>> findAll() {
        return new ReturnBack<>(attributeTypeRepository.findAll());
    }

    @Transactional
    public ReturnBack<AttributeType> removeAttributeTypeByName(String attributeTypeName) {
        Optional<AttributeType> attributeTypeOptional = attributeTypeRepository
                .findByAttributeTypeName(attributeTypeName);
        if (attributeTypeOptional.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        AttributeType attributeType = attributeTypeOptional.get();
        Set<ProductType> productType = attributeType.getProductTypes();
        productType.forEach((prT) -> prT.getRequiredAttributeTypes().remove(attributeType));
        attributeType.getAttributes().forEach(attribute -> attribute.setAttributeType(null));
        return new ReturnBack<>(attributeType);
    }

    @Transactional
    public ReturnBack<AttributeType> updateAttributeType(AttributeTypeDTO attributeTypeDTO) {
        Optional<AttributeType> attributeTypeOptional = attributeTypeRepository
                .findByAttributeTypeName(attributeTypeDTO.getAttributeTypeName());
        if (attributeTypeOptional.isEmpty()) {
            return new ReturnBack<>("There is no productType", HttpStatus.NOT_FOUND);
        }
        AttributeType attributeType = attributeTypeOptional.get();

        if (attributeType.getType() != null) {
            attributeType.setType(attributeType.getType());
        }
        if (attributeType.getAttributeTypeName() != null) {
            attributeType.setAttributeTypeName(attributeType.getAttributeTypeName());
        }
        return new ReturnBack<>(attributeTypeRepository.save(attributeTypeDTO.toAttributeType()));
    }


}
