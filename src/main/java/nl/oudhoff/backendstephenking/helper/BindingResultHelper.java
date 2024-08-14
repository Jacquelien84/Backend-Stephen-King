package nl.oudhoff.backendstephenking.helper;

import org.springframework.validation.BindingResult;

public class BindingResultHelper {

    public static String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMessage.append(  "\n" + fieldError.getField() + " : " + fieldError.getDefaultMessage()).append(". "));
        return errorMessage.toString();
    }
}

