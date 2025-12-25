package com.finalproje.EKitapSatisProjesi.service;

import com.finalproje.EKitapSatisProjesi.dto.KullaniciKayitDto;
import com.finalproje.EKitapSatisProjesi.entity.Kitap;
import com.finalproje.EKitapSatisProjesi.entity.Kullanici;
import com.finalproje.EKitapSatisProjesi.repository.KitapRepository;
import com.finalproje.EKitapSatisProjesi.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KullaniciService implements UserDetailsService {

    @Autowired
    private KullaniciRepository kullaniciRepository;
    @Autowired
    private KitapRepository kitapRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    // --- GİRİŞ İÇİN KULLANICI BULMA ---
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return kullaniciRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
    }

    // --- KAYIT OLMA ---
    public Kullanici kayitOl(KullaniciKayitDto dto) {
        
        Optional<Kullanici> mevcut = kullaniciRepository.findByUsername(dto.getUsername());
        if (mevcut.isPresent()) {
            throw new RuntimeException("Bu kullanıcı adı zaten kullanılıyor!");
        }

        Kullanici kullanici = new Kullanici();
        kullanici.setUsername(dto.getUsername());
        kullanici.setEmail(dto.getEmail());
        
        // ŞİFRELEME DÜZELTİLDİ: Şifreyi direkt DTO'dan alıyoruz
        kullanici.setPassword(passwordEncoder.encode(dto.getPassword())); 

        // Admin Mantığı
        if ("admin".equals(dto.getUsername()) || dto.isAdmin()) {
            kullanici.setAdmin(true);
        } else {
            kullanici.setAdmin(false);
        }
        
        return kullaniciRepository.save(kullanici);
    }

 // KullaniciService.java içi

    public void kitapSatinAl(Long userId, Long kitapId) {
        // 1. Kullanıcıyı bul
        Kullanici kullanici = kullaniciRepository.findById(userId).orElseThrow();
        
        // 2. Kitabı bul
        Kitap kitap = kitapRepository.findById(kitapId).orElseThrow();
        
        // 3. İlişkiyi kur (Listeye ekle)
        kullanici.getAlinanKitaplar().add(kitap);
        
        // 4. KULLANICIYI KAYDET (Bu adım çok önemli, yoksa tabloya yazmaz)
        kullaniciRepository.save(kullanici);
    }
    
    public List<Kullanici> tumKullanicilariGetir() { return kullaniciRepository.findAll(); }
    
    public void adminYap(Long id) {
        Optional<Kullanici> k = kullaniciRepository.findById(id);
        if(k.isPresent()) {
            k.get().setAdmin(true);
            kullaniciRepository.save(k.get());
        }
    }
}