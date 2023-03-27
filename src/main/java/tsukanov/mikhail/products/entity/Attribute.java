package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String attributeType;

    @NotNull
    private String aValue;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    public Attribute(String attributeType, @NotNull String value, Product product) {
        this.attributeType = attributeType;
        this.aValue = value;
        this.product = product;
    }

    public Attribute(String attributeType, @NotNull String value) {
        this.attributeType = attributeType;
        this.aValue = value;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "attributeType='" + attributeType + '\'' +
                ", aValue='" + aValue + '\'' +
                '}';
    }
}
