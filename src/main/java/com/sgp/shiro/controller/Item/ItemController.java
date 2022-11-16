package com.sgp.shiro.controller.Item;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/select")
    public String item(){
        return "item select !!!";
    }

    @GetMapping("/delete")
    @RequiresRoles("admin")
    public String delete(){
        return "item delete!!";
    }

    @GetMapping("/update")
    public String update(){
        return "item update!!";
    }
}
