package com.plonit.plonitservice.domain.crewping.repository;

import com.plonit.plonitservice.domain.crewping.CrewpingMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CrewpingMemberRepository extends JpaRepository<CrewpingMember, Long> {

    @Query("SELECT cm FROM CrewpingMember cm JOIN FETCH cm.member WHERE cm.crewping.id = :crewpingId AND cm.isCrewpingMaster = true")
    Optional<CrewpingMember> findMasterCrewpingMemberWitMemberJoinFetch(Long crewpingId);

    @Query("SELECT cm FROM CrewpingMember cm JOIN FETCH cm.crewping JOIN FETCH cm.member WHERE cm.member.id = :memberId AND cm.crewping.id = :crewpingId")
    Optional<CrewpingMember> findCrewpingMemberByJoinFetch(Long memberId, Long crewpingId);

}
