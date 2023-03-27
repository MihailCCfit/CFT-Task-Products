package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.Generated;
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

    @ManyToOne
    @Column(unique = true)
    private AttributeType attributeType;

    @NotNull
    private String value;

    @ManyToOne
    private Product product;

    public Attribute(AttributeType attributeType, @NotNull String value, Product product) {
        this.attributeType = attributeType;
        this.value = value;
        this.product = product;
    }

    public Attribute(AttributeType attributeType, @NotNull String value) {
        this.attributeType = attributeType;
        this.value = value;
    }


}
