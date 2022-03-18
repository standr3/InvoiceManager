// Contains invoices of a medium-sized store

import java.util.ArrayList;
import java.util.Vector;

public class MediumMarket extends Store {

    public MediumMarket(String name, Vector<Invoice> invoices, ArrayList<String> countries) {
        super(name, invoices, countries);
    }

    public ArrayList<String> getTypes() {
        ArrayList<String> list = new ArrayList<>();
        for (Invoice i : super.invoices) {
            for (InvoiceItem j : i.items) {
                list.add(j.getProduct().getType());
            }
        }
        return list;
    }

    public Double getTypeTotal(String type) {
        Double total = 0d;
        for (Invoice i : super.invoices) {
            for (InvoiceItem j : i.items) {
                if (type.equals(j.getProduct().getType()))
                    total += j.getQuantity() * (j.getQuantity() + j.getTax());
            }
        }
        return total;
    }

    @Override
    public Double getLoweredPercentage() {
        ArrayList<String> types = this.getTypes();
        for (String t : types) {
            if (this.getTypeTotal(t) >= this.getTotal() / 2)
                return 0.05d;
        }
        return 0d;
    }
}
