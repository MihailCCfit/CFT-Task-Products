package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = EAGER, cascade = ALL)
    private Set<Product> products;

    @ManyToMany(fetch = EAGER, cascade = ALL)
    private Set<AttributeType> requiredAttributeTypes;

    public ProductType(String name, Set<AttributeType> requiredAttributeTypes) {
        this.name = name;
        this.requiredAttributeTypes = requiredAttributeTypes;
    }

    public ProductType(String name) {
        this(name, new HashSet<>());
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attributeTypes=" + requiredAttributeTypes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
