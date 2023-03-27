package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String attributeType;

    private String attributeName;

    @NotNull
    private String aValue;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Product product;

    public Attribute(String attributeType, @NotNull String value, Product product) {
        this.attributeType = attributeType;
        this.aValue = value;
        this.product = product;
    }

    public Attribute(String attributeType, String attributeName, @NotNull String aValue) {
        this.attributeType = attributeType;
        this.attributeName = attributeName;
        this.aValue = aValue;
    }

    public Attribute(String attributeName) {
        this.attributeType = attributeName;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "attributeType='" + attributeType + '\'' +
                ", aValue='" + aValue + '\'' +
                '}';
    }
}
