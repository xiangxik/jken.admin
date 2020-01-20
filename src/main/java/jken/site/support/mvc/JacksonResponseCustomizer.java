package jken.site.support.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.List;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class JacksonResponseCustomizer extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        Object value = bodyContainer.getValue();
        if (value != null) {
            if (value instanceof Page) {
                Page<?> page = (Page<?>) value;
                bodyContainer.setValue(new LayuiPage(0, "", page));
            }
        }
    }

    static class LayuiPage {
        private Integer code;
        private String msg;
        private Long count;
        private List<?> data;

        public LayuiPage() {
        }

        public LayuiPage(Integer code, String msg, Page<?> page) {
            this.code = code;
            this.msg = msg;
            this.count = page.getTotalElements();
            this.data = page.getContent();
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }
}
