package tsukanov.mikhail.products.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import tsukanov.mikhail.products.entity.Product;

import java.util.Set;
import java.util.stream.Collectors;


@Setter
@Getter
public class ProductDTO {

    private Long serialNumber;

    private Long amount;

    private Double price;

    private String manufacturer;

    private String productType;
    private Set<AttributeDTO> attributes;

    public Product toProduct() {
        return new Product(serialNumber, amount, price, manufacturer, null,
                attributes.stream().map(AttributeDTO::toAttribute)
                        .collect(Collectors.toSet()));
    }


    public ProductDTO(@NotNull Long serialNumber, @NotNull Long amount,
                      Double price,
                      @NotNull String manufacturer, @NotNull String productType, Set<AttributeDTO> attributes) {
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.price = price;
        this.manufacturer = manufacturer;
        this.productType = productType;
        this.attributes = attributes;
    }

}
