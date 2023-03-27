package tsukanov.mikhail.products.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsukanov.mikhail.products.dto.ProductDTO;
import tsukanov.mikhail.products.entity.Attribute;
import tsukanov.mikhail.products.entity.Product;
import tsukanov.mikhail.products.entity.ProductType;
import tsukanov.mikhail.products.service.ProductService;
import tsukanov.mikhail.products.service.ProductUpdate;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private ProductService productService;

    @GetMapping({"/find/all"})
    public ResponseEntity<?> findAllProducts() {
        return ResponseEntity.ok(productService.findAll()
                .stream().map(InfoProduct::new).collect(Collectors.toList()));
    }

    @GetMapping("/find/byid/{id}")
    public ResponseEntity<?> findProductById(@PathVariable("id") Long id) {
        return productService.findById(id)
                .map(InfoProduct::new)
                .getResponse();
    }

    @DeleteMapping("/remove/byid/{id}")
    public ResponseEntity<?> removeProductById(@PathVariable("id") Long id) {
        return productService.removeProduct(id)
                .map(InfoProduct::new)
                .getResponse();
    }

    @PostMapping("/add/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO product) {
        return productService.addProduct(product)
                .map(InfoProduct::new)
                .getResponse();
    }

    @PostMapping("/change/product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdate productUpdate) {
        return productService.updateProduct(productUpdate)
                .map(InfoProduct::new)
                .getResponse();
    }


}


record InfoProduct(
        @NotNull
        Long id,
        @NotNull
        Long serialNumber,
        @NotNull
        Long amount,
        @NotNull
        Double price,
        @NotNull
        String manufacturer,
        @NotNull
        InfoProductType productType,
        @NotNull
        Set<InfoAttribute> attributes
) {
    public InfoProduct(Product product) {
        this(product.getId(),
                product.getSerialNumber(),
                product.getAmount(),
                product.getPrice(),
                product.getManufacturer(),
                new InfoProductType(product.getProductType()),
                product.getAttributes()
                        .stream().map(InfoAttribute::new)
                        .collect(Collectors.toSet()));
    }

}

record InfoProductType(
        Long id,
        String name
) {
    public InfoProductType(ProductType productType) {
        this(productType.getId(),
                productType.getName());
    }

}

record InfoAttribute(
        Long id,
        String attributeType,
        String attributeName,
        String value
) {
    public InfoAttribute(Attribute attribute) {
        this(attribute.getId(),
                attribute.getAttributeType(),
                attribute.getAttributeName(),
                attribute.getAValue());
    }

}
