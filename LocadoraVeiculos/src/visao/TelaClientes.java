/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import controle.Locadora;
import modelo.Cliente;
import javax.swing.*;
import java.awt.*;

public class TelaClientes extends JDialog {
    
    private Locadora locadora;
    private ClienteTableModel modeloTabela;
    private JTable tabela;

    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JTextField txtRg;
    private JTextField txtCpf;
    private JTextField txtEndereco;

    public TelaClientes(JFrame telaPrincipal, Locadora locadora) {

        super(telaPrincipal, "Gerenciamento de Clientes", true);

        this.locadora = locadora;

        setSize(850, 450);
        setLocationRelativeTo(telaPrincipal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Campos de preenchimento

        txtNome = new JTextField();
        txtSobrenome = new JTextField();
        txtRg = new JTextField();
        txtCpf = new JTextField();
        txtEndereco = new JTextField();

        JPanel painelCampos = new JPanel(new GridLayout(5, 2, 5, 5));

        painelCampos.add(new JLabel("Nome:"));
        painelCampos.add(txtNome);

        painelCampos.add(new JLabel("Sobrenome:"));
        painelCampos.add(txtSobrenome);

        painelCampos.add(new JLabel("RG:"));
        painelCampos.add(txtRg);

        painelCampos.add(new JLabel("CPF:"));
        painelCampos.add(txtCpf);

        painelCampos.add(new JLabel("Endereco:"));
        painelCampos.add(txtEndereco);

        // Botoes

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        // Painel superior

        JPanel painelSuperior = new JPanel(new BorderLayout());

        painelSuperior.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 0, 10)
        );

        painelSuperior.add(painelCampos, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);

        // Tabela de clientes

        modeloTabela = new ClienteTableModel(locadora.getClientes());

        tabela = new JTable(modeloTabela);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Eventos dos botoes

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());

        // Evento de selecao da tabela

        tabela.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                preencherCampos();
            }
        });
    }

    // Cadastrar cliente

    private void cadastrar() {

        try {

            Cliente cliente = new Cliente(txtNome.getText(), txtSobrenome.getText(), txtRg.getText(), txtCpf.getText(), txtEndereco.getText());

            locadora.cadastrarCliente(cliente);

            atualizarTabela();
            limpar();

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso.");

        } catch (IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        }
    }

    // Atualizar cliente

    private void atualizar() {

        try {

            Cliente cliente = getClienteSelecionado();

            locadora.atualizarCliente(cliente, txtNome.getText(), txtSobrenome.getText(), txtRg.getText(), txtCpf.getText(), txtEndereco.getText());

            atualizarTabela();
            limpar();

            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.");

        } catch (IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        }
    }

    // Excluir cliente

    private void excluir() {

        try {

            Cliente cliente = getClienteSelecionado();

            int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este cliente?", "Confirmar exclusao", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {

                locadora.excluirCliente(cliente);

                atualizarTabela();
                limpar();

                JOptionPane.showMessageDialog(this, "Cliente excluido com sucesso.");
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarErro(e.getMessage());
        }
    }

    // Retorna o cliente selecionado

    private Cliente getClienteSelecionado() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um cliente na tabela.");
        }

        return modeloTabela.getCliente(linha);
    }

    // Preenche os campos com os dados selecionados

    private void preencherCampos() {

        int linha = tabela.getSelectedRow();

        if (linha != -1) {

            Cliente cliente = modeloTabela.getCliente(linha);

            txtNome.setText(cliente.getNome());
            txtSobrenome.setText(cliente.getSobrenome());
            txtRg.setText(cliente.getRg());
            txtCpf.setText(cliente.getCpf());
            txtEndereco.setText(cliente.getEndereco());
        }
    }

    // Atualizar os dados da tabela

    private void atualizarTabela() {
        modeloTabela.atualizarTabela(locadora.getClientes());
    }

    // Limpar os campos

    private void limpar() {

        tabela.clearSelection();

        txtNome.setText("");
        txtSobrenome.setText("");
        txtRg.setText("");
        txtCpf.setText("");
        txtEndereco.setText("");

        txtNome.requestFocusInWindow();
    }

    // Exibir mensagem de erro

    private void mostrarErro(String mensagem) {

        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
