/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Calendar;

public abstract class Veiculo implements VeiculoI {
    private Marca marca;
    private Estado estado;
    private Locacao locacao;
    private Categoria categoria;
    private double valorDeCompra;
    private String placa;
    private int ano;
    
    public Veiculo(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano) {
        if ((estado == Estado.LOCADO) != (locacao != null)) {
            throw new IllegalArgumentException("Estado de locacao incompativeis.");
        }
        
        this.marca = marca;
        this.estado = estado;
        this.locacao = locacao;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
    }
    
    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        if (estado != Estado.DISPONIVEL) {
            throw new IllegalStateException("Veiculo indisponivel para locacao");
        }
        
        if (dias <= 0 || data == null || cliente == null) {
            throw new IllegalArgumentException("Dados da locacao invalidos.");
        }
        
        double valor = dias * getValorDiariaLocacao();
        
        this.locacao = new Locacao(dias, valor, data, cliente);
        this.estado = Estado.LOCADO;
    }
    
    @Override 
    public void vender() {
        if (estado != Estado.DISPONIVEL) {
            throw new IllegalStateException("Veiculo indisponivel para venda.");
        }
        
        this.estado = Estado.VENDIDO;
    }
    
    @Override
    public void devolver() {
        if (estado != Estado.LOCADO) {
            throw new IllegalStateException("O veiculo nao esta locado.");
        }
        
        this.locacao = null;
        this.estado = Estado.DISPONIVEL;
    }
    
    @Override
    public Estado getEstado() {
        return estado;
    }
    
    @Override
    public Marca getMarca() {
        return marca;
    }
    
    @Override
    public Categoria getCategoria() {
        return categoria;
    }
    
    @Override
    public Locacao getLocacao() {
        return locacao;
    }
    
    @Override
    public String getPlaca(){
        return placa;
    }
    
    @Override
    public int getAno() {
        return ano;
    }
    
    @Override
    public double getValorParaVenda() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        
        int idade = Math.max(0, anoAtual - ano);
        
        double valorParaVenda = valorDeCompra - idade * 0.15 * valorDeCompra;
        
        if (valorParaVenda < valorDeCompra * 0.1) {
            valorParaVenda = valorDeCompra * 0.1;
        }
        
        return valorParaVenda;
    }
    
    @Override
    public abstract double getValorDiariaLocacao();
    
}
