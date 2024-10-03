package com.hhplus.lms.domain.lecture;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "lecture")
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureSeq;
    private String lectureTitle;
    private String description;
    private String lectureTeacher;
    private int capacity;
    private LocalDate lectureDate;

    public Lecture(String lectureTitle, String description, String lectureTeacher, int capacity , LocalDate lectureDate) {
        this.lectureTitle = lectureTitle;
        this.description = description;
        this.lectureTeacher = lectureTeacher;
        this.capacity = capacity;
        this.lectureDate = lectureDate;
    }

}
