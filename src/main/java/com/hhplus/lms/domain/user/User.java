package com.hhplus.lms.domain.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//H2 DB 에서 user 라는 테이블을 사용하면 에러가 발생해서 이름만 변경
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;
    private String username;

    public User(String username) {
        this.username = username;
    }
}
