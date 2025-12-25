package com.finalproje.EKitapSatisProjesi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal; // <-- BU IMPORT ŞART
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalproje.EKitapSatisProjesi.dto.KitapEkleDto;
import com.finalproje.EKitapSatisProjesi.entity.Kitap;
import com.finalproje.EKitapSatisProjesi.entity.Kullanici;
import com.finalproje.EKitapSatisProjesi.entity.Yorum;
import com.finalproje.EKitapSatisProjesi.service.KategoriService;
import com.finalproje.EKitapSatisProjesi.service.KitapService;
import com.finalproje.EKitapSatisProjesi.service.KullaniciService; // Artık buna çok az ihtiyacımız kaldı

import java.util.List;

@Controller
public class KitapController {

    @Autowired private KitapService kitapService;
    @Autowired private KategoriService kategoriService;
    // KullaniciService'e artık kullanıcıyı bulmak için ihtiyacımız yok!

    // 1. Kitap Ekleme Sayfası
    @GetMapping("/kitap-ekle")
    public String kitapEkleSayfasi(Model model) {
        model.addAttribute("kategoriler", kategoriService.tumKategorileriGetir());
        model.addAttribute("kitapDto", new KitapEkleDto());
        return "kitap-ekle";
    }

    // 2. Kitap Kaydetme
    @PostMapping("/kitap-ekle")
    // DİKKAT: @AuthenticationPrincipal sayesinde 'satici' nesnesi dolu geliyor!
    public String kitapKaydet(KitapEkleDto dto, @AuthenticationPrincipal Kullanici satici) {
        
        if (satici != null) {
            kitapService.kitapKaydet(dto, satici);
        }
        return "redirect:/ana-sayfa";
    }

    // 3. Detay Sayfası
    @GetMapping("/kitap/{id}")
    public String kitapDetay(@PathVariable Long id, Model model) {
        Kitap kitap = kitapService.kitapBul(id);
        List<Yorum> yorumlar = kitapService.yorumlariGetir(id);
        
        model.addAttribute("kitap", kitap);
        model.addAttribute("yorumlar", yorumlar);
        return "kitap-detay";
    }

    @GetMapping("/kategori/{id}")
    public String kategoriFiltrele(@PathVariable Long id, 
                                   @AuthenticationPrincipal Kullanici girisYapan, 
                                   Model model) {
        
        if (girisYapan != null) {
             model.addAttribute("kullaniciAdi", girisYapan.getUsername());
        }
        
        model.addAttribute("kategoriler", kategoriService.tumKategorileriGetir());
        model.addAttribute("kitapListesi", kitapService.kategoriyeGoreGetir(id));
        
        com.finalproje.EKitapSatisProjesi.entity.Kategori secilen = kategoriService.kategoriBul(id);
        model.addAttribute("secilenKategori", (secilen != null ? secilen.getAd() : null));
        
        return "home";
    }

    // 7. Arama Yapma
    @GetMapping("/ara")
    public String aramaYap(@RequestParam String kelime, 
                           @AuthenticationPrincipal Kullanici girisYapan, 
                           Model model) {
        
        if (girisYapan != null) {
            model.addAttribute("kullaniciAdi", girisYapan.getUsername());
        }
        
        model.addAttribute("kitapListesi", kitapService.kitapAra(kelime));
        model.addAttribute("kategoriler", kategoriService.tumKategorileriGetir());
        model.addAttribute("secilenKategori", null);
        model.addAttribute("aramaKelimesi", kelime);
        
        return "home";
    }

    // 5. Silme İşlemi
    @GetMapping("/kitap-sil/{id}")
    public String kitapSil(@PathVariable Long id, @AuthenticationPrincipal Kullanici girisYapan) {
        
        Kitap silinecekKitap = kitapService.kitapBul(id);

        if (girisYapan != null && silinecekKitap != null) {
            Long kitapSahibiId = (silinecekKitap.getEkleyenKullanici() != null) 
                                 ? silinecekKitap.getEkleyenKullanici().getId() 
                                 : null;

            if (girisYapan.getId().equals(kitapSahibiId) || girisYapan.getUsername().equals("admin")) {
                kitapService.kitapSil(id);
            }
        }
        return "redirect:/ana-sayfa";
    }

    // 6. Yorum Yapma
    @PostMapping("/yorum-yap")
    public String yorumYap(@RequestParam Long kitapId, 
                           @RequestParam String yorumMetni, 
                           @AuthenticationPrincipal Kullanici girisYapan) {
        
        Kitap kitap = kitapService.kitapBul(kitapId);
        
        Yorum yeniYorum = new Yorum();
        yeniYorum.setMetin(yorumMetni);
        yeniYorum.setKitap(kitap);
        yeniYorum.setKullanici(girisYapan); // Direkt nesneyi kullanıyoruz!
        
        kitapService.yorumKaydet(yeniYorum);
        
        return "redirect:/kitap/" + kitapId;
    }
}