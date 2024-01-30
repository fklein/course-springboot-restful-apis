package dev.fklein.demoservice.dto;

import dev.fklein.demoservice.entities.Order;

import java.util.List;

public class UserDto {

    private Long userId;
    private String userName;
    private String lastName;
    private List<Order> orders;

    public UserDto() {
    }

    public UserDto(Long userId, String userName, String lastName) {
        this.userId = userId;
        this.userName = userName;
        this.lastName = lastName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
