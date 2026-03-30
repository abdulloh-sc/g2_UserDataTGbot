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
        if(update.hasMessage() || ! update.getMessage().hasText()) return;

        Long chatId = update.getMessage().getChatId();

        String text = update.getMessage().getText();

        User user = userService.addUser(chatId);

        Status status = userService.addStatus(chatId);

        try{
            if (text.equals("/start")){
                userService.saveStatus(chatId, Status.ISM_KIRITISH);
                message(chatId,"Ismingizni kiriting");
                return;
            }
            if (text.equals("/restart")){
                userService.restart(chatId);
                message(chatId,"Bot qayta ishga tushirildi");
                return;
            }
            switch (status){
                case ISM_KIRITISH:
                    user.setIsm(text);
                    userService.saveStatus(chatId, Status.YOSH_KIRITISH);
                    message(chatId,"Yoshingizni kiriting");
                    break;
                case YOSH_KIRITISH:
                    try{
                        int yosh = Integer.parseInt(text);

                        if (yosh < 0 || yosh > 100){
                            message(chatId,"Yoshingiz 0 - 100 oraligida bolishi kerak ");
                            return;
                        }

                        user.setYosh(yosh);
                        userService.saveStatus(chatId, Status.YAKUNLANDI);
                        message(chatId,"Ro'yxatdan o'tdingiz \n" +
                                user.getId() + "\n" +
                                user.getIsm() + "\n" +
                                user.getYosh());
                    } catch (Exception e){
                        message(chatId,"Faqat son kiriting ");
                    }
                    break;

                case YAKUNLANDI:
                    message(chatId,"Siz allaqachon ro'yxatdan o'tgansiz");
                    break;

                default:
                    message(chatId,"Botdan foydalanish uchun /start buyrug'ini bering");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    private void message(Long chatId, String text) throws Exception{
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        execute(sendMessage);
    }

    @Override
    public String getBotToken(){
        return "8681756358:AAGoqS_Rgz-iTFHjn3yrnYssA7U2NthLch8";
    }

    @Override
    public String getBotUsername() {
        return "testjavag2_bot";
    }
}
