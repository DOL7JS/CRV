package cz.upce.nnpro_backend.dtos;

import java.util.ArrayList;
import java.util.List;

public class ErrorDto {
    List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);

    }
}
