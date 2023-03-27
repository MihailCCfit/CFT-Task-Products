package tsukanov.mikhail.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class RequiredAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String attributeName;


    @ManyToOne(fetch = EAGER, cascade = ALL)
    private ProductType productType;

    public RequiredAttribute(String attributeTypeName) {
        this.attributeName = attributeTypeName;
    }

    @Override
    public String toString() {
        return "AttributeType{" +
                "id=" + id +
                ", attributeTypeName='" + attributeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequiredAttribute that = (RequiredAttribute) o;
        return attributeName.equals(that.attributeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName);
    }
}
