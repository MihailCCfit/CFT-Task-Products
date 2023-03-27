package tsukanov.mikhail.products.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.function.Function;

public class Maybe<T> {
    private Optional<T> object = Optional.empty();
    private String errorMessage = null;
    private HttpStatus status = HttpStatus.OK;

    public boolean hasProblem() {
        return object.isEmpty();
    }

    public T getObjectUnsafe() {
        assert object.isPresent();
        return object.get();
    }

    public ResponseEntity<?> getResponse() {
        if (object.isEmpty()) {
            return ResponseEntity.status(status.value()).body(new Response(errorMessage, status));
        } else {
            return ResponseEntity.ok(object);
        }
    }

    @Data
    @AllArgsConstructor
    static public class Response {
        private String errorMessage;
        private HttpStatus status;
    }

    public Optional<T> getObject() {
        return object;
    }

    public Maybe(T object) {
        this.object = Optional.ofNullable(object);
    }


    public Maybe(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    @Override
    public String toString() {
        if (object.isPresent()) {
            return object.get().toString();
        } else {
            return errorMessage + "code:" + status;
        }
    }

    public <E> Maybe<E> map(Function<? super T, ? extends E> function) {
        return object.map(t -> new Maybe<>((E) function.apply(t)))
                .orElseGet(() -> new Maybe<>(errorMessage, status));
    }
}
