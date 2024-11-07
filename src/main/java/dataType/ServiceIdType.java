package dataType;

public enum ServiceIdType {
    BLOG("blog"),
    KNOWLEDGE("knowledge"),
    VCLIP("vclip"),
    BOOK("book"),
    IMAGE("image"),
    NEWS("news"),
    UNKNOWN("unknown");

    private final String value; // 실제 소문자 값을 저장할 필드

    // 생성자
    ServiceIdType(String value) {
        this.value = value;
    }

    // 소문자 값을 반환하는 메서드
    public String getValue() {
        return value;
    }
}
