package com.rusu.controller.DashBoard.AddController;

import com.rusu.domain.User;
import com.rusu.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/add")
public class AddUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("user")
    public String getUser(Model model){
        model.addAttribute("user", new User());
        return "/dashboard/add/user";
    }

    @PostMapping("user")
    public String addUser(User user){
        userRepository.save(user);
        return "redirect:/dashboard/edit/userTable";
    }

}
