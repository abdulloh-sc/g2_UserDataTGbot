package uz.imaan.entity;

import java.util.UUID;

public class User {
    private UUID id;
    private Long chatId;
    private String ism;
    private Integer yosh;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getIsm() {
        return ism;
    }

    public void setIsm(String ism) {
        this.ism = ism;
    }

    public Integer getYosh() {
        return yosh;
    }

    public void setYosh(Integer yosh) {
        this.yosh = yosh;
    }
}
