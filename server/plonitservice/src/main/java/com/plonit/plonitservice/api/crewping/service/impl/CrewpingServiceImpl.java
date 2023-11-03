package com.plonit.plonitservice.api.crewping.service.impl;

import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import com.plonit.plonitservice.api.crewping.service.CrewpingService;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.crewping.Crewping;
import com.plonit.plonitservice.domain.crewping.CrewpingMember;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingMemberRepository;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.plonit.plonitservice.common.exception.ErrorCode.CREWPING_BAD_REQUEST;
import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CrewpingServiceImpl implements CrewpingService {

    private final CrewpingRepository crewpingRepository;
    private final CrewpingMemberRepository crewpingMemberRepository;
    private final MemberRepository memberRepository;
    private final CrewRepository crewRepository;
    private final CrewMemberQueryRepository crewMemberQueryRepository;
    private final AwsS3Uploader awsS3Uploader;

    @Override
    public void saveCrewping(SaveCrewpingDto dto) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Member member = memberRepository.findById(dto.getMemberKey())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_BAD_REQUEST));

        // TODO: N + 1 문제 해결 필요
        Crew crew = crewRepository.findById(dto.getCrewId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CREW));

        Integer isValidCrewMember = crewMemberQueryRepository.isValidCrewMember(dto.getMemberKey(), dto.getCrewId());
        if(isValidCrewMember == 0) {
           throw new CustomException(CREWPING_BAD_REQUEST);
        }

        String crewpingImageUrl = null;
        if(dto.getCrewpingImage() != null) {
            try {
                crewpingImageUrl = awsS3Uploader.uploadFile(dto.getCrewpingImage(), "crewping/crewpingImage");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }

        Crewping crewping = crewpingRepository.save(dto.toEntity(crew, crewpingImageUrl));
        CrewpingMember crewpingMember =crewpingMemberRepository.save(CrewpingMember.of(member, crewping, true));
    }
}
