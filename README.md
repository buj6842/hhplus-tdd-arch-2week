# ERD 설계 및 ERD 설계 이유

### User(사용자)테이블 설계
User테이블의 Create 문
```sql
CREATE TABLE users (
user_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(255) NOT NULL
);

```
간단한 DB를 사용하려 H2를 채택하였는데 user 라는 네이밍이 이미 H2에서 사용중이라 테이블이름은 users 로 설정하게 되었으며
회원가입, 로그인,로그아웃을 고려하지 않다보니
user_seq(흔히 얘기하는 userId), username(유저의 이름) 정도만 컬럼으로 만들게 되었다.

### Lecture(강의)
Lecture 테이블의 Create 문
```sql
CREATE TABLE lecture (
lecture_seq BIGINT AUTO_INCREMENT PRIMARY KEY, // Lecture_seq 강의의 ID 번호
lecture_title VARCHAR(255) NOT NULL,           // 강의 제목
description TEXT,                              // 강의 설명
lecture_teacher VARCHAR(255) NOT NULL,         // 강연자
capacity INT NOT NULL DEFAULT 30,              // 최대 인원
lecture_date DATE NOT NULL                     // 강의 일시(강의를 진행하는 날)
);
```

강의 테이블에는 강의 제목, 강의내용, 강연자, 강의를 들을 수 있는 최대 인원, 강의 일시 정도로 구성되어있다.

###Lecture_Registration (강의 신청 테이블)
Lecture_Registartion 테이블의 Create 문
```sql
CREATE TABLE lecture_registration (
lecture_registration_seq BIGINT AUTO_INCREMENT PRIMARY KEY, // 강의 신청의 Seq(ID 번호)
user_seq BIGINT,                                            // 신청한 강의의 User 번호
lecture_seq BIGINT,                                         // 신청한 강의 번호
registered_at TIMESTAMP                                     // 강의 신청한 시간
);
```
사실 ERD를 작성하면서 이렇게 설계한 이유를 설명할 때 이곳을 설명해야 생각이 들었다.
보다시피 신청을 한 내역을 저장하는 역할을 하기 때문인데, 외래키 설정이 없도록 설정이 되었다.
그 이유로는 연관 관계를 지으려면
USER 와 강의 의 관계가
1:N
강의 와 강의신청 의 관계가
1:N 으로 묶이게 설계가 되었었는데
이렇게 설계가 되었을 때 select 문이 좀더 많이 발생할 것으로 생각이 되었고
데이터의 확인을 application 단계로 옮겨 좀더 수월한 작업으로 진행하자는 의도로 진행하기 위해 외래키가 없는 형식으로 설계하게 되었다.
