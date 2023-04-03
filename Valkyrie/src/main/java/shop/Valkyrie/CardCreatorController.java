package shop.Valkyrie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Controller
public class CardCreatorController {

    @RequestMapping(value = "/admin-panel/creator", method = RequestMethod.POST)
    public String getCreatorCard(
            @ModelAttribute("adminUser") AdminUser adminUser,
            Model model
    ) {
        if (adminUser.getLogin().equals("root")) {
            if (adminUser.getPassword().equals("123456789")) {
                model.addAttribute("card", new Card());
                model.addAttribute("adminUser", adminUser);
                return "card_creator";
            }
        }
        return "admin_login";
    }

    @RequestMapping(value = "/admin-panel/creator/create", method = RequestMethod.POST)
    public RedirectView postCreatorCard(
            @ModelAttribute("adminUser") AdminUser adminUser,
            @ModelAttribute("card") Card card,
            @RequestParam("image") MultipartFile file,
            RedirectAttributes attributes,
            Model model
    ) {
        if (adminUser.getLogin().equals("root")) {
            if (adminUser.getPassword().equals("123456789")) {
                if (cardFieldsIsFill(card) && isUserInputImageCorrectAndSaveHisAndSetPath(file, card)) {
                    int id = CardSaver.saveNewCardAndGetId(card);
                    return new RedirectView("/cardInfo?numberCard=" + id);
                }
            }
        }
        return new RedirectView("/admin-login");
    }

    private boolean cardFieldsIsFill(Card card) {
        return !Objects.equals(card.getHeader(), "")
                && !Objects.equals(card.getDescription(), "")
                && !Objects.equals(card.getFullDescription(), "")
                && !Objects.equals(card.getPrice(), "");
    }

    private boolean isUserInputImageCorrectAndSaveHisAndSetPath(MultipartFile file, Card card) {

        if (file == null) {
            return false;
        }

        if (!Objects.equals(file.getOriginalFilename(), "")) {
            String uniqueFileName = CardReader.getUniqueFileName(file.getOriginalFilename());
            String path = System.getProperty("user.dir") + "/src/main/resources/static/images/" + uniqueFileName;
            try {
                Files.write(Path.of(path), file.getBytes());
                card.setImagePath("/images/" + uniqueFileName);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
