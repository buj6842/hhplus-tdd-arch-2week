package com.hhplus.lms;

import com.hhplus.lms.application.lecture.LectureDTO;
import com.hhplus.lms.application.lecture.LectureService;
import com.hhplus.lms.domain.lecture.Lecture;
import com.hhplus.lms.infrastructure.lecture.LectureRepository;
import com.hhplus.lms.infrastructure.regist.LectureRegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LectureServiceUnitTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureRegistrationRepository registrationRepository;

    @InjectMocks
    private LectureService lectureService;

    private Lecture lecture1;
    private Lecture lecture2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        lecture1 = new Lecture(1L,"1주차 TDD 기초강의", "TDD에 대해 기초강의를 합니다", "허재", 30, LocalDate.of(2024, 10, 01));

        lecture2 = new Lecture(2L,"2주차 Clean Code 강의", "Clean Code에 대해 강의합니다", "허재", 30, LocalDate.of(2024, 10, 01));
    }

    @Test
    void 특정_날짜_특강_목록_조회() {
        // given 특정 날짜에 해당하는 강의를 반환하도록 설정
        LocalDate lectureDate = LocalDate.of(2024, 10, 01);
        when(lectureRepository.findByLectureDate(lectureDate)).thenReturn(Arrays.asList(lecture1, lecture2));

        // when 해당 날짜의 강의 목록 조회
        List<LectureDTO> lectureDTOs = lectureService.getAvailableLecturesByDate(lectureDate);

        // then 조회된 강의가 두 개 있는지 검증
        assertEquals(2, lectureDTOs.size());
        assertEquals("1주차 TDD 기초강의", lectureDTOs.get(0).lectureTitle());
        assertEquals("2주차 Clean Code 강의", lectureDTOs.get(1).lectureTitle());
    }

    @Test
    void 특정_날짜에_강의_없음_조회_실패() {
        // given 특정 날짜에 강의가 없는 상태로 설정
        LocalDate lectureDate = LocalDate.of(2024, 10, 01);
        when(lectureRepository.findByLectureDate(lectureDate)).thenReturn(Collections.emptyList());

        // when 해당 날짜의 강의 조회
        List<LectureDTO> lectureDTOs = lectureService.getAvailableLecturesByDate(lectureDate);

        // then 강의 목록이 비어있는지 검증
        assertTrue(lectureDTOs.isEmpty());
    }
}

