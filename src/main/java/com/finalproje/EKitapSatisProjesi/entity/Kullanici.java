package com.finalproje.EKitapSatisProjesi.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "kullanicilar")
public class Kullanici implements UserDetails { // <-- Değişiklik Burası

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private boolean admin;

    // --- İLİŞKİLER (Aynen Kalıyor) ---
    @ManyToMany(fetch = FetchType.EAGER) // Rolleri çekerken hata vermesin diye EAGER yaptık
    private List<Kitap> alinanKitaplar;
    
    @OneToOne(mappedBy = "kullanici", cascade = CascadeType.ALL)
    private Sepet sepet;
    
    @OneToMany(mappedBy = "ekleyenKullanici")
    private List<Kitap> eklenenKitaplar;

    // --- SPRING SECURITY İÇİN GEREKLİ METOTLAR ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Eğer admin ise "ROLE_ADMIN", değilse "ROLE_USER" veriyoruz
        if (this.admin) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    // --- GETTER & SETTER (Aynen Kalıyor) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public List<Kitap> getAlinanKitaplar() { return alinanKitaplar; }
    public void setAlinanKitaplar(List<Kitap> alinanKitaplar) { this.alinanKitaplar = alinanKitaplar; }
    public Sepet getSepet() { return sepet; }
    public void setSepet(Sepet sepet) { this.sepet = sepet; }
    public List<Kitap> getEklenenKitaplar() { return eklenenKitaplar; }
    public void setEklenenKitaplar(List<Kitap> eklenenKitaplar) { this.eklenenKitaplar = eklenenKitaplar; }
}