package tsukanov.mikhail.products;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tsukanov.mikhail.products.dao.AttributeTypeRepository;
import tsukanov.mikhail.products.dao.ProductTypeRepository;

@Component
@AllArgsConstructor
public class Configuration implements ApplicationRunner {
    private ProductTypeRepository productTypeRepository;
    private AttributeTypeRepository attributeTypeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}