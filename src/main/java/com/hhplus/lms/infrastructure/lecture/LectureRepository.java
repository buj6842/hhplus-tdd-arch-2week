package com.hhplus.lms.infrastructure.lecture;

import com.hhplus.lms.domain.lecture.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.lectureSeq = :lectureSeq")
    Optional<Lecture> findByIdForUpdate(@Param("lectureSeq") Long lectureSeq);

    List<Lecture> findByLectureDate(LocalDate lectureDate);
}
