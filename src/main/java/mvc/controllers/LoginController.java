package mvc.controllers;

import mvc.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@SessionAttributes("user")
public class LoginController {

    private static final Logger logger = Logger.getLogger(String.valueOf(LoginController.class));

    @Autowired
    MessageSource messageSource;

    @ModelAttribute
    public User createNewUseer() {
        return new User("usernamevalue");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(@ModelAttribute User user, Locale locale) {
        return "login";
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public ModelAndView checkUser(Locale locale, @Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView=new ModelAndView();
        if (!bindingResult.hasErrors()) {
            RedirectView redirectView=new RedirectView("mainpage");
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            modelAndView.setView(redirectView);

            redirectAttributes.addFlashAttribute("redirect", true);
            redirectAttributes.addFlashAttribute("locale", messageSource.getMessage("locale",
                    new String[]{locale.getDisplayName(locale)}, locale));
        }else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    public String goMain(HttpServletRequest request) {
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if (map != null) {
            logger.info("redirect...");
        } else {
            logger.info("update...");
        }
        return "main";
    }

    @RequestMapping(value = "/failed", method = RequestMethod.GET)
    public ModelAndView failed() {
        return new ModelAndView("login-failed", "message", "Login failed...");
    }

    @RequestMapping(value = "/get-json-user/{name}/{password}/{admin}", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public User getJsonUser(@PathVariable("name") String name,
                            @PathVariable("password") String password,
                            @PathVariable("admin") Boolean admin) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setAdmin(admin);
        return user;
    }

    @RequestMapping(value = "/put-json-user",
            method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> setJsonUser(@RequestBody User user) {
        logger.info(user.getName());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
