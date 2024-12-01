package vn.hoidanit.jobhunter.util.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.RestResponse;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // TODO Auto-generated method stub
        // Sử dụng khi nào muốn ghi đè, format phản hồi api
        // false sẽ không format - true chạy dến hàm tiếp theo
        System.out.println("log function supports");
        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        // TODO Auto-generated method stub
        System.out.println("log funtion beforeBodyWitre");
        HttpServletResponse httpServletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = httpServletResponse.getStatus();
        RestResponse<Object> res = new RestResponse<Object>();
        // Nếu data là kiểu string thì không thao tác thêm
        // if (body instanceof String) {
        // res.setData(body);
        // res.setMessage("Call api success");
        // return body;
        // }
        if (status >= 400) {
            res.setStatusCode(status);
            res.setError(res.toString());
            String message = (String) res.getData();
            res.setData(body);
            return res;
        } else {
            res.setStatusCode(status);
            res.setData(body);
            res.setMessage("Call api success");
        }
        return res;

    }

}
