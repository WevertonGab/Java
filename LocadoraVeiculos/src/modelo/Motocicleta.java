/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

public class Motocicleta extends Veiculo {
    private ModeloMotocicleta modelo;

    public Motocicleta(Marca marca, Estado estado,Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloMotocicleta modelo) {

        super(marca, estado, locacao, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloMotocicleta getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {

        switch (getCategoria()) {
            case POPULAR:
                return 70.00;

            case INTERMEDIARIO:
                return 200.00;

            case LUXO:
                return 350.00;

            default:
                throw new IllegalStateException("Categoria invalida.");
        }
    }
}
