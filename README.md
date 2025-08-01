# 🧾 LogAnalysis (로그 분석기)

순수 Java 기반의 로그 분석기입니다.  
서버 로그 파일을 분석하여 API 키, 상태 코드, 서비스 ID, 브라우저, 날짜별 요청 통계를 출력합니다.  
제네릭 기반 인터페이스와 Map 자료구조를 활용하여 확장성과 재사용성을 고려해 설계되었습니다.

---

## 🛠 기술 스택

- Java 21
- Gradle
- IntelliJ IDEA
- Git

---

## 📂 프로젝트 구조
LogAnalysis/
├── build.gradle
├── settings.gradle
├── gradle/
├── src/
│ └── main/
│ └── java/
│ └── log/
│ ├── controller/
│ ├── model/
│ ├── service/
│ └── view/
└── README.md
