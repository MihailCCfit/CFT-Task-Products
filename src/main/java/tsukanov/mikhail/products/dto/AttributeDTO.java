package tsukanov.mikhail.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tsukanov.mikhail.products.entity.Attribute;

@Data
@AllArgsConstructor
public class AttributeDTO {
    private String attributeType;
    private String attributeName;

    private String value;
    public Attribute toAttribute() {
        return new Attribute(attributeType, attributeName, value);
    }
}
