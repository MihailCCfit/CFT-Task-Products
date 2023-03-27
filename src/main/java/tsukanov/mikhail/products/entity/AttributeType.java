package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Setter
@Getter
public class AttributeType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String attributeTypeName;

    private String Type;

    @OneToMany(fetch = EAGER, cascade = ALL)
    private Set<Attribute> attributes;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    private ProductType productType;

    public AttributeType(String attributeTypeName, String type) {
        this.attributeTypeName = attributeTypeName;
        Type = type;
    }

    @Override
    public String toString() {
        return "AttributeType{" +
                "id=" + id +
                ", attributeTypeName='" + attributeTypeName + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeType that = (AttributeType) o;
        return attributeTypeName.equals(that.attributeTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeTypeName, Type);
    }
}
