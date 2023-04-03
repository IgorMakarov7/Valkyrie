package shop.Valkyrie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherPagesController {
    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/contacts")
    public String getContactsPage() {
        return "contacts";
    }

    @GetMapping("/socials")
    public String getSocialsPage() {
        return "socials";
    }

    @GetMapping("/creators")
    public String getCreatorsPage() {
        return "creators";
    }

}