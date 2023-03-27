package tsukanov.mikhail.products.service;

import tsukanov.mikhail.products.dto.AttributeDTO;

import java.util.Objects;

public record AttributeUpdate(String changeType, AttributeDTO attribute) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeUpdate that = (AttributeUpdate) o;
        return attribute.equals(that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }
}
