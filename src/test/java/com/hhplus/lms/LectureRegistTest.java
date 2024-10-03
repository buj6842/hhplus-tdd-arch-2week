package com.hhplus.lms;

import com.hhplus.lms.application.regist.LectureRegistrationService;
import com.hhplus.lms.domain.user.User;
import com.hhplus.lms.infrastructure.lecture.LectureRepository;
import com.hhplus.lms.infrastructure.regist.LectureRegistrationRepository;
import com.hhplus.lms.infrastructure.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LectureRegistTest {
    @Autowired
    private LectureRegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureRegistrationRepository registrationRepository;

    private User user;

    @BeforeEach
    void setup() {
        // 테스트 사용자 생성 (부트테스트로 진행하여 기존 테스트데이터 이용할 수 있어서 get으로 가져옴)
        user = userRepository.findById(1L).get();
    }

    @Test
    @Transactional
    void 동일_유저_같은_특강_5번_신청_성공_1번_확인() {
        // given 첫 번째 강의 신청을 위해 값 세팅
        Long lectureSeq = 1L;

        //when
        // 동일 유저가 같은 특강을 5번 신청
        registrationService.registerForLecture(user.getUserSeq(), lectureSeq);  // 첫 번째 신청 성공해야함 (이단계에서 오류가 발생하면 안됨)
        // 이후 신청 시도는 실패해야 함
        for (int i = 0; i < 4; i++) {
            assertThrows(RuntimeException.class, () -> registrationService.registerForLecture(user.getUserSeq(), lectureSeq));
        }

        // Then
        // 신청이 한 번만 성공했는지 검증
        long count = registrationRepository.countByLectureSeq(lectureSeq);
        assertTrue(count == 1);  // 성공한 신청은 1건이어야 함
    }
}
