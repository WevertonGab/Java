/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import modelo.Cliente;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ClienteTableModel extends AbstractTableModel{
    
    private ArrayList<Cliente> clientes;

    private String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereco"};

    public ClienteTableModel(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
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

        Cliente cliente = clientes.get(linha);

        switch (coluna) {
            case 0:
                return cliente.getNome();

            case 1:
                return cliente.getSobrenome();

            case 2:
                return cliente.getRg();

            case 3:
                return cliente.getCpf();

            case 4:
                return cliente.getEndereco();

            default:
                return null;
        }
    }

    public Cliente getCliente(int linha) {
        return clientes.get(linha);
    }

    public void atualizarTabela(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
        fireTableDataChanged();
    }
}
