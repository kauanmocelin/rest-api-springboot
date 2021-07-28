package dev.kauanmocelin.springbootrestapi.customer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FieldErrorMessage {
    private final String field;
    private final String message;
}
