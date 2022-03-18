import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

public abstract class Store implements IStore {
    public String name;
    public Vector<Invoice> invoices;
    public String type;
    public ArrayList<String> countries;

    public Store(String name, Vector<Invoice> invoices, ArrayList<String> countries) {
        this.name = name;
        this.invoices = invoices;
        this.type = this.getClass().toString();
        this.countries = countries;
    }

//    Double getLoweredPercentage();

    public Double getTotalTaxFree() {
        Double res = 0d;
        for (Invoice i : invoices) {
            res += i.getTotalTaxFree();
        }
        return res;
    }

    public Double getTotal() {
        Double res = 0d;
        for (Invoice i : invoices) {
            res += i.getTotal();
        }
        return res;
    }

    public Double getTotalLowTax() {
        return this.getTotal() * (1d - this.getLoweredPercentage());
    }

    public Double getCountryTotalTaxFree(String country) {
        Double res = 0d;
        for (Invoice i : invoices) {
            res += i.getCountryTotalTaxFree(country);
        }
        return res;
    }

    public Double getCountryTotal(String country) {
        Double res = 0d;
        for (Invoice i : invoices) {
            res += i.getCountryTotal(country);
        }
        return res;
    }

    public Double getCountryTotalLowTax(String country) {
        return this.getCountryTotal(country) * (1d - this.getLoweredPercentage());
    }

    public abstract Double getLoweredPercentage();


    public String toString() {
        DecimalFormat df = new DecimalFormat("#.####"); // aproximarea procentelor vanzarilor
        String result = this.name + "\n\n" + "Total " + df.format(this.getTotalTaxFree()) + " "
                + df.format(this.getTotal()) + " " + df.format(this.getTotalLowTax()) + "\n\nTara\n";

        for (String c : countries) {
            if (this.getCountryTotalTaxFree(c) != 0)
                result += c + " " + df.format(this.getCountryTotalTaxFree(c))
                        + " " + df.format(this.getCountryTotal(c))
                        + " " + df.format(this.getCountryTotalLowTax(c)) + "\n";
            else
                result += c + " 0\n";
        }
        for (int i = 0; i < this.invoices.size(); ++i) {
            result += this.invoices.get(i);
        }
        result = result.replaceAll(",", ".");
        return result;
    }

}
