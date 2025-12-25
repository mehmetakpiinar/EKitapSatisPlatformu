package com.finalproje.EKitapSatisProjesi.repository;

import com.finalproje.EKitapSatisProjesi.entity.Yorum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface YorumRepository extends JpaRepository<Yorum, Long> {
    List<Yorum> findByKitapId(Long kitapId);
    @Transactional
    void deleteByKitapId(Long kitapId);
}