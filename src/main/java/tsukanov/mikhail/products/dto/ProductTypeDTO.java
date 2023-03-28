package tsukanov.mikhail.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tsukanov.mikhail.products.entity.ProductType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class ProductTypeDTO {
    private String name;
    private Set<AttributeTypeDTO> requiredAttributes;

    public ProductTypeDTO(@JsonProperty("name") String name,
                          @JsonProperty("requiredAttributes") Set<AttributeTypeDTO> requiredAttributes) {
        this.name = name;
        this.requiredAttributes = requiredAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTypeDTO that = (ProductTypeDTO) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public ProductType toProductType() {
        if (requiredAttributes != null) {
            return new ProductType(name, requiredAttributes.stream()
                    .map(AttributeTypeDTO::toAttributeType)
                    .collect(Collectors.toSet()));
        } else {
            return new ProductType(name, new HashSet<>());
        }

    }
}
