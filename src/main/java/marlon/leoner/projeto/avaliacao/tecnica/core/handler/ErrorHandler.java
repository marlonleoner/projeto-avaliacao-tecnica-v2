package marlon.leoner.projeto.avaliacao.tecnica.core.handler;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Error;
import marlon.leoner.projeto.avaliacao.tecnica.exception.*;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApiIgnore
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handleGenericException(Exception ex) {
        return new Error("InternalError", Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new Error("ValidationError", messages);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Error handleObjectNotFoundException(ObjectNotFoundException ex) {
        return new Error(ex.getId(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler({
            ObjectAlreadyExistsException.class,
            InvalidParameterException.class,
            SessionException.class
    })
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Error handleObjectAlreadyExists(AbstractException ex) {
        return new Error(ex.getId(), Collections.singletonList(ex.getMessage()));
    }
}