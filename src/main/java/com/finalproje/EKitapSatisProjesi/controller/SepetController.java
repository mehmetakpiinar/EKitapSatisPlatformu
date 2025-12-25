package com.finalproje.EKitapSatisProjesi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal; // <-- ÖNEMLİ
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalproje.EKitapSatisProjesi.entity.Kullanici;
import com.finalproje.EKitapSatisProjesi.entity.Sepet;
import com.finalproje.EKitapSatisProjesi.entity.SepetDetay;
import com.finalproje.EKitapSatisProjesi.service.KullaniciService;
import com.finalproje.EKitapSatisProjesi.service.SepetService;

@Controller
public class SepetController {

    @Autowired private SepetService sepetService;
    @Autowired private KullaniciService kullaniciService;

    @GetMapping("/sepetim")
    public String sepetiGoster(@AuthenticationPrincipal Kullanici girisYapan, Model model) {
        // Güvenlik kontrolüne (null check) bile gerek yok, Spring Security giriş yapmayanı buraya sokmaz.
        
        Sepet sepet = sepetService.sepetiGetir(girisYapan);
        
        model.addAttribute("sepet", sepet);
        model.addAttribute("toplamTutar", sepet.getToplamTutar());
        
        return "sepet";
    }

    @PostMapping("/sepete-ekle")
    public String sepeteEkle(@RequestParam Long kitapId, @AuthenticationPrincipal Kullanici girisYapan) {
        sepetService.sepeteEkle(girisYapan, kitapId);
        return "redirect:/sepetim";
    }

    @GetMapping("/sepetten-cikar/{id}")
    public String sepettenCikar(@PathVariable Long id) {
        sepetService.sepettenCikar(id);
        return "redirect:/sepetim";
    }

    @PostMapping("/sepeti-onayla")
    public String sepetiOnayla(@AuthenticationPrincipal Kullanici girisYapan) {
        Sepet sepet = sepetService.sepetiGetir(girisYapan);

        for (SepetDetay detay : sepet.getDetaylar()) {
            kullaniciService.kitapSatinAl(girisYapan.getId(), detay.getKitap().getId());
        }

        sepetService.sepetiTemizle(girisYapan);
        return "redirect:/profilim";
    }
}