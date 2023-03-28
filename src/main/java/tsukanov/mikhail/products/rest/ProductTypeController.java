package tsukanov.mikhail.products.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsukanov.mikhail.products.dto.AttributeTypeDTO;
import tsukanov.mikhail.products.dto.ProductTypeDTO;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.entity.RequiredAttribute;
import tsukanov.mikhail.products.service.ProductTypeService;

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
        return productTypeService.findAllProductsByType(type)
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


    @PostMapping("/add")
    public ResponseEntity<?> addProductType(@RequestBody ProductTypeDTO productTypeDTO) {
        return productTypeService.addProductType(productTypeDTO)
                .map(TypeProductType::new)
                .getResponse();
    }

    @PostMapping("/requiredarguments/add")
    public ResponseEntity<?> addRequiredArgumentsForProductType(@RequestBody RequiredArgumentForAdding requiredArgument) {
        return productTypeService.addRequiredAttribute(requiredArgument.typeName(),
                        new AttributeTypeDTO(requiredArgument.argumentName()))
                .map(TypeProductType::new)
                .getResponse();
    }

}

record RequiredArgumentForAdding(String argumentName, String typeName) {

}


record ShortProduct(
        Long id,
        Long serialNumber,
        Long amount,
        Double price,
        String manufacturer,
        Set<InfoAttribute> attribute
) {
    public ShortProduct(Product product) {
        this(product.getId(),
                product.getSerialNumber(),
                product.getAmount(),
                product.getPrice(),
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