package com.finalproje.EKitapSatisProjesi.service;

import com.finalproje.EKitapSatisProjesi.entity.Kitap;
import com.finalproje.EKitapSatisProjesi.entity.Kullanici;
import com.finalproje.EKitapSatisProjesi.entity.Sepet;
import com.finalproje.EKitapSatisProjesi.entity.SepetDetay;
import com.finalproje.EKitapSatisProjesi.repository.SepetDetayRepository;
import com.finalproje.EKitapSatisProjesi.repository.SepetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SepetService {

    @Autowired
    private SepetRepository sepetRepository;
    
    @Autowired
    private SepetDetayRepository sepetDetayRepository;
    
    @Autowired
    private KitapService kitapService;

    public Sepet sepetiGetir(Kullanici kullanici) {
        Sepet sepet = sepetRepository.findByKullanici(kullanici);
        if (sepet == null) {
            sepet = new Sepet();
            sepet.setKullanici(kullanici);
            sepetRepository.save(sepet);
        }
        return sepet;
    }
    @Transactional
    public void sepeteEkle(Kullanici kullanici, Long kitapId) {
        Sepet sepet = sepetiGetir(kullanici);
        Kitap kitap = kitapService.kitapBul(kitapId);

        SepetDetay mevcutDetay = null;
        for (SepetDetay detay : sepet.getDetaylar()) {
            if (detay.getKitap().getId().equals(kitapId)) {
                mevcutDetay = detay;
                break;
            }
        }

        if (mevcutDetay != null) {
            mevcutDetay.setAdet(mevcutDetay.getAdet() + 1);
        } else {
            SepetDetay yeniDetay = new SepetDetay();
            yeniDetay.setKitap(kitap);
            yeniDetay.setSepet(sepet);
            yeniDetay.setAdet(1);
            yeniDetay.setFiyat(kitap.getFiyat());
            sepet.getDetaylar().add(yeniDetay);
        }
        
        sepetRepository.save(sepet);
    }

    @Transactional
    public void sepettenCikar(Long detayId) {
        sepetDetayRepository.deleteById(detayId);
    }

    @Transactional
    public void sepetiTemizle(Kullanici kullanici) {
        Sepet sepet = sepetiGetir(kullanici);
        sepet.getDetaylar().clear();
        sepetRepository.save(sepet);
    }
}