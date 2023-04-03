package shop.Valkyrie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHomePage(
            @RequestParam(value = "numberPage", required = false, defaultValue = "1") String numberPageStr,
            @RequestParam(value = "isScrolling", required = false, defaultValue = "false") boolean isScrolling,
            Model model) {

        int numberPage = Integer.parseInt(numberPageStr);
        List<Card> cards = CardReader.readCardsForPage(numberPage);
        model.addAttribute("cards", cards);

        model.addAttribute("numberPage", numberPage);
        model.addAttribute("isScrolling", isScrolling);
        model.addAttribute("isShowNext", CardReader.isNextPageExits(numberPage));
        model.addAttribute("isShowBack", CardReader.isPreviousPageExits(numberPage));

        return "home_page";
    }
}
