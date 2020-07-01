package br.com.rodrigo.githubfiles.util;

import java.util.List;

import org.springframework.validation.FieldError;

public final class ContentFormatter {
	
	private ContentFormatter() {
	}

    public static String validationErrorMessage(List<FieldError> errors) {
    	final StringBuilder validationMsg = new StringBuilder();

        errors.forEach( (FieldError erro) -> {
            validationMsg.append(erro.getField());
            validationMsg.append(' ');
            validationMsg.append(erro.getDefaultMessage());
            validationMsg.append(". ");
        });

        return validationMsg.toString();
    }
}