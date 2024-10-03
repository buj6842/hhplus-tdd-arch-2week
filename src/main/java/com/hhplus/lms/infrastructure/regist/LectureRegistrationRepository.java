package com.hhplus.lms.infrastructure.regist;

import com.hhplus.lms.domain.regist.LectureRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureRegistrationRepository extends JpaRepository<LectureRegistration, Long> {

    @Query("SELECT CASE WHEN COUNT(lr) > 0 THEN true ELSE false END " +
            "FROM LectureRegistration lr WHERE lr.userSeq = :userSeq AND lr.lectureSeq = :lectureSeq")
    boolean existsByUserSeqAndLectureSeq(Long userSeq, Long lectureSeq);

    @Query("SELECT COUNT(lr) FROM LectureRegistration lr WHERE lr.lectureSeq = :lectureSeq")
    long countByLectureSeq(@Param("lectureSeq") Long lectureSeq);

    @Query("SELECT lr FROM LectureRegistration lr WHERE lr.userSeq = :userSeq")
    List<LectureRegistration> findAllByUserSeq(@Param("userSeq") Long userSeq);
}
