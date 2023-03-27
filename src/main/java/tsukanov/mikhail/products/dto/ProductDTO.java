package tsukanov.mikhail.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import tsukanov.mikhail.products.entity.Product;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProductDTO {
    @NotNull
    private Long serialNumber;
    @NotNull
    private Long amount;
    @NotNull
    private String manufacturer;
    @NotNull
    private String productType;
    private Set<AttributeDTO> attributes;

    public Product toProduct() {
        return new Product(serialNumber, amount, manufacturer, null,
                attributes.stream().map(AttributeDTO::toAttribute)
                        .collect(Collectors.toSet()));
    }

    public ProductDTO(@NotNull Long serialNumber, @NotNull Long amount, @NotNull String manufacturer, @NotNull String productType) {
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.manufacturer = manufacturer;
        this.productType = productType;
        attributes = new HashSet<>();
    }
}
