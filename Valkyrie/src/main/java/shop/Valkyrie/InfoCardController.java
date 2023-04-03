package shop.Valkyrie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InfoCardController {

    @RequestMapping(value = "/cardInfo", method = RequestMethod.GET)
    public String getCardInfoPage(
            @RequestParam("numberCard") int numberCard,
            Model model
    ) {
        Card card = CardReader.readCardById(numberCard);
        String[] cards = card.getFullDescription().split("\n");
        model.addAttribute("splitFullDescription", cards);
        model.addAttribute("card", card);
        return "info_card";
    }
}
