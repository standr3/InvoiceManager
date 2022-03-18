import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Manager {
    // Singleton pattern
    private static final Manager instance = new Manager();

    public Vector<Product> products = new Vector<>();

    public ArrayList<Store> stores = new ArrayList<>();
    public HashMap<String, HashMap<String, Double>> taxes = new HashMap<>();
    public ArrayList<String> storeTypes = new ArrayList<>();

    private Manager() {
    }

    public static Manager getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        FileScanner fScanner = new FileScanner();
        Vector<Product> products = fScanner.getProductsFromFile("products.in");
        HashMap<String, HashMap<String, Double>> taxes = fScanner.getTaxesFromFile("taxes.in");
        ArrayList<Store> stores = fScanner.getStoresFromFile("invoices.in", products, taxes);
        ArrayList<String> storeTypes = fScanner.getStoreTypes();
        Manager manager = Manager.getInstance();

        manager.setStores(stores);
        manager.setProducts(products);
        manager.setTaxes(taxes);
        manager.setStoreTypes(storeTypes);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("out.txt"));
            pw.write(instance.toString());
            pw.close();
            System.out.println(instance);
            //System.setOut(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProducts(Vector<Product> products) {
        this.products = products;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void setTaxes(HashMap<String, HashMap<String, Double>> taxes) {
        this.taxes = taxes;
    }

    public void setStoreTypes(ArrayList<String> storeTypes) {
        this.storeTypes = storeTypes;
    }

    public String toString() {
        String result = "";

        for (String type : storeTypes) {
            result += type + "\n";
            for (int i = 0; i < stores.size(); ++i) {

                if (type.equals(stores.get(i).type.split(" ")[1]))
                    result += stores.get(i) + "\n";
            }
        }
        return result.trim();
    }


}
