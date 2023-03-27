package tsukanov.mikhail.products.service;

import java.util.Set;

public record ProductUpdate(
        Long id,
        Long serialNumber,
        Long amount,
        Double price,
        String manufacturer,
        String productType,
        Set<AttributeUpdate> attributeUpdates) {

    @Override
    public String toString() {
        return "ProductUpdate{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", amount=" + amount +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", productType='" + productType + '\'' +
                ", attributeUpdates=" + attributeUpdates +
                '}';
    }
}
