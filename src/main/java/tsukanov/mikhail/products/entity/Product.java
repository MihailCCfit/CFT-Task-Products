package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Long serialNumber;
    @NotNull
    private Long amount;
    @NotNull
    private String manufacturer;
    @ManyToOne
    private ProductType productType;
    @OneToMany
    private Set<Attribute> attributes;

    public Product(@NotNull Long serialNumber,
                   @NotNull Long amount,
                   @NotNull String manufacturer,
                   ProductType productType,
                   Set<Attribute> attributes) {
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.manufacturer = manufacturer;
        this.productType = productType;
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return serialNumber.equals(product.serialNumber) && manufacturer.equals(product.manufacturer) && Objects.equals(productType, product.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, manufacturer, productType);
    }
}
