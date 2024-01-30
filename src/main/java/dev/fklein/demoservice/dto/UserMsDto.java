package dev.fklein.demoservice.dto;

public class UserMsDto {

    private Long userId;
    private String userName;
    private String emailAddress;
    private String group;

    public UserMsDto() {
    }

    public UserMsDto(Long userId, String userName, String emailAddress, String group) {
        this.userId = userId;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.group = group;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
