package shop.Valkyrie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CardSaver {
    public static void saveCard(Card card) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = Card.getPathToCardById(card.getId());
        File file = new File(path);
        try {
            objectMapper.writeValue(file, card);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int saveNewCardAndGetId(Card card) {
        int id = CardReader.getMaxIdCardInDatabase() + 1;
        card.setId(id);
        saveCard(card);
        return id;
    }
}
