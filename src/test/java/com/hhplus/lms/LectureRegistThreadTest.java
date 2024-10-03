package com.hhplus.lms;
import com.hhplus.lms.application.regist.LectureRegistrationService;
import com.hhplus.lms.domain.user.User;
import com.hhplus.lms.infrastructure.lecture.LectureRepository;
import com.hhplus.lms.infrastructure.regist.LectureRegistrationRepository;
import com.hhplus.lms.infrastructure.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LectureRegistThreadTest {

    @Autowired
    private LectureRegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureRegistrationRepository registrationRepository;

    private List<User> users;

    @BeforeEach
    void setup() {
        // 40명의 사용자 리스트 가져오기(테스트 데이터는 40명만 만들어놓았음)
        users = userRepository.findAll();
    }

    @Test
    @DisplayName("동시성 테스트 40인기준 30명만 성공 확인 테스트")
    void 동시에_40명_중_30명만_강의신청_성공_확인_테스트() throws InterruptedException {
        // given
        // 테스트 데이터의 첫번째 강의를 기준으로 테스트한다
        Long lectureSeq = 1L;

        // 40개의 스레드(40명)가 동시에 신청
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        CountDownLatch latch = new CountDownLatch(40);

        for (User user : users) {
            executorService.submit(() -> {
                try {
                    registrationService.registerForLecture(user.getUserSeq(), lectureSeq);
                } catch (Exception e) {
                    System.out.println("에러 발생 " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();  // 모든 스레드가 작업을 마칠 때까지 대기

        // THEN 30명만 성공했는지 확인
        long successfulRegistrations = registrationRepository.countByLectureSeq(lectureSeq);
        assertEquals(30, successfulRegistrations);  // 성공한 신청자 수가 30명인지 검증
    }
}
