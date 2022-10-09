package gzb.tools.log;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Log {
    void i(Object log);

    void e(Exception e);

    void e(Exception e, Object log);

    void sql(Object log);

    void req(HttpServletRequest request);

    void req(Exception e, Object log, HttpServletRequest request);

    void print(Object[] arr);

    void print(String name, Object[] arr);

    void print(List<?> arr);

    void print(String name, List<?> arr);
}
