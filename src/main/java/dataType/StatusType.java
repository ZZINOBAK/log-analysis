package dataType;

public enum StatusType {
    WRONG(10),
    SUCCESS(200),
    NOT_FOUND(404),
    UNKNOWN(-1);


    private final int code; // 상태 코드를 저장할 필드

    // 생성자
    StatusType(int code) {
        this.code = code;
    }

    // 상태 코드 가져오기
    public int getCode() {
        return code;
    }

}
