package shop.Valkyrie;

public class Card {
    private String imagePath;
    private String header;
    private String description;
    private String fullDescription;
    private String price;

    private Integer id;

    public Card() {
    }

    public Card(String imagePath, String header, String description, String price, Integer id) {
        this.imagePath = imagePath;
        this.header = header;
        this.description = description;
        this.price = price;
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CardProduct{" +
                "imagePath='" + imagePath + '\'' +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", price='" + price + '\'' +
                ", id=" + id +
                '}';
    }

    public static String getPathToCardById(int id) {
        return System.getProperty("user.dir") + "/database/" + "card" + id + ".json";
    }
}