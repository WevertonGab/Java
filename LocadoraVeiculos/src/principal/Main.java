/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package principal;

import controle.Locadora;
import visao.TelaPrincipal;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Locadora locadora = new Locadora();

            TelaPrincipal tela = new TelaPrincipal(locadora);

            tela.setVisible(true);
        });
    }
}
