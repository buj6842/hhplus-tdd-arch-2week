package com.hhplus.lms.application.lecture;

import com.hhplus.lms.application.regist.LectureRegistrationService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class LectureFacade {

    private final LectureService lectureService;
    private final LectureRegistrationService registrationService;

    public LectureFacade(LectureService lectureService, LectureRegistrationService registrationService) {
        this.lectureService = lectureService;
        this.registrationService = registrationService;
    }

    // 특강 신청
    public void registerLecture(Long userId, Long lectureId) {
        registrationService.registerForLecture(userId, lectureId);
    }

    // 신청 가능한 특강 목록 조회
    public List<LectureDTO> getAvailableLectures(LocalDate date) {
        return lectureService.getAvailableLecturesByDate(date);
    }

    // 신청 완료된 특강 목록 조회
    public List<LectureDTO> getRegisteredLecturesByUserId(Long userId) {
        return lectureService.getRegisteredLectureList(userId);
    }
}
