package fruit.stand.company;

import fruit.stand.people.Cashier;
import fruit.stand.products.Fruit;
import fruit.stand.products.Meat;
import fruit.stand.products.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Store {
    private Cashier cashier;
    private int balance;
    private final List<Fruit> fruits;
    private final List<Meat> meats;
    protected List<Transaction> pendingTransactions;
    private final List<Transaction> transactionHistory;
    private final double pricePercent;

    public Store(int balance) {
        this.balance = balance;
        this.fruits = new ArrayList<>();
        this.meats = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
        this.pricePercent = 1.25;
    }


    protected void hire(Cashier cashier) {
        if (this.cashier == null) {
            setCashier(cashier);
        }
    }

    protected void handlePendingtransactions() {
        List<Transaction> transactionsToRemove = new ArrayList<>();
        for (Transaction transaction : pendingTransactions) {
            if (transaction.getTo().getName().equals(cashier.getName())) {
                sell(transaction);
                transactionHistory.add(transaction);
                transactionsToRemove.add(transaction);
            } else if (transaction.getFrom().getName().equals(cashier.getName())) {
                buy(transaction.getProduct());
                transactionHistory.add(transaction);
                transactionsToRemove.add(transaction);

            }
        }
        pendingTransactions.removeAll(transactionsToRemove);
    }

    protected void addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    private void sell(Transaction transaction) {
        if (transaction.getProduct().getExpDate().isAfter(LocalDate.now())) {
            transaction.getFrom().pay(transaction);
            setBalance(balance + (transaction.getProduct().getCost() * transaction.getAmount()));
            //how much the customer paid divided by how much it costs = amount
            transaction.getProduct().setAmount((transaction.getProduct().getAmount() - transaction.getAmount()));
            remove(transaction.getProduct());
        } else {
            if (transaction.getProduct().getExpDate().isBefore(LocalDate.now())) {
                int sueCharge = (int) Math.floor(Math.random() * (balance / 2)) + 1;
                System.out.println("Product is expired customer sued for $" + sueCharge);
                balance -= sueCharge;
                remove(transaction.getProduct());

            } else {
                System.out.println(transaction.getFrom().getName() + " does not have enough cash they left.");

            }

        }
    }

    private void remove(Product product) {
        switch (product.getClass().getSimpleName().toLowerCase(Locale.ROOT)) {
            case "fruit":
                for (Fruit fruit : fruits) {
                    if (fruit.getType().equals(product.getType()) && fruit.getExpDate() == product.getExpDate()) {
                        fruit.setAmount(fruit.getAmount() - product.getAmount());
                        if (fruit.getAmount() < 1) {
                            fruits.remove(fruit);
                        }
                        break;
                    }
                }
                break;
            case "meat":
                for (Meat meat : meats) {
                    if (meat.getType().equals(product.getType()) && meat.getExpDate() == product.getExpDate()) {
                        meat.setAmount(meat.getAmount() - product.getAmount());
                        if (meat.getAmount() < 1) {
                            meats.remove(meat);
                        }
                        break;
                    }
                }
                break;
        }
    }

    private void buy(Product product) {
        if (product.getCost() > balance) {
            System.out.println("Not enough money to buy \nname:" + product.getName() + "\ntype:" + product.getType());
        } else {
            setBalance(balance - product.getCost());
            add(product);
        }
    }

    private void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    private void setBalance(int balance) {
        this.balance = balance;
    }


    private void add(Product product) {
        switch (product.getClass().getSimpleName().toLowerCase(Locale.ROOT)) {
            case "fruit":
                fruits.add((Fruit) product);
                break;
            case "meat":
                meats.add((Meat) product);
                break;
        }
    }

    protected List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    protected void displayProducts() {
        System.out.println(getFruits());
        System.out.println(getMeats());
    }

    protected double getPricePercent() {
        return pricePercent;
    }

    protected List<Meat> getMeats() {
        return meats;
    }

    protected List<Fruit> getFruits() {
        return fruits;
    }

    protected int getBalance() {
        return balance;
    }

    public Cashier getCashier() {
        return cashier;
    }
}
