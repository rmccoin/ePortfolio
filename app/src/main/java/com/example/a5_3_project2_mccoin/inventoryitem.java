package com.example.a5_3_project2_mccoin;

public class inventoryitem {
    private int id;
    private String name;
    private int quantity;

    public inventoryitem(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
