package com.example.vaccineManagement.Exceptions;

public class VaccineNotFound extends RuntimeException {
    public VaccineNotFound(String message) {
        super(message);
    }
}
