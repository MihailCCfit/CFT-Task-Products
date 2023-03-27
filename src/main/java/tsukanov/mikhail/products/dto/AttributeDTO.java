package tsukanov.mikhail.products.dto;

import lombok.Data;
import tsukanov.mikhail.products.entity.Attribute;
import tsukanov.mikhail.products.entity.AttributeType;
import tsukanov.mikhail.products.entity.ProductType;

@Data
public class AttributeDTO {
    private AttributeType attributeType;
    private String value;

    private ProductType productType;

    public Attribute toAttribute(){
        return new Attribute(attributeType, value);
    }
}
