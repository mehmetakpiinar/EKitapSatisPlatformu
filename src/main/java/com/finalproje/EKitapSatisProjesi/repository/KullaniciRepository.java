package com.finalproje.EKitapSatisProjesi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproje.EKitapSatisProjesi.entity.Kullanici;

import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {
    Optional<Kullanici> findByUsernameAndPassword(String username, String password);
    Optional<Kullanici> findByUsername(String username);
}