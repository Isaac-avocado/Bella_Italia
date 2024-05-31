package com.example.bella_italia.models;

public class Cliente {
    private int id;
    private String name;
    private String email;
    private String address;
    private String city;
    private String zip;

    // Constructor, getters y setters

    // Constructor
    public Cliente(int id, String nombre, String email, String codigopost, String direccion, String city) {
        this.id = id;
        this.name = nombre;
        this.address = direccion;
        this.city = city;
        this.email = email;
        this.zip = codigopost;
    }

    // getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String direccion) {
        this.address = direccion;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String ciudad) {
        this.city = ciudad;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String codigoPostal) {
        this.zip = codigoPostal;
    }
}
