/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import modelo.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Locadora {
    private ArrayList<Cliente> clientes;
    private ArrayList<Veiculo> veiculos;

    public Locadora() {
        clientes = new ArrayList<>();
        veiculos = new ArrayList<>();
    }

    //Clientes

    public void cadastrarCliente(Cliente cliente) {
        validarCliente(cliente, null);
        clientes.add(cliente);
    }

    public void atualizarCliente(Cliente cliente, String nome, String sobrenome, String rg, String cpf, String endereco) {
        verificarClienteCadastrado(cliente);

        Cliente dados = new Cliente(nome, sobrenome, rg, cpf, endereco);

        validarCliente(dados, cliente);

        cliente.setNome(nome);
        cliente.setSobrenome(sobrenome);
        cliente.setRg(rg);
        cliente.setCpf(cpf);
        cliente.setEndereco(endereco);
    }

    public void excluirCliente(Cliente cliente) {
        verificarClienteCadastrado(cliente);

        if (clientePossuiLocacao(cliente)) {
            throw new IllegalStateException("Cliente possui um veiculo locado.");
        }

        clientes.remove(cliente);
    }

    public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public ArrayList<Cliente> pesquisarClientes(String pesquisa) {
        ArrayList<Cliente> resultado = new ArrayList<>();

        pesquisa = pesquisa.trim().toLowerCase(Locale.ROOT);

        for (Cliente cliente : clientes) {
            String nomeCompleto = cliente.getNome() + " " + cliente.getSobrenome();

            if (nomeCompleto.toLowerCase(Locale.ROOT).contains(pesquisa) || normalizarCpf(cliente.getCpf()).contains(normalizarCpf(pesquisa)) && !normalizarCpf(pesquisa).isEmpty()) {
                resultado.add(cliente);
            }
        }

        return resultado;
    }

    public  boolean clientePossuiLocacao(Cliente cliente) {

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getEstado() == Estado.LOCADO && veiculo.getLocacao().getCliente() == cliente) {
                return true;
            }
        }
        return false;
    }

    //Veiculos

    public void cadastrarVeiculo(Veiculo veiculo) {

        if (veiculo == null || veiculo.getMarca() == null || veiculo.getCategoria() == null || veiculo.getEstado() == null || veiculo.getPlaca() == null || !veiculo.getPlaca().matches("[A-Z]{3}-[0-9]{4}") || veiculo.getAno() < 1000 || veiculo.getAno() > 9999) {
            throw new IllegalArgumentException("Dados do veiculo invalidos.");
        }

        if (veiculo.getEstado() == Estado.LOCADO) {
            throw new IllegalArgumentException("Nao e permitido cadastrar um veiculo locado.");
        }

        for (Veiculo v : veiculos) {
            
            if (v.getPlaca().equals(veiculo.getPlaca())) {
                throw new IllegalArgumentException("Placa ja cadastrada.");
            }
        }

        veiculos.add(veiculo);
    }

    public ArrayList<Veiculo> getVeiculos() {
        return new ArrayList<>(veiculos);
    }

    public ArrayList<Veiculo> filtrarVeiculos(Estado estado, Class<? extends Veiculo> tipo, Marca marca, Categoria categoria) {
        ArrayList<Veiculo> resultado = new ArrayList<>();

        for (Veiculo veiculo : veiculos) {

            if ((estado == null || veiculo.getEstado() == estado) && (tipo == null || tipo.isInstance(veiculo)) && (marca == null || veiculo.getMarca() == marca) && (categoria == null || veiculo.getCategoria() == categoria)) {
                resultado.add(veiculo);
            }
        }
        return resultado;
    }

    //Operacoes

    public void locarVeiculo(Veiculo veiculo, int dias, Calendar data, Cliente cliente) {
        verificarVeiculoCadastrado(veiculo);
        verificarClienteCadastrado(cliente);

        if (clientePossuiLocacao(cliente)) {
            throw new IllegalStateException("Cliente ja possui um veiculo locado.");
        }

        veiculo.locar(dias, data, cliente);
    }

    public void devolverVeiculo(Veiculo veiculo) {
        verificarVeiculoCadastrado(veiculo);

        veiculo.devolver();
    }

    public void venderVeiculo(Veiculo veiculo) {
        verificarVeiculoCadastrado(veiculo);

        veiculo.vender();
    }

    //Validacoes

    private void verificarClienteCadastrado(Cliente cliente) {

        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("Cliente nao cadastrado.");
        }
    }

    private void verificarVeiculoCadastrado(Veiculo veiculo) {

        if (!veiculos.contains(veiculo)) {
            throw new IllegalArgumentException("Veiculo nao cadastrado.");
        }
    }

    private void validarCliente(Cliente cliente, Cliente ignorar) {

        if (cliente == null || campoVazio(cliente.getNome()) || campoVazio(cliente.getSobrenome()) || campoVazio(cliente.getRg()) || campoVazio(cliente.getCpf()) || campoVazio(cliente.getEndereco()) || normalizarCpf(cliente.getCpf()).isEmpty()) {
            throw new IllegalArgumentException("Preencha todos os dados do cliente.");
        }
        
        if (!normalizarCpf(cliente.getCpf()).matches("[0-9]{11}")) {
         throw new IllegalArgumentException("CPF deve possuir 11 digitos.");
        }
        
        if (!cliente.getRg().trim().matches("[0-9]+")) {
            throw new IllegalArgumentException("RG deve possuir apenas numeros.");
        }
        
        for (Cliente c : clientes) {

            if (c != ignorar && normalizarCpf(c.getCpf()) .equals(normalizarCpf(cliente.getCpf()))) {
                throw new IllegalArgumentException("Cpf ja cadastrado.");
            }
        }
    }

    private boolean campoVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private String normalizarCpf(String cpf) {
        return cpf.replaceAll("[.\\s-]", "");
    }
}
