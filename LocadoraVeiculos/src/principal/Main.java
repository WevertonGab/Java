/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package principal;

import modelo.Cliente;
import modelo.Estado;
import modelo.Marca;

public class Main {

    public static void main(String[] args) {
        Cliente cliente = new Cliente("Leo", "Santos", "12345678",  "12345678900", "Rua Exemplo, 100" );
        
        System.out.println(cliente.getNome());
        System.out.println(cliente.getSobrenome());

        Marca marca = Marca.HONDA;
        Estado estado = Estado.DISPONIVEL;

        System.out.println(marca);
        System.out.println(estado);   
    }
}
