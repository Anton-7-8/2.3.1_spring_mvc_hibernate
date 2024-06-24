package web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userServiceImp;

    @Autowired
    public UsersController(UserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceImp.showAllUsers());
        return "users/index";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") @Valid User user,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        userServiceImp.createNewUser(user);
        return "redirect:/users";
    }

    @GetMapping("/update")
    public String updateUser(@RequestParam("id") long id, Model model) {
        if (userServiceImp.getUserById(id).isPresent()) {
            model.addAttribute("user", userServiceImp.getUserById(id).get());
            return "users/update";
        }
        return "redirect:/users";
    }

    @PostMapping("/update")
    public String updateUserOption(@ModelAttribute("user") @Valid User user,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/update";
        }
        userServiceImp.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String removeUser(@RequestParam("id") Long id) {
        userServiceImp.removeUserById(id);
        return "redirect:/users";
    }
}
