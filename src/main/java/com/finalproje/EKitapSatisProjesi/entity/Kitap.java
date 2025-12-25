package com.finalproje.EKitapSatisProjesi.entity;

import jakarta.persistence.*;

@Entity
public class Kitap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;
    private String yazar;
    private Double fiyat;
    @Column(length = 5000)
    private String aciklama;
    private String resimUrl;
    @ManyToOne
    private Kullanici ekleyenKullanici;
    @ManyToOne
    @JoinColumn(name = "kategori_id")
    private Kategori kategori;
    private String isbn;
    private Integer sayfaSayisi;
    private String yayinevi;
    private Integer basimYili;
    private String dil;
    private String kapakTuru;
    
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getSayfaSayisi() { return sayfaSayisi; }
    public void setSayfaSayisi(Integer sayfaSayisi) { this.sayfaSayisi = sayfaSayisi; }

    public String getYayinevi() { return yayinevi; }
    public void setYayinevi(String yayinevi) { this.yayinevi = yayinevi; }

    public Integer getBasimYili() { return basimYili; }
    public void setBasimYili(Integer basimYili) { this.basimYili = basimYili; }

    public String getDil() { return dil; }
    public void setDil(String dil) { this.dil = dil; }

    public String getKapakTuru() { return kapakTuru; }
    public void setKapakTuru(String kapakTuru) { this.kapakTuru = kapakTuru; }
    
    public Kategori getKategori() { return kategori; }
    public void setKategori(Kategori kategori) { this.kategori = kategori; }

    public Kullanici getEkleyenKullanici() { return ekleyenKullanici; }
    public void setEkleyenKullanici(Kullanici ekleyenKullanici) { this.ekleyenKullanici = ekleyenKullanici; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getYazar() { return yazar; }
    public void setYazar(String yazar) { this.yazar = yazar; }
    public Double getFiyat() { return fiyat; }
    public void setFiyat(Double fiyat) { this.fiyat = fiyat; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public String getResimUrl() { return resimUrl; }
    public void setResimUrl(String resimUrl) { this.resimUrl = resimUrl; }
}