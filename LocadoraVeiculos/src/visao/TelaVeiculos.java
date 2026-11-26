/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import controle.Locadora;
import modelo.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class TelaVeiculos extends JDialog {

    private Locadora locadora;

    private JComboBox<String> cmbTipo;
    private JComboBox<Marca> cmbMarca;
    private JComboBox<Estado> cmbEstado;
    private JComboBox<Categoria> cmbCategoria;
    private JComboBox<Enum<?>> cmbModelo;

    private JFormattedTextField txtValorCompra;
    private JFormattedTextField txtPlaca;
    private JTextField txtAno;

    public TelaVeiculos(JFrame telaPrincipal, Locadora locadora) {

        super(telaPrincipal, "Cadastro de Veiculos", true);

        this.locadora = locadora;

        setSize(500, 450);
        setLocationRelativeTo(telaPrincipal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ComboBoxes

        cmbTipo = new JComboBox<>(new String[]{"Automovel", "Motocicleta", "Van"});

        cmbMarca = new JComboBox<>(Marca.values());

        cmbEstado = new JComboBox<>(new Estado[]{Estado.NOVO, Estado.DISPONIVEL});

        cmbEstado.setSelectedItem(Estado.DISPONIVEL);

        cmbCategoria = new JComboBox<>(Categoria.values());

        cmbModelo = new JComboBox<>();

        atualizarModelos();

        // Campos de preenchimento

        txtValorCompra = criarCampoMonetario();
        txtPlaca = criarCampoPlaca();
        txtAno = new JTextField();

        // Painel de campos

        JPanel painelCampos = new JPanel(new GridLayout(8, 2, 10, 10));

        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        painelCampos.add(new JLabel("Tipo:"));
        painelCampos.add(cmbTipo);

        painelCampos.add(new JLabel("Marca:"));
        painelCampos.add(cmbMarca);

        painelCampos.add(new JLabel("Estado:"));
        painelCampos.add(cmbEstado);

        painelCampos.add(new JLabel("Categoria:"));
        painelCampos.add(cmbCategoria);

        painelCampos.add(new JLabel("Modelo:"));
        painelCampos.add(cmbModelo);

        painelCampos.add(new JLabel("Valor de compra (R$):"));
        painelCampos.add(txtValorCompra);

        painelCampos.add(new JLabel("Placa:"));
        painelCampos.add(txtPlaca);

        painelCampos.add(new JLabel("Ano:"));
        painelCampos.add(txtAno);

        add(painelCampos, BorderLayout.CENTER);

        // Botoes

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos

        cmbTipo.addActionListener(e -> atualizarModelos());

        btnCadastrar.addActionListener(e -> cadastrar());

        btnLimpar.addActionListener(e -> limpar());
    }

    // Atualizar modelos conforme o tipo

    private void atualizarModelos() {

        cmbModelo.removeAllItems();

        String tipo = (String) cmbTipo.getSelectedItem();

        switch (tipo) {

            case "Automovel":
                for (ModeloAutomovel modelo : ModeloAutomovel.values()) {
                    cmbModelo.addItem(modelo);
                }
            break;

            case "Motocicleta":
                for (ModeloMotocicleta modelo : ModeloMotocicleta.values()) {
                    cmbModelo.addItem(modelo);
                }
            break;

            case "Van":
                for (ModeloVan modelo : ModeloVan.values()) {
                    cmbModelo.addItem(modelo);
                }
            break;
        }
    }

    // Mascara para valor monetario

    private JFormattedTextField criarCampoMonetario() {

        NumberFormat formato = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);

        NumberFormatter formatter = new NumberFormatter(formato);

        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(true);

        JFormattedTextField campo = new JFormattedTextField(formatter);

        campo.setFocusLostBehavior(JFormattedTextField.PERSIST);
        campo.setValue(0.00);

        return campo;
    }

    // Mascara para placa

    private JFormattedTextField criarCampoPlaca() {

        try {

            MaskFormatter mascara = new MaskFormatter("UUU-####");

            mascara.setPlaceholderCharacter('_');

            JFormattedTextField campo = new JFormattedTextField(mascara);

            campo.setFocusLostBehavior(JFormattedTextField.PERSIST);

            return campo;

        } catch (ParseException e) {

            throw new IllegalStateException("Erro ao criar mascara da placa.", e);
        }
    }

    // Cadastrar veiculo

    private void cadastrar() {

        try {

            String tipo = (String) cmbTipo.getSelectedItem();

            Marca marca = (Marca) cmbMarca.getSelectedItem();

            Estado estado = (Estado) cmbEstado.getSelectedItem();

            Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();

            String placa = txtPlaca.getText().trim();

            // Validar valor de compra

            txtValorCompra.commitEdit();

            Number numero = (Number) txtValorCompra.getValue();

            if (numero == null) {
                throw new IllegalArgumentException("Informe o valor de compra.");
            }

            double valorCompra = numero.doubleValue();

            if (!Double.isFinite(valorCompra) || valorCompra <= 0) {
                throw new IllegalArgumentException("Valor de compra deve ser maior que zero.");
            }

            // Validar placa

            if (!placa.matches("[A-Z]{3}-[0-9]{4}")) {
                throw new IllegalArgumentException("Informe uma placa no formato XXX-0000.");
            }

            // Validar ano

            if (!txtAno.getText().trim().matches("[0-9]{4}")) {
                throw new IllegalArgumentException("Informe um ano com quatro digitos.");
            }

            int ano = Integer.parseInt(txtAno.getText().trim());

            // Instanciar veiculo

            Veiculo veiculo;

            switch (tipo) {

                case "Automovel":
                    veiculo = new Automovel(marca, estado, null, categoria, valorCompra, placa, ano, (ModeloAutomovel) cmbModelo.getSelectedItem());
                break;

                case "Motocicleta":
                    veiculo = new Motocicleta(marca, estado, null, categoria, valorCompra, placa, ano, (ModeloMotocicleta) cmbModelo.getSelectedItem());
                break;

                case "Van":
                    veiculo = new Van(marca, estado, null, categoria, valorCompra, placa, ano, (ModeloVan) cmbModelo.getSelectedItem());
                break;

                default:
                    throw new IllegalArgumentException("Tipo de veiculo invalido.");
            }

            locadora.cadastrarVeiculo(veiculo);

            JOptionPane.showMessageDialog(this, "Veiculo cadastrado com sucesso.\n" + "Total de veiculos cadastrados: " + locadora.getVeiculos().size());

            limpar();

        } catch (ParseException e) {
            mostrarErro("Informe um valor de compra valido.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarErro(e.getMessage());
        }
    }

    // Limpar campos

    private void limpar() {

        cmbTipo.setSelectedIndex(0);
        cmbMarca.setSelectedIndex(0);
        cmbEstado.setSelectedItem(Estado.DISPONIVEL);
        cmbCategoria.setSelectedIndex(0);

        atualizarModelos();

        txtValorCompra.setValue(0.00);
        txtPlaca.setValue(null);
        txtAno.setText("");
    }

    // Exibir mensagem de erro

    private void mostrarErro(String mensagem) {

        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
