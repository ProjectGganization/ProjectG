package io.ggroup.demo.dto;

import java.time.LocalDate;

public class UserResponse {

    private Integer userId;
    private String email;
    private LocalDate accountCreated;
    private String statusName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(LocalDate accountCreated) {
        this.accountCreated = accountCreated;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
