package tsukanov.mikhail.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsukanov.mikhail.products.entity.RequiredAttribute;

import java.util.Optional;

@Repository
public interface AttributeTypeRepository extends JpaRepository<RequiredAttribute, Long> {
    Optional<RequiredAttribute> findByAttributeName(String name);
}
