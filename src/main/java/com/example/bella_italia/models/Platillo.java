package com.example.bella_italia.models;

import java.util.List;

public class Platillo {
    private int id;
    private String name;
    private Float price;
    private List<InventarioModel.Ingrediente> ingredientes;
    private int quantity;



    public Platillo(String name, Float price, List<InventarioModel.Ingrediente> ingredientes) {
        this.name = name;
        this.price = price;
        this.ingredientes = ingredientes;
    }

    public void actualizarId(int nuevoId) {
        this.id = nuevoId;
    }

    public int getId() {
        System.out.println(id);
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<InventarioModel.Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<InventarioModel.Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setId(int generatedId) {
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getQuantity() {
        return quantity;
    }
}
