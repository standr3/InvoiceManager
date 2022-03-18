// a line from an invoice containing a product


// obj of type InvoiceItem will be created by parsing 'invoices.in'

public class InvoiceItem {
    private Product product;
    private Double tax;
    private Integer quantity;

    public InvoiceItem(Product product, Double tax, Integer quantity) {
        this.product = product;
        this.tax = tax;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
