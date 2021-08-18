package fruit.stand.company;

import fruit.stand.Transaction;
import fruit.stand.people.Cashier;
import fruit.stand.people.Person;
import fruit.stand.products.Fruit;
import fruit.stand.products.Meat;
import fruit.stand.products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Store {
    private Cashier cashier;
    private double balance;
    private List<Fruit> fruits;
    private List<Meat> meats;
    private List<Transaction> pendingTransactions;

    public Store(double balance) {
        this.balance = balance;
        this.fruits = new ArrayList<>();
        this.meats = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
    }


    public void hire(Cashier cashier) {
        if (this.cashier == null) {
            setCashier(cashier);
        }
    }

    public void takePendingTransactions() {
        for (Transaction transaction : pendingTransactions) {
            if (transaction.getTo().getName().equals(cashier.getName())) {
                sell(transaction.getProduct(), transaction.getFrom());
            } else if (transaction.getFrom().getName().equals(cashier.getName())) {
                for (int i = 0; i < transaction.getAmount(); i++) {
                    buy(transaction.getProduct());
                }
            }
        }
    }

    private void sell(Product product, Person person) {
        if (person.pay(product.getCost())) {
            setBalance(balance + product.getCost());
            remove(product);
        } else {
            System.out.println("Not enough cash");
        }
    }

    private void buy(Product product) {
        if (product.getCost() > balance) {
            System.out.println("Not enough money to buy \ntype:" + product.getType() + "\nsubtype:" + product.getType());
        } else {
            setBalance(balance - product.getCost());
            add(product);
        }
    }

    private void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }


    private void remove(Product product) {
        switch (product.getClass().getSimpleName().toLowerCase(Locale.ROOT)) {
            case "fruit":
                fruits.remove((Fruit) product);
                break;
            case "meat":
                meats.remove((Meat) product);
                break;
        }
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

    private List<Fruit> getFruits() {
        return fruits;
    }
}