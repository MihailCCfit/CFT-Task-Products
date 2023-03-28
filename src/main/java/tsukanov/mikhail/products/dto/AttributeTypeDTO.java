package tsukanov.mikhail.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tsukanov.mikhail.products.entity.RequiredAttribute;



@Getter
@Setter
@ToString
public class AttributeTypeDTO {
    private String attributeName;

    public AttributeTypeDTO(@JsonProperty("attributeName") String attributeName) {
        this.attributeName = attributeName;
    }
    public RequiredAttribute toAttributeType() {
        return new RequiredAttribute(attributeName);
    }
}
