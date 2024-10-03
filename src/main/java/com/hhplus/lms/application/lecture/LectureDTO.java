package com.hhplus.lms.application.lecture;

import java.time.LocalDate;

public record LectureDTO(
        Long Seq,
        int capacity,
        String lectureTeacher,
        String lectureTitle,
        String description,
        LocalDate lectureDate
) {

}
