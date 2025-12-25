package com.finalproje.EKitapSatisProjesi.dto;

import org.springframework.web.multipart.MultipartFile; // Eğer dosya yüklüyorsan

public class KitapEkleDto {

    private String ad;
    private String yazar;
    private Double fiyat;
    private String resimUrl; // Veya dosya yükleme varsa MultipartFile
    private String aciklama;
    
    // Yeni eklediğin özellikler
    private String isbn;
    private Integer sayfaSayisi;
    private String yayinevi;
    private Integer basimYili;
    private String dil;
    private String kapakTuru;
    
    private Long kategoriId; // DTO'nun en büyük faydası: Direkt ID alırız

    // Getter ve Setter'lar
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getYazar() { return yazar; }
    public void setYazar(String yazar) { this.yazar = yazar; }
    public Double getFiyat() { return fiyat; }
    public void setFiyat(Double fiyat) { this.fiyat = fiyat; }
    public String getResimUrl() { return resimUrl; }
    public void setResimUrl(String resimUrl) { this.resimUrl = resimUrl; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
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
    public Long getKategoriId() { return kategoriId; }
    public void setKategoriId(Long kategoriId) { this.kategoriId = kategoriId; }
}	