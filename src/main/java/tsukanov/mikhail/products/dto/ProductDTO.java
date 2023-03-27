package tsukanov.mikhail.products.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProductDTO {
    private Long serialNumber;
    @NotNull
    private Long amount;
    @NotNull
    private String manufacturer;
    private String productType;
    private Set<AttributeDTO> attributes;

    public Product toProduct() {
        return new Product(serialNumber, amount, manufacturer, new ProductType(productType),
                attributes.stream().map(AttributeDTO::toAttribute)
                        .collect(Collectors.toSet()));
    }
}
