import java.util.ArrayList;
import java.util.Vector;

// Contains invoices of a big size store
public class HyperMarket extends Store {
    public HyperMarket(String name, Vector<Invoice> invoices, ArrayList<String> countries) {
        super(name, invoices, countries);
    }

    @Override
    public Double getLoweredPercentage() {
        for (Invoice i : super.invoices) {
            if (i.getTotal() >= (super.getTotal() * 10) / 100) {
                return 0.01d;
            }
        }
        return 0d;
    }
}
