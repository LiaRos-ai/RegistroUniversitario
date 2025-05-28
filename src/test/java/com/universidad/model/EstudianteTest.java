package com.universidad.model;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.time.LocalDate;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

public class EstudianteTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void estudianteConEmailInvalidoNoEsValido() {
        Estudiante estudiante = Estudiante.builder()
            .nombre("Juan")
            .apellido("Pérez")
            .email("no-es-email")
            .fechaNacimiento(LocalDate.of(2000, 1, 1))
            .numeroInscripcion("12345")
            .build();
        Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);
        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().contains("email"));
    }
}
