package tsukanov.mikhail.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsukanov.mikhail.products.entity.AttributeType;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {

}
