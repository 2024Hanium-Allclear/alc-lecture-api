👨🏻‍🏫 01 | 프로젝트 소개

- 수강신청 사이트 allclear는 학생들의 수강신청 연습을 도와주는 서비스를 제공합니다. <br/>
수강신청의 특징이라고 한다면, 특정한 시각에 사람들이 한꺼번에 몰려 트래픽이 집중된다는 것입니다. <br/>
저희 팀은 이러한 상황에서도 서버가 터지지 않게 하기 위한 안정적인 아키텍처를 고민해보고 프로젝트에 적용해보았습니다.<br/>

- 프로젝트 진행 기간 : 2023.04.10(월) ~ 2023.05.19(금) (6주간 진행)
- 시연 영상 :  allclear

 👨🏻‍🏫 02 | 레포지토리 구조

[alc-eureka-api](https://github.com/2024Hanium-Allclear/alc-eureka-api)

[alc-gateway-api](https://github.com/2024Hanium-Allclear/alc-gateway-api)

[alc-lecture-api](https://github.com/2024Hanium-Allclear/alc-lecture-api)

[alc-user-api](https://github.com/2024Hanium-Allclear/alc-user-api)

[alc-waiting-api](https://github.com/2024Hanium-Allclear/alc-waiting-api)

[alc-front](https://github.com/2024Hanium-Allclear/Frontend)

⚙️ 03 | 아키텍쳐

- 아키텍쳐 설계도

<img src="https://github.com/user-attachments/assets/1a75eb9c-a3b1-4fe8-99c7-4ded50117b5e" alt="image" width="600" height="400">

<img src="https://github.com/user-attachments/assets/52396791-42fe-40eb-b06b-275855da071c" alt="image" width="300" height="280">

기술 의사 설정

MSA 구조 - 특정 시간에 트래픽이 몰릴 것을 대비하고 모듈의 확장성과 독립성을 위해 채택

🛠️ 04 | 기술 스택


📃 05 | 프로젝트 산출물

- ERD
<img src="https://github.com/user-attachments/assets/862b6e61-7d18-44b2-b495-4861838bc08a" alt="image" width="450" height="400">

06 | 관련 포스트

민주:  [Amazon SQS](https://hmjhaha.tistory.com/9)<br/>
민영: [[Error] ExcelFile & RDS 동기화 실시간 변경 데이터 적용 시 나타난 이슈s](https://sinabro-dev.tistory.com/8) <br/>
민영: [[Kafka] 사전 설정 및 2개의 Thread를 겸비한 Controller 구성](https://sinabro-dev.tistory.com/5)<br/>
민영: [[Kafka] 파일 변화를 감지하고 Kafka로 데이터를 전송하는 Producer 구축](https://sinabro-dev.tistory.com/6)<br/>
민영: [[Kafka] Producer로부터 받은 데이터를 DB에 적재하는 Consumer 구축](https://sinabro-dev.tistory.com/7)<br/>


<img src="https://github.com/user-attachments/assets/f1ec1cf9-8d8f-4365-ae88-28af579b442d" alt="image" width="450" height="400">


👨‍👩‍👧‍👦 06 | 팀원
| 이름       | 역할                                                                                                  | GitHub                                   |
|------------|-------------------------------------------------------------------------------------------------------|------------------------------------------|
| 신이현     | - Spring Security, Jwt 기반의 로그인 기능 구현<br>- CI/CD 적용<br>- Gateway 및 Eureka 서버 개발<br>- Docker를 사용한 인프라 구축<br>- 프론트 로그인, 회원가입 화면 구현 | [2hy2on](https://github.com/2hy2on)      |
| 이나연     | - AWS EC2 PostgreSQL 연결<br>- 접속자 대기열 구현<br>- 수강신청, 조회, 삭제 기능 구현                   | [yeon2lee](https://github.com/yeon2lee)  |
| 한민주     | - 위시리스트 기능 설계 및 개발<br>- Jwt 토큰 활용하여 위시리스트 접근<br>- API 연동, Postman을 통한 테스트 | [Hanminjoo72](https://github.com/Hanminjoo72) |
| 허민영     | - EC2 Kafka, Zookeeper Port 개방 인스턴스 구축<br>- 강의 데이터 수집 및 전처리<br> - Kafka Producer&Consumer구조 실시간 데이터 모니터링 및 DB적재 <br> - 강의 검색 기능 구현<br>- 검색화면 구현 | [MinCodeHub](https://github.com/MinCodeHub)                 |
| **공통**   | 코드 리팩토링                                                                                          |                                          |
