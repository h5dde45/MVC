package mvc.interceptors;

import mvc.objects.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CheckUserInterCeptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request,
                           javax.servlet.http.HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if(request.getRequestURI().contains("check-user")){
            User user=(User) modelAndView.getModel().get("user");
            if(user==null || !user.getAdmin()){
                response.sendRedirect(request.getContextPath()+"/failed");
            }
        }
    }
}
