/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;

import controle.Locadora;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private Locadora locadora;

    public TelaPrincipal(Locadora locadora) {

        this.locadora = locadora;

        setTitle("Locadora de Veiculos");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel titulo = new JLabel(
                "Sistema Locadora", SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnClientes = new JButton("Clientes");
        JButton btnVeiculos = new JButton("Cadastrar Veiculos");
        JButton btnLocacao = new JButton("Locar Veiculos");
        JButton btnDevolucao = new JButton("Devolver Veiculos");
        JButton btnVenda = new JButton("Vender Veiculos");
        JButton btnSair = new JButton("Sair");

        JPanel painel = new JPanel(new GridLayout(7, 1, 10, 10));

        painel.setBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        );

        painel.add(titulo);
        painel.add(btnClientes);
        painel.add(btnVeiculos);
        painel.add(btnLocacao);
        painel.add(btnDevolucao);
        painel.add(btnVenda);
        painel.add(btnSair);

        add(painel);

        // Abrir tela de clientes

        btnClientes.addActionListener(e -> {

            TelaClientes tela = new TelaClientes(this, this.locadora);

            tela.setVisible(true);
        });

        // Telas ainda nao implementadas

        btnVeiculos.setEnabled(false);
        btnLocacao.setEnabled(false);
        btnDevolucao.setEnabled(false);
        btnVenda.setEnabled(false);

        // Encerrar sistema

        btnSair.addActionListener(e -> System.exit(0));
    }
}
