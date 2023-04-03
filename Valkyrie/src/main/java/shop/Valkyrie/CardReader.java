package shop.Valkyrie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardReader {
    public static List<Card> readCardsForPage(int numberPage) {

        int idCurrentCard = (numberPage - 1) * 9 + 1;
        int idLastCard = idCurrentCard + 9;

        ObjectMapper objectMapper = new ObjectMapper();

        List<Card> cards = new ArrayList<>();
        while (idCurrentCard < idLastCard) {
            String path = Card.getPathToCardById(idCurrentCard);
            Card card = readCard(objectMapper, path);
            if (card == null) {
                break;
            }
            cards.add(card);
            idCurrentCard++;
        }
        return cards;
    }

    public static Card readCardById(int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = Card.getPathToCardById(id);
        return readCard(objectMapper, path);
    }

    private static Card readCard(ObjectMapper objectMapper, String path) {
        Card card;
        File file = new File(path);
        try {
            card = objectMapper.readValue(file, Card.class);
        } catch (IOException e) {
            return null;
        }
        return card;
    }

    public static Boolean isNextPageExits(int numberPage) {
        int maxIdCurrentPage = numberPage * 9;

        String filePathString = Card.getPathToCardById(maxIdCurrentPage + 1);
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }

    public static Boolean isPreviousPageExits(int numberPage) {
        int minIdCurrentPage = (numberPage - 1) * 9 + 1;

        String filePathString = Card.getPathToCardById(minIdCurrentPage - 1);
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }

    public static int getMaxIdCardInDatabase() {
        int maxIndex = 1;
        while (true) {
            String pathToFile = Card.getPathToCardById(maxIndex);
            File f = new File(pathToFile);
            if (f.exists() && !f.isDirectory()) {
                maxIndex++;
            } else {
                maxIndex--;
                break;
            }
        }
        return maxIndex;
    }

    public static String getUniqueFileName(String fileName) {
        String directoryPath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
        File fileOfUserPath = new File(directoryPath + fileName);
        if (!fileOfUserPath.exists() && !fileOfUserPath.isDirectory()) {
            return fileName;
        }
        int appendDigit = 1;
        String[] splitFileName = fileName.split("\\.");
        while (true) {
            String fileNameWithAppendDigit = splitFileName[0] + appendDigit + "." + splitFileName[1];
            File f = new File(directoryPath + fileNameWithAppendDigit);
            if (f.exists() && !f.isDirectory()) {
                appendDigit++;
            } else {
                return fileNameWithAppendDigit;
            }
        }
    }
}
