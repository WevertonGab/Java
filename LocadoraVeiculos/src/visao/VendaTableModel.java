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

public class VendaTableModel extends AbstractTableModel {

    private ArrayList<Veiculo> veiculos;

    private String[] colunas = {
        "Placa", "Marca", "Modelo", "Ano", "Preco de venda"
    };

    private NumberFormat formatoMonetario =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public VendaTableModel(ArrayList<Veiculo> veiculos) {
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
                return VeiculoTableModel.getModeloVeiculo(veiculo);

            case 3:
                return String.format("%04d", veiculo.getAno());

            case 4:
                return formatoMonetario.format(
                    veiculo.getValorParaVenda()
            );

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
}
