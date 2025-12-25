package com.finalproje.EKitapSatisProjesi.repository;

import com.finalproje.EKitapSatisProjesi.entity.Kitap;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface KitapRepository extends JpaRepository<Kitap, Long> {
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM kullanicilar_alinan_kitaplar WHERE alinan_kitaplar_id = ?1", nativeQuery = true)
    void satisKayitlariniSil(Long kitapId);
	
	List<Kitap> findByKategoriId(Long kategoriId);
	
	@Query("SELECT k FROM Kitap k WHERE " +
	           "k.ad LIKE %?1% OR " +
	           "k.yazar LIKE %?1% OR " +
	           "k.yayinevi LIKE %?1% OR " +
	           "k.isbn LIKE %?1%")
	    List<Kitap> aramaYap(String kelime);
}
