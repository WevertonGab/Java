/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import modelo.*;
import java.util.ArrayList;
import java.util.Locale;
import java.text.NumberFormat;
import javax.swing.table.AbstractTableModel;

public class VeiculoTableModel extends AbstractTableModel {

    private ArrayList<Veiculo> veiculos;

    private String[] colunas = {
        "Placa", "Marca", "Modelo", "Ano", "Preco da diaria"
    };

    private NumberFormat formatoMonetario = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public VeiculoTableModel(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    @Override
    public int getRowCount() {
        return veiculos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {

        Veiculo veiculo = veiculos.get(linha);

        switch (coluna) {

            case 0:
                return veiculo.getPlaca();

            case 1:
                return veiculo.getMarca();

            case 2:
                return getModeloVeiculo(veiculo);

            case 3:
                return String.format("%04d", veiculo.getAno());

            case 4:
                return formatoMonetario.format(veiculo.getValorDiariaLocacao());

            default:
                return null;
        }
    }

    public Veiculo getVeiculo(int linha) {
        return veiculos.get(linha);
    }

    public void atualizarTabela(ArrayList<Veiculo> veiculos) {

        this.veiculos = veiculos;
        fireTableDataChanged();
    }

    public static String getModeloVeiculo(Veiculo veiculo) {

        if (veiculo instanceof Automovel) {
            return ((Automovel) veiculo).getModelo().toString();
        }

        if (veiculo instanceof Motocicleta) {
            return ((Motocicleta) veiculo).getModelo().toString();
        }

        if (veiculo instanceof Van) {
            return ((Van) veiculo).getModelo().toString();
        }

        return "";
    }
}
