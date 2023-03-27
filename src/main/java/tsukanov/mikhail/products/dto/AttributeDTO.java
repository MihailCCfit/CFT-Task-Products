package tsukanov.mikhail.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tsukanov.mikhail.products.entity.Attribute;

import java.util.Objects;

@Data
@AllArgsConstructor
public class AttributeDTO {
    private String attributeType;
    private String attributeName;

    private String value;

    public Attribute toAttribute() {
        return new Attribute(attributeType, attributeName, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeDTO that = (AttributeDTO) o;
        return Objects.equals(attributeName, that.attributeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName);
    }
}
