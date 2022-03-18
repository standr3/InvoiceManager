import java.util.ArrayList;
import java.util.Vector;

// contains invoices of a small store
public class MiniMarket extends Store {

    public MiniMarket(String name, Vector<Invoice> invoices, ArrayList<String> countries) {
        super(name, invoices, countries);
    }

    public ArrayList<String> getTariOrigine() {
        ArrayList<String> list = new ArrayList<>();
        for (Invoice i : super.invoices) {
            for (InvoiceItem j : i.items) {
                list.add(j.getProduct().getCountry());
            }
        }
        return list;
    }

    @Override
    public Double getLoweredPercentage() {
        ArrayList<String> countries = this.getTariOrigine();
        for (String c : countries) {
            if (this.getCountryTotal(c) >= this.getTotal() / 2)
                return 0.1d;
        }
        return 0d;
    }
}
