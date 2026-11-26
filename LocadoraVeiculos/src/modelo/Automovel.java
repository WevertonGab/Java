/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Automovel extends Veiculo {
    private ModeloAutomovel modelo;

    public Automovel(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloAutomovel modelo) {

        super(marca, estado, locacao, categoria,valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloAutomovel getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {

        switch (getCategoria()) {
            case POPULAR:
                return 100.00;

            case INTERMEDIARIO:
                return 300.00;

            case LUXO:
                return 450.00;

            default:
                throw new IllegalStateException("Categoria invalida.");
        }
    }
}
