package uz.imaan.service;

import uz.imaan.entity.User;
import uz.imaan.entity.status.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    private Map<Long,User> users = new HashMap<Long,User>();

    private Map<Long, Status> userSatatus = new HashMap<>();

    public User addUser(Long chatId){
        User user = users.get(chatId);
        if (user == null){
            user = new User();
            user.setId(UUID.randomUUID());
            user.setChatId(chatId);
            users.put(chatId, user);
        }
        return user;
    }

    public Status addStatus(Long chatId){
        Status status = userSatatus.get(chatId);
        if (status == null){
            return Status.BOSHLANDI;
        }
        return status;
    }

    public void saveStatus(Long chatId, Status status){
        userSatatus.put(chatId, status);
    }

    public void restart(Long chatId){
        users.remove(chatId);
        userSatatus.remove(chatId);
    }
}
