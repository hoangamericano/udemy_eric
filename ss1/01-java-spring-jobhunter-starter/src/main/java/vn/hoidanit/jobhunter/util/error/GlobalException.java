package vn.hoidanit.jobhunter.util.error;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.hoidanit.jobhunter.domain.RestResponse;

@RestControllerAdvice
@Order(1)
public class GlobalException {

    @ExceptionHandler(value = {
            IdInvalidException.class,
            UserInvalidException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<RestResponse<Object>> handleIdException(UserInvalidException userInvalidException) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(userInvalidException.getMessage());
        res.setMessage("Exception occurs...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Validation Error");

        // Lấy danh sách lỗi
        List<Map<String, String>> errors = fieldErrors.stream()
                .map(fieldError -> Map.of(
                        "field", fieldError.getField(),
                        "message", fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        res.setMessage(errors.size() > 1 ? errors : errors.get(0));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

}
