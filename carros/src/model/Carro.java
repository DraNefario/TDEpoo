package model;

public class Carro {
    private int id;
    private String modelo;
    private String marca;
    private int ano;
    private String placa;

    public Carro(int id, String modelo, String marca, int ano, String placa) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.placa = placa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
