package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Long serialNumber;
    @NotNull
    private Double price;
    @NotNull
    private Long amount;
    @NotNull
    private String manufacturer;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private ProductType productType;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attribute> attributes;

    public Product(@NotNull Long serialNumber,
                   @NotNull Long amount,
                   @NotNull Double price,
                   @NotNull String manufacturer,
                   ProductType productType,
                   Set<Attribute> attributes) {
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.price = price;
        this.manufacturer = manufacturer;
        this.productType = productType;
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", amount=" + amount +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", productType=" + productType.getName() +
                ", attributes=" + attributes +
                '}';
    }
}
