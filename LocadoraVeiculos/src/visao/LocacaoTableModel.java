/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import modelo.*;
import java.util.ArrayList;
import java.util.Locale;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;

public class LocacaoTableModel extends AbstractTableModel {

    private ArrayList<Veiculo> veiculos;

    private String[] colunas = {"Nome do cliente", "Placa", "Marca", "Modelo", "Ano", "Data locacao", "Preco diaria", "Dias locados", "Valor locacao"};

    private NumberFormat formatoMonetario = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    public LocacaoTableModel(ArrayList<Veiculo> veiculos) {
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

        Locacao locacao = veiculo.getLocacao();

        if (locacao == null) {
            return null;
        }

        switch (coluna) {

            case 0:
                return locacao.getCliente().getNome() + " " + locacao.getCliente().getSobrenome();

            case 1:
                return veiculo.getPlaca();

            case 2:
                return veiculo.getMarca();

            case 3:
                return VeiculoTableModel.getModeloVeiculo(veiculo);

            case 4:
                return String.format("%04d", veiculo.getAno());

            case 5:
                return formatoData.format(
                    locacao.getData().getTime()
                );

            case 6:
                return formatoMonetario.format(
                    veiculo.getValorDiariaLocacao()
                );

            case 7:
                return locacao.getDias();

            case 8:
                return formatoMonetario.format(
                    locacao.getValor()
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
