import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileScanner {

    private final ArrayList<String> countries = new ArrayList<>();
    private final ArrayList<String> storeTypes = new ArrayList<>();
    private final ArrayList<String> productTypes = new ArrayList<>();

    FileScanner() {
    }


    // Factory pattern
    private Store getStore(String name, String shop_name, Vector<Invoice> list, ArrayList<String> tariOrigine) {
        if (name.equals("MiniMarket"))
            return new MiniMarket(shop_name, list, tariOrigine);
        else if (name.equals("MediumMarket"))
            return new MediumMarket(shop_name, list, tariOrigine);
        else if (name.equals("HyperMarket"))
            return new HyperMarket(shop_name, list, tariOrigine);
        return null;
    }

    private Product getProduct(List<Product> list, String name, String country) {
        for (Product p : list) {
            if (p.getName().equals(name) && p.getCountry().equals(country))
                return p;
        }
        return null;
    }

    private Double findTax(HashMap<String, HashMap<String, Double>> map, String type, String country) {
        for (Map.Entry<String, HashMap<String, Double>> entry1 : map.entrySet()) {
            for (Map.Entry<String, Double> entry2 : entry1.getValue().entrySet()) {
                if (type.equals(entry1.getKey()) && country.equals(entry2.getKey()))
                    return entry2.getValue();
            }
        }
        return -1d;
    }
//    private HashMap<String, HashMap<String, Double>> getTaxesFromFile (String filename) {
//        HashMap<String, HashMap<String, Double>> map = new HashMap<>();
//        File file = new File(filename);
//        if (!file.exists())
//
//            return null;
//        Scanner scan;
//        try {
//            scan = new Scanner(file);
//            String first_line = scan.nextLine();
//            String [] data = first_line.split(" ");
//            ArrayList <String> countries = new ArrayList <>();
//            for (String s : data) {
//                countries.add(s);
//                this.countries.add(s);
//            }
//            while (scan.hasNextLine()) {
//                String line = scan.nextLine();
//                String[] members = line.split(" ");
//                HashMap<String, Double> map_value = new HashMap<>();
//                int i = 0;
//                for (String c : countries) {
//                    map_value.put(c, Double.parseDouble(members[i++ + 1]));
//                }
//                map.put(members[0], map_value);
//            }
//            scan.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return map;
//    }

    public Vector<Product> getProductsFromFile(String filename) {
        Vector<Product> list = new Vector<>();
        File file = new File(filename);
        if (!file.exists())
            return null;
        Scanner scan = null;
        try {
            scan = new Scanner(file);
            String first_line = scan.nextLine();
            String[] data = first_line.split(" ");
            ArrayList<String> countries = new ArrayList<>();
            for (int i = 2; i < data.length; ++i)
                countries.add(data[i]);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] members = line.split(" ");
                for (int i = 0; i < countries.size(); ++i) {
                    list.add(new Product(members[0], members[1], countries.get(i), Double.parseDouble(members[i + 2])));
                    this.productTypes.add(members[1]);
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HashMap<String, HashMap<String, Double>> getTaxesFromFile(String filename) {
        HashMap<String, HashMap<String, Double>> map = new HashMap<>();
        File file = new File(filename);
        if (!file.exists())
            return null;
        Scanner scan;
        try {
            scan = new Scanner(file);
            String first_line = scan.nextLine();
            String[] data = first_line.split(" ");
            ArrayList<String> countries = new ArrayList<>();
            for (int i = 1; i < data.length; ++i) {
                countries.add(data[i]);
                this.countries.add(data[i]); // adaug in TreeSet-ul de tari de origine
            }
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] members = line.split(" ");
                HashMap<String, Double> map_value = new HashMap<>();


                for (int i = 0; i < countries.size(); ++i) {

                    map_value.put(countries.get(i), Double.parseDouble(members[i + 1]));
                }
                map.put(members[0], map_value);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public ArrayList<Store> getStoresFromFile(String filename, Vector<Product> products, HashMap<String, HashMap<String, Double>> taxes) {

        ArrayList<Store> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists())
            return null;
        Scanner scan;
        try {
            scan = new Scanner(file);
            int count = 0;
            HashMap<Integer, ShopType> map = new HashMap<>(); // count + magazin
            HashMap<Integer, Vector<Invoice>> map2 = new HashMap<>(); // count + vector factura
            String line, data = null;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.contains("Magazin")) {
                    String[] shop_data = line.split(":");
                    this.storeTypes.add(shop_data[1]);
                    map.put(count++, new ShopType(shop_data[1], shop_data[2]));
                }

                if (line.contains("Factura")) {
                    data = scan.nextLine();
                    Vector<InvoiceItem> items = new Vector<>();
                    while (data.length() > 0 && scan.hasNextLine()) {
                        if (!data.contains("Denumire")) {
                            String[] info = data.split(" ");
                            Product product = getProduct(products, info[0], info[1]);
                            if (product != null) {
                                double tax = findTax(taxes, product.getType(), info[1]);
                                int quantity = Integer.parseInt(info[2]);
                                items.add(new InvoiceItem(product, tax, quantity));
                            }
                        }
                        data = scan.nextLine();
                    }
                    Invoice invoice = new Invoice(line, items, this.countries);
                    if (map2.get(count - 1) == null) {
                        map2.put(count - 1, new Vector<>());
                        map2.get(count - 1).add(invoice);
                    } else {
                        map2.get(count - 1).add(invoice);
                    }
                }
            }

            String[] parsing = data.split(" ");
            Product product = getProduct(products, parsing[0], parsing[1]);
            if (product != null) {
                double tax = findTax(taxes, product.getType(), parsing[1]);
                int quantity = Integer.parseInt(parsing[2]);
                InvoiceItem item = new InvoiceItem(product, tax, quantity);
                Vector<Invoice> last_invoice = map2.get(count - 1);
                Invoice last = last_invoice.get(last_invoice.size() - 1);
                last_invoice.remove(last);
                last.addProdus(item);
                last_invoice.add(last);
                map2.put(count - 1, last_invoice);
            }
            // construim obiectele de tip Magazin si le adaugam in lista de obiecte Magazin
            for (int i = 0; i < count; ++i) {
                Store store = getStore(map.get(i).name, map.get(i).type, map2.get(i), this.countries);
                list.add(store);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getStoreTypes() {
        return this.storeTypes;
    }

    private class ShopType {
        public String name;
        public String type;

        public ShopType(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String toString() {
            String result = "Name: " + this.type + "\nType: " + this.type + "\n";
            return result;
        }
    }
}
