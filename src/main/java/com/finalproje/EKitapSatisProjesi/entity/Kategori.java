package com.finalproje.EKitapSatisProjesi.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Kategori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;

    @OneToMany(mappedBy = "kategori") 
    private List<Kitap> kitaplar;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public List<Kitap> getKitaplar() { return kitaplar; }
    public void setKitaplar(List<Kitap> kitaplar) { this.kitaplar = kitaplar; }
}