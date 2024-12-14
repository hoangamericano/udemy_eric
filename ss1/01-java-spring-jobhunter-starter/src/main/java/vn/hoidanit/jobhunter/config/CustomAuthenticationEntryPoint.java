package vn.hoidanit.jobhunter.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.RestResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    //
    // ------------commence---------------
    // String error - logic xu ly bat exceptionToken neu tra ra null
    // java.lang.NullPointerException: Cannot invoke
    // "java.lang.Throwable.getMessage()
    // " because the return value of
    // "org.springframework.security.core.AuthenticationException.getCause()" is
    // null
    // -----------------------------------
    //
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        response.setContentType("application/json;charset=UTF-8");
        // if (authException.getCause() instanceof JwtValidationException
        // validationException) {
        // var problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        // problemDetail.setType(URI.create("https://tools.ietf.org/html/rfc6750#section-3.1"));
        // problemDetail.setProperty("errors", validationException.getErrors());
        // problemDetail.setTitle("Invalid Token");
        // mapper.writeValue(response.getWriter(), problemDetail);
        // }
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        String errorMessage = Optional.ofNullable(authException.getCause())
                .map(Throwable::getMessage)
                .orElse(authException.getMessage());
        // res.setError(authException.getCause().getMessage());
        res.setError(errorMessage);
        res.setMessage("Token không hợp lệ.");
        mapper.writeValue(response.getWriter(), res);
    }
}
