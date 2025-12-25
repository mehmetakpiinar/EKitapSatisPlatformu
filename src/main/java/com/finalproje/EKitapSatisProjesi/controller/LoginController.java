package com.finalproje.EKitapSatisProjesi.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import org.springframework.security.core.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.finalproje.EKitapSatisProjesi.dto.KullaniciKayitDto;
import com.finalproje.EKitapSatisProjesi.dto.LoginDto;
import com.finalproje.EKitapSatisProjesi.entity.Kullanici;
import com.finalproje.EKitapSatisProjesi.service.KategoriService;
import com.finalproje.EKitapSatisProjesi.service.KitapService;
import com.finalproje.EKitapSatisProjesi.service.KullaniciService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired private KitapService kitapService; 
    @Autowired private KullaniciService kullaniciService;
    @Autowired private KategoriService kategoriService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private SecurityContextRepository securityContextRepository;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("kullaniciDto", new KullaniciKayitDto());
        return "register";
    }

    @PostMapping("/login")
    public String loginYap(@ModelAttribute("loginDto") LoginDto loginDto, 
                           HttpServletRequest request, 
                           HttpServletResponse response,
                           Model model) {
        try {
            UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication auth = authenticationManager.authenticate(token);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);

            return "redirect:/ana-sayfa";

        } catch (AuthenticationException e) {
            model.addAttribute("error", "Kullanıcı adı veya şifre hatalı!");
            return "login";
        }
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("kullaniciDto") KullaniciKayitDto dto, Model model) {
        try {
            kullaniciService.kayitOl(dto);
            return "redirect:/login?success=true"; 
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage()); 
            model.addAttribute("kullaniciDto", dto); 
            return "register";
        }
    }

    // --- ANA SAYFA ---
    @GetMapping("/ana-sayfa")
    public String anaSayfa(Model model, @AuthenticationPrincipal Kullanici kullanici) { 
        // Kullanıcı veritabanından çekilmez, Security Context'ten gelir.
        
        if (kullanici != null) {
            model.addAttribute("kullaniciAdi", kullanici.getUsername());
        }
        
        model.addAttribute("kitapListesi", kitapService.tumKitaplariGetir());
        model.addAttribute("kategoriler", kategoriService.tumKategorileriGetir());
        
        return "home";
    }
    
 // LoginController.java içinde

    @GetMapping("/profilim")
    public String profilSayfasi(Model model, @AuthenticationPrincipal Kullanici principal) {
        // 1. Principal'dan sadece kullanıcı adını alıyoruz (Bu değişmez çünkü)
        String username = principal.getUsername();
        
        // 2. Veritabanından EN GÜNCEL kullanıcı verisini çekiyoruz
        Kullanici guncelKullanici = (Kullanici) kullaniciService.loadUserByUsername(username);
        
        // 3. Modela güncel kullanıcıyı ve güncel kitap listesini ekliyoruz
        model.addAttribute("kullanici", guncelKullanici);
        
        // Burada artık veritabanındaki son durumu görüyoruz
        model.addAttribute("alinanlar", guncelKullanici.getAlinanKitaplar());
        
        return "profil";
    }
}