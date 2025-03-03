package bot.telegram.menfess.utils.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class StartCommandKeyboard {

    public static InlineKeyboardMarkup notRegister(){
        InlineKeyboardButton buttonAgree = InlineKeyboardButton.builder()
                .text("Mendaftar")
                .callbackData("mendaftar")
                .build();
        InlineKeyboardButton buttonDisagree = InlineKeyboardButton.builder()
                .text("Tidak Mendaftar")
                .callbackData("disagree")
                .build();

        InlineKeyboardButton help = InlineKeyboardButton.builder()
                .text("Bantuan")
                .callbackData("help")
                .build();
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(buttonAgree, buttonDisagree)))
                .keyboard(List.of(List.of(help)))
                .build();
    }
    public static InlineKeyboardMarkup register() {
        InlineKeyboardButton buttonRegister = InlineKeyboardButton.builder()
                .text("Akun Anda")
                .callbackData("akun")
                .build();
        InlineKeyboardButton buttonHelp = InlineKeyboardButton.builder()
                .text("Bantuan")
                .callbackData("help")
                .build();
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(buttonRegister, buttonHelp)))
                .build();
    }
}
