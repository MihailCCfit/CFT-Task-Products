package tsukanov.mikhail.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsukanov.mikhail.products.entity.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

}
