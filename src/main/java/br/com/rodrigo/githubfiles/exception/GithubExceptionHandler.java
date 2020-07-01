package br.com.rodrigo.githubfiles.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.util.ContentFormatter;

@ControllerAdvice
public class GithubExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GithubException.class)
    protected ResponseEntity<GithubFilesSummaryDTO> invalidRepository(GithubException ex) {
        final GithubFilesSummaryDTO error = GithubFilesSummaryDTO
                .builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .description(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final GithubFilesSummaryDTO error = GithubFilesSummaryDTO
                .builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .description(ContentFormatter.validationErrorMessage(ex.getFieldErrors()))
                .build();
        return ResponseEntity.badRequest().body(error);
    }
}