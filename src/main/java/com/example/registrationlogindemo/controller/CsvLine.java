package com.example.registrationlogindemo.controller;

// la classe qui represente chaque ligne dans le ficher csv 
public class CsvLine {
    private Long id;
    private String t1;
    private String t2;

    // Constructeur
    public CsvLine(Long id, String t1, String t2) {
        this.id = id;
        this.t1 = t1;
        this.t2 = t2;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }
}
