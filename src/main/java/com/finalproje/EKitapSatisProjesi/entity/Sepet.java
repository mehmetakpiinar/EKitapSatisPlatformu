package com.finalproje.EKitapSatisProjesi.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sepet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Kullanici kullanici;

    @OneToMany(mappedBy = "sepet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SepetDetay> detaylar = new ArrayList<>();

    private double toplamTutar;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Kullanici getKullanici() { return kullanici; }
    public void setKullanici(Kullanici kullanici) { this.kullanici = kullanici; }
    public List<SepetDetay> getDetaylar() { return detaylar; }
    public void setDetaylar(List<SepetDetay> detaylar) { this.detaylar = detaylar; }

    public double getToplamTutar() {
        double toplam = 0;
        for (SepetDetay detay : detaylar) {
            toplam += (detay.getFiyat() * detay.getAdet());
        }
        return toplam;
    }
    
    public void setToplamTutar(double toplamTutar) { this.toplamTutar = toplamTutar; }
}