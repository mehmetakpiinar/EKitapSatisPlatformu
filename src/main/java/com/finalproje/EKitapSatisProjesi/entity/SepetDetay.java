package com.finalproje.EKitapSatisProjesi.entity;

import jakarta.persistence.*;

@Entity
public class SepetDetay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Kitap kitap;

    @ManyToOne
    private Sepet sepet;

    private int adet;
    private double fiyat;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Kitap getKitap() { return kitap; }
    public void setKitap(Kitap kitap) { this.kitap = kitap; }
    public Sepet getSepet() { return sepet; }
    public void setSepet(Sepet sepet) { this.sepet = sepet; }
    public int getAdet() { return adet; }
    public void setAdet(int adet) { this.adet = adet; }
    public double getFiyat() { return fiyat; }
    public void setFiyat(double fiyat) { this.fiyat = fiyat; }

    public double getAraToplam() {
        return this.fiyat * this.adet;
    }
}