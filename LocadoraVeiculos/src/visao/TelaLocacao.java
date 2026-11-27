/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import controle.Locadora;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaLocacao extends JDialog {

    private Locadora locadora;

    private ClienteTableModel modeloClientes;
    private VeiculoTableModel modeloVeiculos;

    private JTable tabelaClientes;
    private JTable tabelaVeiculos;

    private JTextField txtPesquisa;
    private JTextField txtDias;
    private JTextField txtData;

    private JComboBox<String> cmbTipo;
    private JComboBox<Object> cmbMarca;
    private JComboBox<Object> cmbCategoria;

    public TelaLocacao(JFrame telaPrincipal, Locadora locadora) {

        super(telaPrincipal, "Locacao de Veiculos", true);

        this.locadora = locadora;

        setSize(950, 650);
        setLocationRelativeTo(telaPrincipal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Clientes

        txtPesquisa = new JTextField(25);

        JButton btnBuscar = new JButton("Buscar");

        JPanel painelPesquisa = new JPanel();

        painelPesquisa.add(new JLabel("Nome, sobrenome ou CPF:"));
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnBuscar);

        modeloClientes = new ClienteTableModel(locadora.getClientes());

        tabelaClientes = new JTable(modeloClientes);

        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel painelClientes = new JPanel(new BorderLayout());

        painelClientes.setBorder(BorderFactory.createTitledBorder("Selecionar cliente"));

        painelClientes.add(painelPesquisa, BorderLayout.NORTH);

        painelClientes.add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

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

        //Veiculos

        modeloVeiculos = new VeiculoTableModel(locadora.filtrarVeiculos(Estado.DISPONIVEL, null, null, null));

        tabelaVeiculos = new JTable(modeloVeiculos);

        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel painelVeiculos = new JPanel(new BorderLayout());

        painelVeiculos.setBorder(BorderFactory.createTitledBorder("Veiculos disponiveis"));

        painelVeiculos.add(painelFiltros, BorderLayout.NORTH);

        painelVeiculos.add(new JScrollPane(tabelaVeiculos), BorderLayout.CENTER);

        //Painel Central

        JPanel painelCentral = new JPanel(new GridLayout(2, 1, 10, 10));

        painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        painelCentral.add(painelClientes);
        painelCentral.add(painelVeiculos);

        add(painelCentral, BorderLayout.CENTER);

        //Dados da locacao

        txtDias = new JTextField(5);

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

        txtData = new JTextField(formatoData.format(Calendar.getInstance().getTime()), 10);

        JButton btnLocar = new JButton("Locar");

        JPanel painelLocacao = new JPanel();

        painelLocacao.add(new JLabel("Data (dd/MM/yyyy):"));
        painelLocacao.add(txtData);

        painelLocacao.add(new JLabel("Quantidade de dias:"));
        painelLocacao.add(txtDias);

        painelLocacao.add(btnLocar);

        add(painelLocacao, BorderLayout.SOUTH);

        //Eventos

        btnBuscar.addActionListener(e -> pesquisarClientes());

        txtPesquisa.addActionListener(e -> pesquisarClientes());

        btnFiltrar.addActionListener(e -> filtrarVeiculos());

        btnLocar.addActionListener(e -> locar());
    }

    // Pesquisar clientes

    private void pesquisarClientes() {

        tabelaClientes.clearSelection();

        modeloClientes.atualizarTabela(locadora.pesquisarClientes(txtPesquisa.getText()));
    }

    // Filtrar veiculos

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

        tabelaVeiculos.clearSelection();

        modeloVeiculos.atualizarTabela(locadora.filtrarVeiculos(Estado.DISPONIVEL, tipo, marca, categoria));
    }

    // Realizar locacao

    private void locar() {

        try {

            int linhaCliente = tabelaClientes.getSelectedRow();

            int linhaVeiculo = tabelaVeiculos.getSelectedRow();

            if (linhaCliente == -1) {
                throw new IllegalArgumentException("Selecione um cliente na tabela.");
            }

            if (linhaVeiculo == -1) {
                throw new IllegalArgumentException("Selecione um veiculo na tabela.");
            }

            Cliente cliente = modeloClientes.getCliente(linhaCliente);

            Veiculo veiculo = modeloVeiculos.getVeiculo(linhaVeiculo);

            // Validar quantidade de dias

            int dias = Integer.parseInt(txtDias.getText().trim());

            if (dias <= 0) {
                throw new IllegalArgumentException("A quantidade de dias deve ser maior que zero.");
            }

            // Validar data

            String dataTexto = txtData.getText().trim();

            if (!dataTexto.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
                throw new IllegalArgumentException("Informe a data no formato dd/MM/yyyy.");
            }

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            formato.setLenient(false);

            Calendar data = Calendar.getInstance();

            data.setTime(formato.parse(dataTexto));

            // Realizar locacao

            locadora.locarVeiculo(veiculo, dias, data, cliente);

            // Formatar valor da locacao

            NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            String valor = formatoMoeda.format(veiculo.getLocacao().getValor());

            // Atualizar tabela

            filtrarVeiculos();

            txtDias.setText("");

            JOptionPane.showMessageDialog(this, "Locacao realizada com sucesso.\n" + "Cliente: " + cliente.getNome() + "\n" + "Placa: " + veiculo.getPlaca() + "\n" + "Dias: " + dias + "\n" + "Valor total: " + valor);

        } catch (NumberFormatException e) {
            mostrarErro("Informe uma quantidade de dias valida.");

        } catch (ParseException e) {
            mostrarErro("Informe uma data valida.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarErro(e.getMessage());
        }
    }

    // Exibir mensagem de erro

    private void mostrarErro(String mensagem) {

        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
