package uz.imaan.entity;

import java.util.UUID;

public class User {
    private UUID id;
    private Long chatId;
    private String name;    // ism → name
    private Integer age;    // yosh → age

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

    public String getName() {       // getIsm() → getName()
        return name;
    }

    public void setName(String name) {  // setIsm() → setName()
        this.name = name;
    }

    public Integer getAge() {       // getYosh() → getAge()
        return age;
    }

    public void setAge(Integer age) {   // setYosh() → setAge()
        this.age = age;
    }
}