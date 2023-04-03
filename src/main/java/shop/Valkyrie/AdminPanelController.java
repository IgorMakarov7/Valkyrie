package shop.Valkyrie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Controller
public class AdminPanelController {

    @RequestMapping(value = "/admin-panel", method = RequestMethod.GET)
    public String getAdminLoginPage(Model model) {
        AdminUser adminUser = new AdminUser();
        model.addAttribute("adminUser", adminUser);
        return "admin_login";
    }

    @RequestMapping(value = "/admin-panel", method = RequestMethod.POST)
    public String postAdminLoginPage(
            @RequestParam(value = "numberPage", required = false, defaultValue = "1") String numberPageStr,
            @ModelAttribute("adminUser") AdminUser adminUser,
            Model model) {
        if (adminUser.getLogin().equals("root")) {
            if (adminUser.getPassword().equals("123456789")) {

                int numberPage = Integer.parseInt(numberPageStr);
                List<Card> cards = CardReader.readCardsForPage(numberPage);
                model.addAttribute("cards", cards);
                model.addAttribute("numberPage", numberPage);
                model.addAttribute("isShowNext", CardReader.isNextPageExits(numberPage));
                model.addAttribute("isShowBack", CardReader.isPreviousPageExits(numberPage));
                return "admin_panel";
            }
        }
        model.addAttribute("message", "Неверный логин или пароль");
        return "admin_login";
    }

    @RequestMapping(value = "/admin-panel/editCard", method = RequestMethod.POST)
    public String editCardPage(
            @RequestParam(value = "cardId") int cardId,
            @ModelAttribute("adminUser") AdminUser adminUser,
            Model model
    ) {
        Card card = CardReader.readCardById(cardId);
        model.addAttribute("card", card);
        model.addAttribute("adminUser", adminUser);
        return "card_editor";
    }

    @RequestMapping(value = "/admin-panel/change", method = RequestMethod.POST)
    public String changeCard(
            @ModelAttribute("card") Card card,
            @ModelAttribute("adminUser") AdminUser adminUser,
            @RequestParam("image") MultipartFile file,
            Model model
    ) {
        if (adminUser.getLogin().equals("root")) {
            if (adminUser.getPassword().equals("123456789")) {

                if (!Objects.equals(file.getOriginalFilename(), "")) {
                    String uniqueFileName = CardReader.getUniqueFileName(file.getOriginalFilename());
                    String path = System.getProperty("user.dir") + "/src/main/resources/static/images/" + uniqueFileName;
                    try {
                        Files.write(Path.of(path), file.getBytes());
                        card.setImagePath("/images/" + uniqueFileName);
                    } catch (IOException e) {
                        model.addAttribute("adminUser", adminUser);
                        return "admin_login";
                    }
                } else {
                    Card oldCard = CardReader.readCardById(card.getId());
                    card.setImagePath(oldCard.getImagePath());
                }

                model.addAttribute("card", card);
                CardSaver.saveCard(card);
                model.addAttribute("adminUser", adminUser);
                return "card_editor";
            }
        }
        model.addAttribute("adminUser", adminUser);
        return "admin_login";
    }
}
