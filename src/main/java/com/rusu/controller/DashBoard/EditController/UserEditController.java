package com.rusu.controller.DashBoard.EditController;

import com.rusu.domain.User;
import com.rusu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/dashboard/edit")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserEditController {
    @Autowired
    private UserService userService;

    @GetMapping("userTable")
    public String editUserTable(Model model){
        model.addAttribute("user", userService.findAll());
        return "/dashboard/edit/userTable";
    }

    @GetMapping("/user/{userID}")
    public String editUser(@PathVariable Long userID, Model model){
        model.addAttribute("user", userService.getOne(userID));
        return "/dashboard/edit/user";
    }

    @GetMapping("/user/deleteUser/{userID}")
    public String deleteUser(@PathVariable Long userID, Model model){
        userService.deleteById(userID);
        model.addAttribute("user", userService.findAll());
        return "/dashboard/edit/userTable";
    }

    @PostMapping("/userEdit/{id}")
    public String editUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model){

        if (result.hasErrors()) {
            user.setId(id);
            return "/dashboard/edit/userTable";
        }

        userService.saveUser(id,user);

        model.addAttribute("user", userService.findAll());
        return "redirect:/dashboard/edit/userTable";
    }
}
