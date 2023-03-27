package tsukanov.mikhail.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsukanov.mikhail.products.entity.AttributeType;

import java.util.Optional;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {
    Optional<AttributeType> findByAttributeTypeName(String name);
}
