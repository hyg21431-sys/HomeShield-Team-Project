package home.role;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),        // 어드민 유저
    USER("ROLE_USER");          // 일반 유저
    
    private String value;
    
    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
