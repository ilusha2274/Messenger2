package filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;



public class CharsetFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null) encoding = "UTF-8";
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {

        if (null == request.getCharacterEncoding()) {
            request.setCharacterEncoding("UTF-8");
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        next.doFilter(request, response);


        System.out.println("doFilter");
    }

    @Override
    public void destroy() {

    }
}
