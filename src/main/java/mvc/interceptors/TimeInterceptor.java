package mvc.interceptors;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class TimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger=Logger.getLogger(String.valueOf(TimeInterceptor.class));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime",System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime=Long.valueOf(request.getAttribute("startTime").toString());

        Thread.sleep(3100);
        int time=(int) (System.currentTimeMillis()-startTime)/1000;
        modelAndView.addObject("time",time);
        logger.info(handler+" = "+time+" sec");
    }
}
