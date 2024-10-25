package com.example.MyPortfolioBuilder.dto;


public class PortfolioRequestDTO {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    private String description;
    // Пример поля, которое будет передано в запросе
    private Long user_id;   // ID пользователя, переданный в запросе
}

