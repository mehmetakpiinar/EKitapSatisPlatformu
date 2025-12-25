package com.finalproje.EKitapSatisProjesi.controller;

import com.finalproje.EKitapSatisProjesi.service.KitapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private KitapService kitapService;

    @GetMapping("/admin")
    public String adminPaneli(Model model) {
        // Güvenlik kontrolünü SecurityConfig hallediyor.
        // Buraya gelen kişi kesinlikle ADMIN'dir.
        model.addAttribute("kitaplar", kitapService.tumKitaplariGetir());
        return "admin";
    }
}