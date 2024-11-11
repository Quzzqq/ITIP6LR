import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SalesTracker {
    private ConcurrentHashMap<String, AtomicInteger> salesCount;
    private ConcurrentHashMap<String, Double> productPrices;
    private double totalSales;

    public SalesTracker() {
        salesCount = new ConcurrentHashMap<>();
        productPrices = new ConcurrentHashMap<>();
        totalSales = 0.0;
    }

    public void addProduct(String productName, double price) {
        productPrices.putIfAbsent(productName, price);
        salesCount.putIfAbsent(productName, new AtomicInteger(0));
    }

    public void sellProduct(String productName) {
        if (salesCount.containsKey(productName)) {
            salesCount.get(productName).incrementAndGet();
            totalSales += productPrices.get(productName);
        } else {
            System.out.println("Товар не найден: " + productName);
        }
    }

    public void printSoldProducts() {
        System.out.println("Список проданных товаров:");
        for (Map.Entry<String, AtomicInteger> entry : salesCount.entrySet()) {
            String productName = entry.getKey();
            int count = entry.getValue().get();
            if (count > 0) {
                System.out.println(productName + ": " + count + " раз(а)");
            }
        }
    }

    public double getTotalSales() {
        return totalSales;
    }

    public String getMostPopularProduct() {
        return salesCount.entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().get()))
                .map(Map.Entry::getKey)
                .orElse("Нет проданных товаров");
    }

    public static void main(String[] args) {
        SalesTracker tracker = new SalesTracker();
        
        tracker.addProduct("Яблоко", 50.0);
        tracker.addProduct("Банан", 30.0);
        tracker.addProduct("Апельсин", 40.0);
        
        tracker.sellProduct("Яблоко");
        tracker.sellProduct("Банан");
        tracker.sellProduct("Яблоко");
        
        tracker.printSoldProducts();
        
        System.out.println("Общая сумма продаж: " + tracker.getTotalSales());
        
        System.out.println("Наиболее популярный товар: " + tracker.getMostPopularProduct());
    }
}