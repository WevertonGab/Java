/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import controle.Locadora;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaVenda extends JDialog {

    private Locadora locadora;

    private VendaTableModel modeloTabela;
    private JTable tabela;

    private JComboBox<String> cmbTipo;
    private JComboBox<Object> cmbMarca;
    private JComboBox<Object> cmbCategoria;

    public TelaVenda(JFrame telaPrincipal, Locadora locadora) {

        super(telaPrincipal, "Venda de Veiculos", true);

        this.locadora = locadora;

        setSize(900, 450);
        setLocationRelativeTo(telaPrincipal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Titulo

        JLabel titulo = new JLabel("Veiculos Disponiveis para Venda", SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        //Filtros

        cmbTipo = new JComboBox<>(new String[]{
            "Todos", "Automovel", "Motocicleta", "Van"
        });

        cmbMarca = new JComboBox<>();

        cmbMarca.addItem("Todas");

        for (Marca marca : Marca.values()) {
            cmbMarca.addItem(marca);
        }

        cmbCategoria = new JComboBox<>();

        cmbCategoria.addItem("Todas");

        for (Categoria categoria : Categoria.values()) {
            cmbCategoria.addItem(categoria);
        }

        JButton btnFiltrar = new JButton("Filtrar");

        JPanel painelFiltros = new JPanel();

        painelFiltros.add(new JLabel("Tipo:"));
        painelFiltros.add(cmbTipo);

        painelFiltros.add(new JLabel("Marca:"));
        painelFiltros.add(cmbMarca);

        painelFiltros.add(new JLabel("Categoria:"));
        painelFiltros.add(cmbCategoria);

        painelFiltros.add(btnFiltrar);

        //Painel Superior

        JPanel painelSuperior = new JPanel(new BorderLayout());

        painelSuperior.add(titulo, BorderLayout.NORTH);
        painelSuperior.add(painelFiltros, BorderLayout.CENTER);

        add(painelSuperior, BorderLayout.NORTH);

        //Tabela

        modeloTabela = new VendaTableModel(locadora.filtrarVeiculos(Estado.DISPONIVEL, null, null, null));

        tabela = new JTable(modeloTabela);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        //Botoes

        JButton btnVender = new JButton("Vender veiculo");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnVender);

        add(painelBotoes, BorderLayout.SOUTH);

        //Eventos

        btnFiltrar.addActionListener(e -> filtrarVeiculos());

        btnVender.addActionListener(e -> vender());
    }

    //Filtrar veiculos

    private void filtrarVeiculos() {

        Class<? extends Veiculo> tipo = null;

        String tipoSelecionado = (String) cmbTipo.getSelectedItem();

        switch (tipoSelecionado) {

            case "Automovel":
                tipo = Automovel.class;
            break;

            case "Motocicleta":
                tipo = Motocicleta.class;
            break;

            case "Van":
                tipo = Van.class;
            break;
        }

        Marca marca = null;

        if (cmbMarca.getSelectedItem() instanceof Marca) {
            marca = (Marca) cmbMarca.getSelectedItem();
        }

        Categoria categoria = null;

        if (cmbCategoria.getSelectedItem() instanceof Categoria) {
            categoria = (Categoria) cmbCategoria.getSelectedItem();
        }

        tabela.clearSelection();

        modeloTabela.atualizarTabela(locadora.filtrarVeiculos(Estado.DISPONIVEL, tipo, marca, categoria));
    }

    //Vender veiculo

    private void vender() {

        try {

            int linha = tabela.getSelectedRow();

            if (linha == -1) {

                throw new IllegalArgumentException("Selecione um veiculo na tabela.");
            }

            Veiculo veiculo = modeloTabela.getVeiculo(linha);

            NumberFormat formatoMonetario = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            String valor = formatoMonetario.format(
                veiculo.getValorParaVenda()
            );

            int resposta = JOptionPane.showConfirmDialog(this, "Deseja vender este veiculo?\n" + "Placa: " + veiculo.getPlaca() + "\n" + "Valor de venda: " + valor, "Confirmar venda", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {

                locadora.venderVeiculo(veiculo);

                filtrarVeiculos();

                JOptionPane.showMessageDialog(this, "Veiculo vendido com sucesso.\n" + "Valor da venda: " + valor);
            }

        } catch (IllegalArgumentException | IllegalStateException e) {

            mostrarErro(e.getMessage());
        }
    }

    //Mensagem de Erro

    private void mostrarErro(String mensagem) {

    JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
