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

import javax.validation.Valid;
import java.util.Locale;
import java.util.logging.Logger;

@Controller
//@SessionAttributes("user")
public class LoginController {

    private static final Logger logger=Logger.getLogger(String.valueOf(LoginController.class));

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(@ModelAttribute("user") User user, Locale locale) {
        System.out.println(locale.getDisplayLanguage());
        System.out.println(messageSource.getMessage("locale",
                new String[] {locale.getDisplayName(locale)},locale));
        user.setName("usernamevalue");
        return "login";
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public ModelAndView checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                                  Locale locale) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("locale",messageSource.getMessage("locale",
                new String[] {locale.getDisplayName(locale)},locale));
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("login");
        }else {
            modelAndView.setViewName("main");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/failed",method = RequestMethod.GET)
    public ModelAndView failed(){
        return new ModelAndView("login-failed","message","Login failed...");
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
