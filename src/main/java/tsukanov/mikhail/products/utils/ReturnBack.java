package tsukanov.mikhail.products.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ReturnBack<T> {
    private Optional<T> object = Optional.empty();
    private String errorMessage = null;
    private HttpStatus status = HttpStatus.OK;

    public ResponseEntity<?> getResponse() {
        return ResponseEntity.status(status).body(errorMessage);
    }

    public Optional<T> getObject() {
        return object;
    }

    public ReturnBack(T object) {
        this.object = Optional.ofNullable(object);
    }

    public ReturnBack(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
