package com.hhplus.lms.controller;

import com.hhplus.lms.application.lecture.LectureFacade;
import com.hhplus.lms.application.lecture.LectureDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureFacade lectureFacade;

    public LectureController(LectureFacade lectureFacade) {
        this.lectureFacade = lectureFacade;
    }

    // 특강 신청
    @PostMapping("/register/{lectureSeq}")
    public ResponseEntity<String> registerLecture(@PathVariable Long lectureSeq, @RequestParam Long userId) {
        try {
            lectureFacade.registerLecture(userId, lectureSeq);
            return ResponseEntity.ok("수강신청이 정상적으로 완료되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // 신청 가능한 특강 목록 조회
    @GetMapping("/available")
    public ResponseEntity<List<LectureDTO>> getAvailableLectures(@RequestParam LocalDate date) {
        return ResponseEntity.ok(lectureFacade.getAvailableLectures(date));
    }

    // 신청 완료된 특강 목록 조회
    @GetMapping("/registered")
    public ResponseEntity<List<LectureDTO>> getRegisteredLectures(@RequestParam Long userId) {
        List<LectureDTO> registeredLectures = lectureFacade.getRegisteredLecturesByUserId(userId);
        return ResponseEntity.ok(registeredLectures);
    }
}
