package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.domain.plogging.PloggingPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PloggingPictureRepository extends JpaRepository<PloggingPicture, Long> {
}
