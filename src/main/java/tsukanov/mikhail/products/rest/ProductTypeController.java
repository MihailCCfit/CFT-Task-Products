package tsukanov.mikhail.products.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.entity.RequiredAttribute;
import tsukanov.mikhail.products.service.ProductTypeService;
import tsukanov.mikhail.products.utils.ReturnBack;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/producttype")
@AllArgsConstructor
@Slf4j
public class ProductTypeController {
    private ProductTypeService productTypeService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productTypeService.findAll()
                .stream().map(TypeProductType::new)
                .toList()
        );
    }

    @GetMapping("/products/find/bytype/{type}")
    public ResponseEntity<?> findProductsByType(@PathVariable("type") String type) {
        if (type == null) {
            return ResponseEntity.badRequest().body("There is no type");
        }
        ReturnBack<Set<Product>> returnValue = productTypeService.findAllProductsByType(type);
        return returnValue
                .map(products -> products.stream()
                        .map(ShortProduct::new)
                        .toList())
                .getResponse();
    }

    @GetMapping("/requiredarguments/find/{type}")
    public ResponseEntity<?> findRequiredArgumentsByType(@PathVariable("type") String type) {
        return productTypeService.findRequiredAttributes(type)
                .map(products -> products.stream()
                        .map(TypeRequiredAttribute::new)
                        .toList())
                .getResponse();
    }
}

record ShortProduct(
        Long id,
        Long serialNumber,
        Long amount,
        String manufacturer,
        Set<InfoAttribute> attribute
) {
    public ShortProduct(Product product) {
        this(product.getId(),
                product.getSerialNumber(),
                product.getAmount(),
                product.getManufacturer(),
                product.getAttributes()
                        .stream().map(InfoAttribute::new)
                        .collect(Collectors.toSet()));
    }
}


record TypeProductType(
        Long id,
        String name,
        Set<TypeRequiredAttribute> requiredAttributes
) {
    public TypeProductType(ProductType productType) {
        this(productType.getId(),
                productType.getName(),
                productType.getRequiredAttributes()
                        .stream().map(TypeRequiredAttribute::new)
                        .collect(Collectors.toSet()));
    }
}

record TypeRequiredAttribute(
        Long id,
        String attributeName) {
    public TypeRequiredAttribute(RequiredAttribute requiredAttribute) {
        this(requiredAttribute.getId(),
                requiredAttribute.getAttributeName());
    }
}