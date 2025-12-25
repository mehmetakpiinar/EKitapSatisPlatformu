package com.finalproje.EKitapSatisProjesi.service;

import com.finalproje.EKitapSatisProjesi.entity.Kategori;
import com.finalproje.EKitapSatisProjesi.repository.KategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class KategoriService {

    @Autowired
    private KategoriRepository kategoriRepository;

    public List<Kategori> tumKategorileriGetir() {
        return kategoriRepository.findAll();
    }
    
    public Kategori kategoriBul(Long id) {
        return kategoriRepository.findById(id).orElse(null);
    }

    @PostConstruct 
    public void varsayilanKategorileriEkle() {
        if (kategoriRepository.count() == 0) {
            String[] kategoriler = {"Roman", "Tarih", "Bilim Kurgu", "Kişisel Gelişim", "Spor", "Ekonomi & İş Dünyası", "Romantik", "Yazılım & Teknoloji", "Çocuk"};
            
            for (String ad : kategoriler) {
                Kategori k = new Kategori();
                k.setAd(ad);
                kategoriRepository.save(k);
            }
        }
    }
}