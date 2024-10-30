<h1>👨🏻‍🏫 01 | 프로젝트 소개</h1>

- 수강신청 사이트 allclear는 학생들의 수강신청 연습을 도와주는 서비스를 제공합니다. <br/>
수강신청의 특징이라고 한다면, 특정한 시각에 사람들이 한꺼번에 몰려 트래픽이 집중된다는 것입니다. <br/>
저희 팀은 트래픽이 몰릴는 상황에서도 서버가 터지지 않게 하기 위한 안정적인 아키텍처를 고민하였고, <br/>
모듈의 확장성과 독립성을 위해 MSA 구조를 채택하여 프로젝트에 적용해보았습니다.<br/>


- 프로젝트 진행 기간 : 2024.03.13 ~ 2024.10.29


 <h1>👨🏻‍🏫 02 | 레포지토리 구조</h1>

- [alc-eureka-api](https://github.com/2024Hanium-Allclear/alc-eureka-api)

- [alc-gateway-api](https://github.com/2024Hanium-Allclear/alc-gateway-api)

- [alc-lecture-api](https://github.com/2024Hanium-Allclear/alc-lecture-api)

- [alc-user-api](https://github.com/2024Hanium-Allclear/alc-user-api)

- [alc-waiting-api](https://github.com/2024Hanium-Allclear/alc-waiting-api)

- [alc-front](https://github.com/2024Hanium-Allclear/Frontend)

<h1>⚙️ 03 | 아키텍처</h1>

<img src="https://github.com/user-attachments/assets/1a75eb9c-a3b1-4fe8-99c7-4ded50117b5e" alt="image" width="600" height="400">

<img src="https://github.com/user-attachments/assets/52396791-42fe-40eb-b06b-275855da071c" alt="image" width="300" height="280">


<h1>🛠️ 04 | 기술 스택</h1>

<img src="https://github.com/user-attachments/assets/f1ec1cf9-8d8f-4365-ae88-28af579b442d" alt="image" width="450" height="400">



<h1>📃 05 | ERD</h1>

<img src="https://github.com/user-attachments/assets/862b6e61-7d18-44b2-b495-4861838bc08a" alt="image" width="750" height="600">

<h1>💻 06 | 관련 포스트</h1>

민주:  [Amazon SQS](https://hmjhaha.tistory.com/9)<br/>
민영: [[Refact] Enum 확장](https://dragonair148.tistory.com/entry/Refact-Enum-%ED%99%95%EC%9E%A5)<br/>
민영: [[Refact] 코드 개선](https://dragonair148.tistory.com/entry/Refact-%EC%BD%94%EB%93%9C-%EA%B0%9C%EC%84%A0)<br/>
민영: [[Error] ExcelFile & RDS 동기화 실시간 변경 데이터 적용 시 나타난 이슈s](https://dragonair148.tistory.com/entry/Error-ExcelFile-RDS-%EB%8F%99%EA%B8%B0%ED%99%94-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EB%B3%80%EA%B2%BD-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%A0%81%EC%9A%A9-%EC%8B%9C-%EB%82%98%ED%83%80%EB%82%9C-%EC%9D%B4%EC%8A%88s) <br/>
민영: [[Kafka] 사전 설정 및 2개의 Thread를 겸비한 Controller 구성](https://dragonair148.tistory.com/entry/Kafka-%EC%82%AC%EC%A0%84-%EC%84%A4%EC%A0%95-%EB%B0%8F-2%EA%B0%9C%EC%9D%98-Thread%EB%A5%BC-%EA%B2%B8%EB%B9%84%ED%95%9C-Controller-%EA%B5%AC%EC%84%B1)<br/>
민영: [[Kafka] 파일 변화를 감지하고 Kafka로 데이터를 전송하는 Producer 구축](https://dragonair148.tistory.com/entry/Kafka-%ED%8C%8C%EC%9D%BC-%EB%B3%80%ED%99%94%EB%A5%BC-%EA%B0%90%EC%A7%80%ED%95%98%EA%B3%A0-Kafka%EB%A1%9C-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%A5%BC-%EC%A0%84%EC%86%A1%ED%95%98%EB%8A%94-Producer-%EA%B5%AC%EC%B6%95)<br/>
민영: [[Kafka] Producer로부터 받은 데이터를 DB에 적재하는 Consumer 구축](https://dragonair148.tistory.com/entry/Kafka-Producer%EB%A1%9C%EB%B6%80%ED%84%B0-%EB%B0%9B%EC%9D%80-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%A5%BC-DB%EC%97%90-%EC%A0%81%EC%9E%AC%ED%95%98%EB%8A%94-Consumer-%EA%B5%AC%EC%B6%95-1)<br/>
이현: [[ECS] 아키텍쳐 구현(1): HTTPS 및 로드밸런서 적용](https://2hy2on.tistory.com/17) <br/>
이현: [[ECS]아키텍쳐 구현(2): Github Action으로 AWS ECS CI / CD 자동화](https://2hy2on.tistory.com/18)<br/>
이현: [[AWS] private subnet에서 ECR 접근할 때 필요한 VPC 설정](https://2hy2on.tistory.com/22)<br/>
이현: [[Eureka] Eureka Server / Client 구축하기](https://2hy2on.tistory.com/33)<br/>



<h1>👨‍👩‍👧‍👦 07 | 팀원</h1>

| 이름       | 역할                                                                                                  | GitHub                                   |
|------------|-------------------------------------------------------------------------------------------------------|------------------------------------------|
| 신이현     | - Spring Security, Jwt 기반의 로그인 기능 구현<br>- CI/CD 적용<br>- Gateway 및 Eureka 서버 개발<br>- Docker를 사용한 인프라 구축<br>- 프론트 로그인, 회원가입 화면 구현 | [2hy2on](https://github.com/2hy2on)      |
| 이나연     | - AWS EC2 PostgreSQL 연결<br>- 접속자 대기열 구현<br>- 수강신청, 조회, 삭제 기능 구현                   | [yeon2lee](https://github.com/yeon2lee)  |
| 한민주     | - 위시리스트 기능 설계 및 개발<br>- Jwt 토큰 활용하여 위시리스트 접근<br>- API 연동, Postman을 통한 테스트 | [Hanminjoo72](https://github.com/Hanminjoo72) |
| 허민영     | - EC2 Kafka, Zookeeper Port 개방 인스턴스 구축<br>- 강의 데이터 수집 및 전처리<br> - Kafka Producer&Consumer구조 실시간 데이터 모니터링 및 DB적재 <br> - 강의 검색 기능 구현<br>- 검색화면 구현 | [MinCodeHub](https://github.com/MinCodeHub)                 |
| **공통**   | 코드 리팩토링                                                                                          |                                          |
