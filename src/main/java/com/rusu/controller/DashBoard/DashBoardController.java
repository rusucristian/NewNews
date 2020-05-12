package com.rusu.controller.DashBoard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/dashboard")
public class DashBoardController {

    @GetMapping("tables")
    public String getBlank(){
        return "/dashboard/tables";
    }


}
