package com.hhplus.lms.application.lecture;

import com.hhplus.lms.domain.lecture.Lecture;

public class LectureMapper {

    // DTO to Entity 변환
    public static Lecture toEntity(LectureDTO dto) {
        return new Lecture(dto.lectureTitle(), dto.description(), dto.lectureTeacher(), dto.capacity(), dto.lectureDate());
    }

    // Entity to DTO 변환
    public static LectureDTO toDTO(Lecture lecture) {
        return new LectureDTO(lecture.getLectureSeq(), lecture.getCapacity(), lecture.getLectureTeacher(), lecture.getLectureTitle(), lecture.getDescription() , lecture.getLectureDate());
    }
}
