package com.hhplus.lms.application.lecture;

public record LectureDTO(
        Long id,
        String title,
        String lecturer,
        int capacity
) {

}
