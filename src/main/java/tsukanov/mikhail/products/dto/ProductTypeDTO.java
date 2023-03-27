package tsukanov.mikhail.products.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import tsukanov.mikhail.products.entity.AttributeType;
import tsukanov.mikhail.products.entity.ProductType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class ProductTypeDTO {
    @NotNull
    private String name;
    private Set<AttributeType> requiredAttributeTypes;

    public ProductTypeDTO(@NotNull String name) {
        this.name = name;
        requiredAttributeTypes = new HashSet<>();
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
        return new ProductType(name, requiredAttributeTypes);
    }
}
