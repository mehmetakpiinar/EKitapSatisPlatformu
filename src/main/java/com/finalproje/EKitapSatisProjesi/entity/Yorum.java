package com.finalproje.EKitapSatisProjesi.entity;

import jakarta.persistence.*;

@Entity
public class Yorum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metin;

    @ManyToOne
    private Kullanici kullanici;

    @ManyToOne
    private Kitap kitap;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMetin() { return metin; }
    public void setMetin(String metin) { this.metin = metin; }
    public Kullanici getKullanici() { return kullanici; }
    public void setKullanici(Kullanici kullanici) { this.kullanici = kullanici; }
    public Kitap getKitap() { return kitap; }
    public void setKitap(Kitap kitap) { this.kitap = kitap; }
}