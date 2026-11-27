/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import controle.Locadora;
import modelo.*;
import javax.swing.*;
import java.awt.*;

public class TelaDevolucao extends JDialog {

    private Locadora locadora;

    private LocacaoTableModel modeloTabela;

    private JTable tabela;

    public TelaDevolucao(JFrame telaPrincipal, Locadora locadora) {

        super(telaPrincipal, "Devolucao de Veiculos", true);

        this.locadora = locadora;

        setSize(1100, 450);
        setLocationRelativeTo(telaPrincipal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Titulo

        JLabel titulo = new JLabel("Veiculos Locados", SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        add(titulo, BorderLayout.NORTH);

        // Tabela de veiculos locados

        modeloTabela = new LocacaoTableModel(locadora.filtrarVeiculos(Estado.LOCADO, null, null, null));

        tabela = new JTable(modeloTabela);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botao

        JButton btnDevolver = new JButton("Devolver veiculo");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnDevolver);

        add(painelBotoes, BorderLayout.SOUTH);

        // Evento

        btnDevolver.addActionListener(e -> devolver());
    }

    // Devolver veiculo

    private void devolver() {

        try {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                throw new IllegalArgumentException("Selecione um veiculo na tabela.");
            }

            Veiculo veiculo = modeloTabela.getVeiculo(linha);

            int resposta = JOptionPane.showConfirmDialog(this, "Deseja devolver o veiculo " + veiculo.getPlaca() + "?", "Confirmar devolucao", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {

                locadora.devolverVeiculo(veiculo);

                atualizarTabela();

                JOptionPane.showMessageDialog(this, "Veiculo devolvido com sucesso.");
            }

        } catch (IllegalArgumentException | IllegalStateException e) {

            mostrarErro(e.getMessage());
        }
    }

    // Atualizar tabela

    private void atualizarTabela() {
        tabela.clearSelection();

        modeloTabela.atualizarTabela(locadora.filtrarVeiculos(Estado.LOCADO, null, null, null));
    }

    // Exibir mensagem de erro

    private void mostrarErro(String mensagem) {

JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
