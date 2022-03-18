
// Actual item that can be found on an invoice

//
//
//
// obj of type Product are created by parsing the 'products.in' file
public class Product {

    private String name;
    private String type;
    private String country; // country
    private Double price;

    public Product(String name, String type, String origin, Double price) {
        this.name = name;
        this.type = type;
        this.country = origin;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", price=" + price +
                '}';
    }

}
