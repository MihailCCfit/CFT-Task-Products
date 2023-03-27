package tsukanov.mikhail.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tsukanov.mikhail.products.entity.AttributeType;

@Data
@AllArgsConstructor
public class AttributeTypeDTO {
    private String attributeTypeName;
    private String Type;

    public AttributeType toAttributeType() {
        return new AttributeType(attributeTypeName, Type);
    }
}
