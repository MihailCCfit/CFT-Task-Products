package tsukanov.mikhail.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tsukanov.mikhail.products.dao.ProductTypeRepository;
import tsukanov.mikhail.products.entity.ProductType;

@SpringBootApplication
public class ProductsApplication {



    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);


    }

}
