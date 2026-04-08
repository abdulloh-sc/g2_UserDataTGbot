package uz.imaan.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.imaan.entity.User;
import uz.imaan.entity.status.Status;
import uz.imaan.service.UserService;

public class MyBot extends TelegramLongPollingBot {

    private UserService userService = new UserService();

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !   update.getMessage().hasText()) return;

        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        User user = userService.addUser(chatId);
        Status status = userService.addStatus(chatId);

        try {
            if (text.equals("/start")) {
                userService.saveStatus(chatId, Status.ENTER_NAME);
                message(chatId, "Enter your name:");
                return;
            }

            if (text.equals("/restart")) {
                userService.restart(chatId);
                message(chatId, "Bot has been restarted.");
                return;
            }

            if(text.equals("/users")){
                message(chatId,userService.getAllUsers());
            }
            if(text.equals("/edit")){
                if(user.getName() == null){
                    message(chatId,"Please  /start to register first.");
                    return;
                }

                message(chatId, """
                        What do you want to change ?
                        1 - name,
                        2 - age
                        """);
                userService.saveStatus(chatId,Status.EDIT_STATUS);
                return;
            }


            switch (status) {

                case EDIT_STATUS:
                    if(text.equals("1")){
                        userService.saveStatus(chatId,Status.EDIT_NAME);
                        message(chatId,"Enter new name:");
                    } else if(text.equals("2")){
                        userService.saveStatus(chatId,Status.EDIT_AGE);
                        message(chatId,"Enter new age:");
                    } else {
                        message(chatId,"Please choose 1 or 2.");
                    }
                    break;
                case ENTER_NAME:
                    user.setName(text);
                    userService.saveStatus(chatId, Status.ENTER_AGE);
                    message(chatId, "Enter your age:");
                    break;

                case ENTER_AGE:
                    try {
                        int age = Integer.parseInt(text);            // yosh → age

                        if (age < 0 || age > 100) {
                            message(chatId, "Age must be between 0 and 100.");
                            return;
                        }

                        user.setAge(age);                            // setYosh → setAge
                        userService.saveStatus(chatId, Status.COMPLETED);
                        message(chatId, "Registration successful!\n" +
                                "ID: " + user.getId() + "\n" +
                                "Name: " + user.getName() + "\n" +  // getIsm → getName
                                "Age: " + user.getAge());            // getYosh → getAge
                    } catch (Exception e) {
                        message(chatId, "Please enter a number only.");
                    }
                    break;

                case COMPLETED:
                    message(chatId, "You are already registered.");
                    break;

                case EDIT_NAME:
                    user.setName(text);
                    userService.saveStatus(chatId, Status.COMPLETED);
                    message(chatId, "Name updated successfully!" + text);
                    break;

                    case EDIT_AGE:
                        try{
                            int age = Integer.parseInt(text);
                            if(age < 0 || age > 100) {
                                message(chatId, "Age must be between 0 and 100.");
                                return;
                            }
                            user.setAge(age);
                            userService.saveStatus(chatId, Status.COMPLETED);
                            message(chatId, "Age updated successfully!" + text);
                        } catch (Exception e){
                            message(chatId, "Please enter a number only.");
                        }
                        break;

                default:
                    message(chatId, "Send /start to use the bot.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void message(Long chatId, String text) throws Exception {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        execute(sendMessage);
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public String getBotUsername() {

        return "";
    }
}