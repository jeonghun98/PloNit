package com.plonit.plonitservice.domain.member.repository;

import com.plonit.plonitservice.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(long kakaoId);
    Optional<Member> findById(long id);
}