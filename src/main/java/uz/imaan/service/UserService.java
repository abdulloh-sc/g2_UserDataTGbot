package uz.imaan.service;

import uz.imaan.entity.User;
import uz.imaan.entity.status.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    private Map<Long, User> users = new HashMap<>();
    private Map<Long, Status> statuses = new HashMap<>();

    public User addUser(Long chatId) {
        users.putIfAbsent(chatId, new User());
        User user = users.get(chatId);
        if (user.getChatId() == null) {
            user.setId(UUID.randomUUID());
            user.setChatId(chatId);
        }
        return user;
    }

    public Status addStatus(Long chatId) {
        statuses.putIfAbsent(chatId, Status.DEFAULT);
        return statuses.get(chatId);
    }

    public void saveStatus(Long chatId, Status status) {
        statuses.put(chatId, status);
    }

    public void restart(Long chatId) {
        users.remove(chatId);
        statuses.remove(chatId);
        addUser(chatId);
        saveStatus(chatId, Status.DEFAULT);
    }

    public String getAllUsers(){
        if (users.isEmpty()) return "user not found";

        StringBuilder sb = new StringBuilder("Users: \n \n");
        int i = 1;

        for (User user : users.values()){
            if(user.getName() != null && user.getAge() != null){
                sb.append(String.format("%d. %s\n", i++, user.getName()));
            }
        }
        return sb.toString();
    }
}