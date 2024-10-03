package com.hhplus.lms.application.lecture;

import com.hhplus.lms.infrastructure.lecture.LectureRepository;
import com.hhplus.lms.infrastructure.regist.LectureRegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureRegistrationRepository registrationRepository;

    public LectureService(LectureRepository lectureRepository, LectureRegistrationRepository registrationRepository) {
        this.lectureRepository = lectureRepository;
        this.registrationRepository = registrationRepository;
    }

    // 특강 목록을 조회
    public List<LectureDTO> getLectureList() {
        return lectureRepository.findAll().stream()
                .filter(lecture -> registrationRepository.countByLectureSeq(lecture.getLectureSeq()) < lecture.getCapacity())
                .map(LectureMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 신청 가능한 특강 목록을 날짜별로 조회
    public List<LectureDTO> getAvailableLecturesByDate(LocalDate date) {
        return lectureRepository.findByLectureDate(date).stream()
                .filter(lecture -> registrationRepository.countByLectureSeq(lecture.getLectureSeq()) < lecture.getCapacity())
                .map(LectureMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 신청 완료된 특강 목록을 조회
    public List<LectureDTO> getRegisteredLectureList(Long userSeq) {
        return registrationRepository.findAllByUserSeq(userSeq).stream()
                .map(registration -> lectureRepository.findById(registration.getLectureSeq())
                        .map(LectureMapper::toDTO)
                        .orElseThrow(() -> new RuntimeException("Lecture not found")))
                .collect(Collectors.toList());
    }
}
