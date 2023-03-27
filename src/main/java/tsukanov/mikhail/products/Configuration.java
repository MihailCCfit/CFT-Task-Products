package tsukanov.mikhail.products;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tsukanov.mikhail.products.dto.AttributeTypeDTO;
import tsukanov.mikhail.products.dto.ProductDTO;
import tsukanov.mikhail.products.dto.ProductTypeDTO;
import tsukanov.mikhail.products.service.ProductService;
import tsukanov.mikhail.products.service.ProductTypeService;

import java.util.Set;

@Component
@AllArgsConstructor
public class Configuration implements ApplicationRunner {
    private ProductTypeService productTypeService;
    private ProductService productService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        productTypeService.addProductType(new ProductTypeDTO("Laptop", Set.of(
                new AttributeTypeDTO("diag")
        )));
        productService.addProduct(new ProductDTO(1L, 1L, "Asus", "Laptop"));
        System.out.println(productTypeService.findAll());
        System.out.println(productService.findAll());

    }
}