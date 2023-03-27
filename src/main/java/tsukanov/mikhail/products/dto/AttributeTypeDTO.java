package tsukanov.mikhail.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tsukanov.mikhail.products.entity.RequiredAttribute;

@Data
@AllArgsConstructor
public class AttributeTypeDTO {
    private String attributeTypeName;

    public RequiredAttribute toAttributeType() {
        return new RequiredAttribute(attributeTypeName);
    }
}
