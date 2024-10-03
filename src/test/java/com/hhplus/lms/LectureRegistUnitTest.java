package com.hhplus.lms;

import com.hhplus.lms.application.regist.LectureRegistrationService;
import com.hhplus.lms.domain.lecture.Lecture;
import com.hhplus.lms.domain.user.User;
import com.hhplus.lms.infrastructure.lecture.LectureRepository;
import com.hhplus.lms.infrastructure.regist.LectureRegistrationRepository;
import com.hhplus.lms.infrastructure.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LectureRegistUnitTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureRegistrationRepository registrationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LectureRegistrationService registrationService;

    private User user;
    private Lecture lecture;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock 데이터를 미리 설정
        user = new User("수강생1");
        user.setUserSeq(1L);

        lecture = new Lecture("1주차 TDD 기초강의", "TDD에 대해 기초강의를 합니다", "허재", 30, LocalDate.of(2024, 10, 01));
        lecture.setLectureSeq(1L);
    }

    @Test
    void 강의_신청_성공() {
        // given 사용자가 강의 신청 가능
        // Mock 설정: 강의가 존재함, 사용자 존재함, 현재 신청하지 않은 상태로 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(lectureRepository.existsById(1L)).thenReturn(true);
        when(lectureRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(lecture));
        when(registrationRepository.countByLectureSeq(1L)).thenReturn(10L);  // Mock 설정: 현재 신청 인원은 10명
        when(registrationRepository.existsByUserSeqAndLectureSeq(1L, 1L)).thenReturn(false);  // Mock 설정: 해당 사용자가 아직 신청하지 않음

        // when  사용자가 강의 신청
        registrationService.registerForLecture(1L, 1L);

        // Then 신청 성공 후 save 메서드가 호출되어야 함
        verify(registrationRepository, times(1)).save(any());
    }

    @Test
    void 강의_정원_초과() {
        // 사용자 존재, 강의 존재, 강의 정원이 꽉 찬 상황
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(lectureRepository.existsById(1L)).thenReturn(true);
        when(lectureRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(lecture));
        when(registrationRepository.countByLectureSeq(1L)).thenReturn(30L);

        // When & Then: RuntimeException 발생 예상
        assertThrows(RuntimeException.class, () -> registrationService.registerForLecture(1L, 1L));

        // 강의 정원 초과 시 save 메서드가 호출되지 않아야 함
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void 이미_신청된_강의_신청_시도_실패() {
        // given: 사용자가 이미 신청한 강의, 강의가 존재, 사용자가 존재, 이미 신청된 강의 인걸로 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(lectureRepository.existsById(1L)).thenReturn(true);
        when(lectureRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(lecture));
        when(registrationRepository.existsByUserSeqAndLectureSeq(1L, 1L)).thenReturn(true);

        // 에러 발생 예상
        assertThrows(RuntimeException.class, () -> registrationService.registerForLecture(1L, 1L));

        // 이미 신청한 경우 save 메서드가 호출되지 않아야 함
        verify(registrationRepository, never()).save(any());
    }
}
