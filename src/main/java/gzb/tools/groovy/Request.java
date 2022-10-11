package gzb.tools.groovy;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Request {
    //请求路径前缀 默认为 类名   类级别可以注释
    String url() default "";
    //返回数据类型 默认为 json
    String contentType() default "*/*";
    //是否允许 跨域
    boolean crossDomain() default false;
}
