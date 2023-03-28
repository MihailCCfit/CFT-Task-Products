package tsukanov.mikhail.products;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tsukanov.mikhail.products.dto.AttributeDTO;
import tsukanov.mikhail.products.dto.AttributeTypeDTO;
import tsukanov.mikhail.products.dto.ProductDTO;
import tsukanov.mikhail.products.dto.ProductTypeDTO;
import tsukanov.mikhail.products.service.ProductService;
import tsukanov.mikhail.products.service.ProductTypeService;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class Configuration implements ApplicationRunner {
    private ProductTypeService productTypeService;
    private ProductService productService;

    @Override
    public void run(ApplicationArguments args) {
        getInitialProductTypes()
                .forEach(
                        productType -> productTypeService.addProductType(productType)
                );
        getInitialProducts().forEach(
                productDTO -> productService.addProduct(productDTO)
        );
    }

    private List<ProductDTO> getInitialProducts() {
        return List.of(
                new ProductDTO(1L, 10L, 1.0,
                        "NSU", "laptop",
                        Set.of(
                                new AttributeDTO("Integer", "size", "17")
                        )),
                new ProductDTO(2L, 15L, 2.0,
                        "Omsk", "desktop_computer",
                        Set.of(
                                new AttributeDTO("String", "form_factor", "desktop")
                        )),
                new ProductDTO(1L, 25L, 1.5,
                        "Barnaul", "monitor",
                        Set.of(
                                new AttributeDTO("Integer", "diagonal", "15")
                        )),
                new ProductDTO(2L, 50L, 2.5,
                        "Rubtsovsk", "hdd",
                        Set.of(
                                new AttributeDTO("Long", "capacity", "100125152012")
                        ))
        );
    }


    private List<ProductTypeDTO> getInitialProductTypes() {
        return List.of(
                new ProductTypeDTO("laptop",
                        Set.of(
                                new AttributeTypeDTO("size")
                        )),
                new ProductTypeDTO("desktop_computer",
                        Set.of(
                                new AttributeTypeDTO("form_factor")
                        )),
                new ProductTypeDTO("monitor",
                        Set.of(
                                new AttributeTypeDTO("diagonal")
                        )),
                new ProductTypeDTO("hdd",
                        Set.of(
                                new AttributeTypeDTO("capacity")
                        ))

        );
    }
}