package mohsen.coder.personalcmsblogspring.errors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    protected ResponseEntity<Object> handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        StringBuilder errorMessage = new StringBuilder("داده (های) دریافتی صحیح نمی باشد. داده دریافتی باید شامل ");
        List<String[]> paramConditionGroups = ex.getParamConditionGroups();

        for (int i = 0; i < paramConditionGroups.size(); i++) {
            errorMessage.append(Arrays.toString(paramConditionGroups.get(i)));
            if (i + 1 < paramConditionGroups.size()) {
                errorMessage.append(" یا ");
            }
        }
        errorMessage.append(" باشد!");

        apiError.addMessage(errorMessage.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println(ex.getPropertyName());
        System.out.println(ex.getErrorCode());
        System.out.println(ex.getValue());
        System.out.println(ex.getRequiredType());
        System.out.println(ex.getLocalizedMessage());
        System.out.println(ex.getMessage());
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.addMessage("داده ارسالی معتبر نمی باشد!");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldException.class)
    protected ResponseEntity<Object> handleInvalidFieldException(InvalidFieldException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.addAllMessages(ex.getGlobalFieldsErrors());

        for (Map<String, String> field : ex.getFields()) {
            String key = (String) field.keySet().toArray()[0];
            String value = field.get(key);
            apiError.addMessage(" خطا در فیلد " + key + " : " + value);
        }

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.addMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.addMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UpdateItemException.class)
    protected ResponseEntity<Object> handleUpdateItemException(UpdateItemException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_MODIFIED);
        apiError.addMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(DeleteItemException.class)
    protected ResponseEntity<Object> handleDeleteItemException(DeleteItemException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_MODIFIED);
        apiError.addMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


