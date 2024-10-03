package com.hhplus.lms.application.regist;

import com.hhplus.lms.domain.lecture.Lecture;
import com.hhplus.lms.domain.regist.LectureRegistration;
import com.hhplus.lms.infrastructure.lecture.LectureRepository;
import com.hhplus.lms.infrastructure.regist.LectureRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LectureRegistrationService {

    private final LectureRepository lectureRepository;
    private final LectureRegistrationRepository registrationRepository;

    public LectureRegistrationService(LectureRepository lectureRepository, LectureRegistrationRepository registrationRepository) {
        this.lectureRepository = lectureRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public void registerForLecture(Long userSeq, Long lectureSeq) {
        // lectureId가 존재하는지 확인
        if (!lectureRepository.existsById(lectureSeq)) {
            throw new RuntimeException("강의가 존재하지 않습니다.");
        }
        Lecture lecture = lectureRepository.findByIdForUpdate(lectureSeq)
                .orElseThrow(() -> new RuntimeException("해당 강의를 찾을 수 없습니다."));

        long registeredCount = registrationRepository.countByLectureSeq(lectureSeq);
        if (registeredCount >= lecture.getCapacity()) {
            throw new RuntimeException("수강신청 자리가 없습니다.");
        }

        if (registrationRepository.existsByUserSeqAndLectureSeq(userSeq, lectureSeq)) {
            throw new RuntimeException("이미 수강신청한 강의입니다.");
        }

        registrationRepository.save(new LectureRegistration(userSeq, lectureSeq));
    }
}
