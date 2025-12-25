package com.finalproje.EKitapSatisProjesi.service;

import com.finalproje.EKitapSatisProjesi.dto.KitapEkleDto;
import com.finalproje.EKitapSatisProjesi.entity.Kategori;
import com.finalproje.EKitapSatisProjesi.entity.Kitap;
import com.finalproje.EKitapSatisProjesi.entity.Kullanici;
import com.finalproje.EKitapSatisProjesi.entity.Yorum;
import com.finalproje.EKitapSatisProjesi.repository.KitapRepository;
import com.finalproje.EKitapSatisProjesi.repository.YorumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KitapService {

    @Autowired
    private KitapRepository kitapRepository;
    
    @Autowired
    private YorumRepository yorumRepository;

    
    public List<Kitap> tumKitaplariGetir() {
        return kitapRepository.findAll();
    }

    public List<Kitap> kategoriyeGoreGetir(Long kategoriId) {
        return kitapRepository.findByKategoriId(kategoriId);
    }
    
    @Autowired
    private KategoriService kategoriService; // Kategoriyi bulmak için lazım

    public void kitapKaydet(KitapEkleDto dto, Kullanici satici) {
        
        // --- DTO'dan ENTITY'ye ÇEVİRME ---
        Kitap kitap = new Kitap();
        kitap.setAd(dto.getAd());
        kitap.setYazar(dto.getYazar());
        kitap.setFiyat(dto.getFiyat());
        kitap.setAciklama(dto.getAciklama());
        kitap.setResimUrl(dto.getResimUrl());
        
        // Yeni özellikler
        kitap.setIsbn(dto.getIsbn());
        kitap.setSayfaSayisi(dto.getSayfaSayisi());
        kitap.setYayinevi(dto.getYayinevi());
        kitap.setBasimYili(dto.getBasimYili());
        kitap.setDil(dto.getDil());
        kitap.setKapakTuru(dto.getKapakTuru());
        
        // İlişkileri Ayarlama
        kitap.setEkleyenKullanici(satici);
        
        // DTO'dan gelen ID ile kategoriyi bulup set ediyoruz
        if (dto.getKategoriId() != null) {
            Kategori k = kategoriService.kategoriBul(dto.getKategoriId());
            kitap.setKategori(k);
        }

        kitapRepository.save(kitap);
    }
    
    public Kitap kitapBul(Long id) {
        Optional<Kitap> bulunan = kitapRepository.findById(id);
        return bulunan.orElse(null);
    }
    
    public List<Kitap> kitapAra(String kelime) {
        if (kelime != null && !kelime.isEmpty()) {
            return kitapRepository.aramaYap(kelime);
        }
        return tumKitaplariGetir();
    }
    
    public void kitapSil(Long id) {
    	yorumRepository.deleteByKitapId(id);
    	kitapRepository.satisKayitlariniSil(id);
        kitapRepository.deleteById(id);
    }
    
    public List<Yorum> yorumlariGetir(Long kitapId) {
        return yorumRepository.findByKitapId(kitapId);
    }

    
    public void yorumKaydet(Yorum yorum) {
        yorumRepository.save(yorum);
    }
}
