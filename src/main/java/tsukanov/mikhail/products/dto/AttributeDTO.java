package tsukanov.mikhail.products.dto;

import lombok.Data;
import tsukanov.mikhail.products.entity.Attribute;

@Data
public class AttributeDTO {
    private String attributeType;
    private String value;

    public Attribute toAttribute() {
        return new Attribute(attributeType, value);
    }
}
