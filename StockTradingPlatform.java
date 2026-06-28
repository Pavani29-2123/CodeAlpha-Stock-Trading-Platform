import java.util.*;

class Stock {

    private String symbol;
    private String companyName;
    private double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }
}

class Portfolio {

    private HashMap<String, Integer> holdings = new HashMap<>();

    public void buyStock(String symbol, int quantity) {

        holdings.put(symbol,
                holdings.getOrDefault(symbol, 0) + quantity);

    }

    public boolean sellStock(String symbol, int quantity) {

        if (!holdings.containsKey(symbol))
            return false;

        int current = holdings.get(symbol);

        if (current < quantity)
            return false;

        if (current == quantity)
            holdings.remove(symbol);
        else
            holdings.put(symbol, current - quantity);

        return true;
    }

    public void displayPortfolio(List<Stock> market) {

        System.out.println("\n========== Portfolio ==========");

        if (holdings.isEmpty()) {
            System.out.println("No Stocks Purchased.");
            return;
        }

        double total = 0;

        for (Stock stock : market) {

            if (holdings.containsKey(stock.getSymbol())) {

                int qty = holdings.get(stock.getSymbol());

                double value = qty * stock.getPrice();

                total += value;

                System.out.println(stock.getSymbol() +
                        " | Qty : " + qty +
                        " | Price : ₹" + stock.getPrice() +
                        " | Value : ₹" + value);
            }
        }

        System.out.println("------------------------------");
        System.out.println("Portfolio Value : ₹" + total);
    }
}

class User {

    private String name;
    private double balance;
    private Portfolio portfolio;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        portfolio = new Portfolio();
    }

    public double getBalance() {
        return balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void buy(Stock stock, int quantity) {

        double cost = stock.getPrice() * quantity;

        if (cost > balance) {
            System.out.println("Insufficient Balance!");
            return;
        }

        balance -= cost;

        portfolio.buyStock(stock.getSymbol(), quantity);

        System.out.println("Stock Purchased Successfully.");
    }

    public void sell(Stock stock, int quantity) {

        if (portfolio.sellStock(stock.getSymbol(), quantity)) {

            balance += stock.getPrice() * quantity;

            System.out.println("Stock Sold Successfully.");

        } else {

            System.out.println("Invalid Transaction.");

        }
    }
}

public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        List<Stock> market = new ArrayList<>();

        market.add(new Stock("TCS", "Tata Consultancy Services", 4200));
        market.add(new Stock("INFY", "Infosys", 1600));
        market.add(new Stock("RELIANCE", "Reliance Industries", 2900));
        market.add(new Stock("HDFC", "HDFC Bank", 1800));
        market.add(new Stock("WIPRO", "Wipro", 500));

        User user = new User("Rahul", 100000);

        int choice;

        do {

            System.out.println("\n========== Stock Trading Platform ==========");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Balance");
            System.out.println("6. Exit");

            System.out.print("Enter Choice : ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showMarket(market);
                    break;

                case 2:

                    showMarket(market);

                    System.out.println("Enter Stock Symbol : ");
                    String buy = sc.next().toUpperCase();

                    System.out.println("Enter Quantity : ");
                    int qty = sc.nextInt();

                    Stock stock = findStock(market, buy);

                    if (stock != null)
                        user.buy(stock, qty);
                    else
                        System.out.println("Stock Not Found.");

                    break;

                case 3:

                    user.getPortfolio().displayPortfolio(market);

                    System.out.println("Enter Stock Symbol : ");
                    String sell = sc.next().toUpperCase();

                    System.out.println("Enter Quantity : ");
                    int sellQty = sc.nextInt();

                    Stock s = findStock(market, sell);

                    if (s != null)
                        user.sell(s, sellQty);
                    else
                        System.out.println("Stock Not Found.");

                    break;

                case 4:
                    user.getPortfolio().displayPortfolio(market);
                    break;

                case 5:
                    System.out.println("Available Balance : ₹" + user.getBalance());
                    break;

                case 6:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 6);
    }

    static void showMarket(List<Stock> market) {

        System.out.println("\n========== Market Data ==========");

        for (Stock stock : market) {

            System.out.println(stock.getSymbol() +
                    " | " +
                    stock.getCompanyName() +
                    " | ₹" +
                    stock.getPrice());

        }
    }

    static Stock findStock(List<Stock> market, String symbol) {

        for (Stock stock : market) {

            if (stock.getSymbol().equalsIgnoreCase(symbol))
                return stock;

        }

        return null;
    }
}