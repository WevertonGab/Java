/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Van extends Veiculo {
    private ModeloVan modelo;

    public Van(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloVan modelo) {

        super(marca, estado, locacao, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloVan getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {

        switch (getCategoria()) {
            case POPULAR:
                return 200.00;

            case INTERMEDIARIO:
                return 400.00;

            case LUXO:
                return 600.00;

            default:
                throw new IllegalStateException("Categoria invalida.");
        }
    }
}
