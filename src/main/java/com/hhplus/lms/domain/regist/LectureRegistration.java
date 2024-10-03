package com.hhplus.lms.domain.regist;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureRegistrationSeq;
    private Long userSeq;
    private Long lectureSeq;
    private LocalDateTime registeredAt;

    public LectureRegistration(Long userSeq, Long lectureSeq) {
        this.userSeq = userSeq;
        this.lectureSeq = lectureSeq;
        this.registeredAt = LocalDateTime.now();
    }


}
