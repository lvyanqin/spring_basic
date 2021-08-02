package lynn.util.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CosOriginFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CosOriginFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        //解决跨域问题
        HttpServletResponse response = (HttpServletResponse) res;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        //自定义的header key为Authorization , 配置* 未起作用
//        response.setHeader("Access-Control-Allow-Headers", "*");//没起作用
//        response.setHeader("Access-Control-Allow-Headers", "X-Token,x-requested-with,x-user-session,origin, content-type, accept, Authorization");
//        response.setHeader("Access-Control-Allow-Credentials", "true");

        //解决获取 @RequestBody 的参数问题
        ServletRequest requestWrapper = null;
        if (req instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) req);
        }
        if (null == requestWrapper) {
            chain.doFilter(req, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.debug("CosOriginFilter过滤器初始化");
    }

}