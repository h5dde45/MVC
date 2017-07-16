package mvc.controllers;

import mvc.objects.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
public class LoginController {

    private static final Logger logger=Logger.getLogger(String.valueOf(LoginController.class));

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        return new ModelAndView("login", "user", new User());
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public String checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "login";
        }
        return "main";
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
