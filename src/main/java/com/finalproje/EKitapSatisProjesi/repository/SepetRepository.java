package com.finalproje.EKitapSatisProjesi.repository;

import com.finalproje.EKitapSatisProjesi.entity.Kullanici;
import com.finalproje.EKitapSatisProjesi.entity.Sepet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SepetRepository extends JpaRepository<Sepet, Long> {
    Sepet findByKullanici(Kullanici kullanici);
}