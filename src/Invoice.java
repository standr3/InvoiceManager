import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

public class Invoice {
    public String name;
    public Vector<InvoiceItem> items;
    public ArrayList<String> countries;

    public Invoice(String name, Vector<InvoiceItem> items, ArrayList<String> countries) {
        this.name = name;
        this.items = items;
        this.countries = countries;
    }

    public Double getTotalTaxFree() {
        Double res = 0d;
        for (int i = 0; i < this.items.size(); ++i) {
            res += this.items.get(i).getProduct().getPrice() * this.items.get(i).getQuantity();
        }
        return res;
    }

    public Double getTotal() {
        return this.getTotalTaxFree() + this.getTaxes();
    }

    public Double getTaxes() {
        Double res = 0d;
        for (int i = 0; i < this.items.size(); ++i) {
            res += this.items.get(i).getQuantity()
                    * this.items.get(i).getTax() * this.items.get(i).getProduct().getPrice() / 100;
        }
        return res;
    }

    public Double getCountryTotalTaxFree(String country) {
        Double res = 0d;
        for (int i = 0; i < this.items.size(); ++i) {
            if (country.equals(this.items.get(i).getProduct().getCountry()))
                res += this.items.get(i).getProduct().getPrice() * this.items.get(i).getQuantity();
        }
        return res;
    }

    public Double getCountryTotal(String country) {
        return this.getCountryTotalTaxFree(country) + this.getCountryTaxes(country);
    }

    public Double getCountryTaxes(String country) {
        Double res = 0d;
        for (int i = 0; i < this.items.size(); ++i) {
            if (country.equals(this.items.get(i).getProduct().getCountry()))
                res += this.items.get(i).getQuantity()
                        * this.items.get(i).getQuantity() * this.items.get(i).getProduct().getPrice() / 100;
        }
        return res;
    }

    public void addProdus(InvoiceItem item) {
        this.items.add(item);
    }

    public String toString() {
        String result = "\n" + this.name + "\n\n";
        DecimalFormat df = new DecimalFormat("#.####");
        result += "Total " + df.format(this.getTotalTaxFree()) + " " + df.format(this.getTotal()) + "\n\n"
                + "Tara\n";
        for (String country : countries) {
            if (this.getCountryTotalTaxFree(country) != 0)
                result += country + " " + df.format(this.getCountryTotalTaxFree(country)) + " "
                        + df.format(this.getCountryTotal(country)) + "\n";
            else
                result += country + " 0\n";
        }
        return result;
    }
}
