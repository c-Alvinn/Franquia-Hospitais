package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.ConnectionFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;
import view.MenuCadastrar;

public class PessoaDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    public Pessoa autenticacaoUsuario(String login, String senha) {

        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "select * from pessoa where login = ? and senha = ? ";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, login);
            pstm.setString(2, senha);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Logado com sucesso!");
                List<Pessoa> pessoas = pesquisarPessoa();

                for (Pessoa p : pessoas) {
                    if (p.getLogin().equals(login) && p.getSenha().equals(senha)) {
                        return p;
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuario ou Senha inválido.");
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "autenticacao de usuario" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void cadastrarPessoa(Pessoa pessoa) {
        String sql = "insert into pessoa (nome, endereco, cpf, telefone, login, senha, tipoUsuario, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, pessoa.getNome());
            pstm.setString(2, pessoa.getEndereco());
            pstm.setString(3, pessoa.getCpf());
            pstm.setString(4, pessoa.getTelefone());
            pstm.setString(5, pessoa.getLogin());
            pstm.setString(6, pessoa.getSenha());
            pstm.setInt(7, pessoa.getTipoUsuario());
            java.sql.Date dataSQL = new java.sql.Date(pessoa.getDataCriacao().getTime());
            pstm.setDate(8, dataSQL);
            pstm.setDate(9, null);

            pstm.execute();
            pstm.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "PessoaDAO - cadastrarPessoa\n" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //busca no banco de dados todos os usuarios e retorna em uma lista
    public List<Pessoa> pesquisarPessoa() {
        String sql = "select * from pessoa";

        List<Pessoa> lista = new ArrayList<>();

        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "pesquisar usuario (fechamento dos parametros)" + e.getMessage());
            return null;
        }

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("cpf"), rs.getString("telefone"),
                        rs.getString("login"), rs.getString("senha"),
                        rs.getInt("tipoUsuario"), rs.getDate("dataCriacao"),
                        rs.getDate("dataModificacao"));

                pessoa.setId(rs.getInt("idPessoa"));

                lista.add(pessoa);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "PessoaDAO - pesquisaPessoa" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return lista;
    }

    public Pessoa getPessoaTipo(int tipo) {

        //função recebe uma lista de pessoas que pode ser a lista que vem da função de cima
        // recebe o tipo de usuario e em seguida cria uma nova lista separando por tipo de usuário
        //depois mostra um input com a lista de usuarios do tipo que foi recebido e o usuario
        //digita qual o id do usuario que ele quer selecionar e ent será retornado para 
        // a variavel ou função que chamou esta
        List<Pessoa> pessoas = pesquisarPessoa();
        String x = "";
        boolean y = true;
        Pessoa pessoa = null;
        int id = 0;

        String titulo = "Lista de Pessoas\nid   |   Nome\n";
        String lista = "";
        List<Pessoa> l2 = new ArrayList<>();

        for (Pessoa p : pessoas) {
            if (p.getTipoUsuario() == tipo) {
                lista += Integer.toString(p.getId());
                lista += "   |   ";
                lista += p.getNome();
                lista += "\n";
                l2.add(p);
            }

        }

        //caso nao exista nenhum usuario do tipo informado é retornado null
        if (l2.size() == 0) {
            return null;
        }

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(titulo + lista + "\nInforme o id da pessoa que deseja selecionar:");
                }
                id = Integer.parseInt(x);
                for (Pessoa p : l2) {
                    if (p.getId() == id) {
                        y = false;
                        pessoa = p;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "PessoaDAO - getPessoa\n" + e.getMessage());
            }
        } while (y);

        return pessoa;
    }

    public Pessoa getPessoaId(int id) {

        List<Pessoa> lista = pesquisarPessoa();

        for (Pessoa p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public void alterarPessoa() {
        List<Pessoa> pessoas = pesquisarPessoa();

        String texto = "Lista de Pessoas\nId  |   Nome     |   CPF\n";
        for (Pessoa p : pessoas) {
            texto += Integer.toString(p.getId());
            texto += "   |   ";
            texto += p.getNome();
            texto += "   |   ";
            texto += p.getCpf();
            texto += "\n";
        }

        String x;
        boolean y = true;
        Pessoa pessoa = null;
        int id;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da Pessoa que deseja selecionar:");
                }
                id = Integer.parseInt(x);
                for (Pessoa p : pessoas) {
                    if (p.getId() == id) {
                        y = false;
                        pessoa = p;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Pessoa try 1\n" + e.getMessage());
            }
        } while (y);

        alterarPessoaZ(pessoa);

    }

    public void alterarPessoaMenosTipo(int tipo) {
        List<Pessoa> pessoas = pesquisarPessoa();
        List<Pessoa> pessoas2 = new ArrayList<>();

        String texto = "Lista de Pessoas\nId  |   Nome     |   CPF\n";
        for (Pessoa p : pessoas) {
            if (p.getTipoUsuario() <= tipo) {
                texto += Integer.toString(p.getId());
                texto += "   |   ";
                texto += p.getNome();
                texto += "   |   ";
                texto += p.getCpf();
                texto += "\n";
                pessoas2.add(p);
            }

        }

        String x;
        boolean y = true;
        Pessoa pessoa = null;
        int id;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da Pessoa que deseja selecionar:");
                }
                id = Integer.parseInt(x);
                for (Pessoa p : pessoas2) {
                    if (p.getId() == id) {
                        y = false;
                        pessoa = p;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Pessoa try 1\n" + e.getMessage());
            }
        } while (y);

        alterarPessoaZAdmin(pessoa);
        return;

    }

    public void alterarPessoaZ(Pessoa pessoa) {

        String idString = Integer.toString(pessoa.getId());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        int opc;
        String[] options = {"Nome", "Endereço", "CPF", "Telefone", "Login", "Senha", "Voltar Menu"};

        String dados = pessoa.getNome() + "\n"
                + "Endereço = " + pessoa.getEndereco() + "\n"
                + "CPF = " + pessoa.getCpf() + "\n"
                + "Telefone = " + pessoa.getTelefone() + "\n"
                + "Login = " + pessoa.getLogin() + "\n"
                + "Senha = " + pessoa.getSenha() + "\n";
        do {
            opc = JOptionPane.showOptionDialog(null, "O que deseja alterar?\n" + dados, "Alterar Pessoa",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    String nome = "";
                    while (nome.equals("")) {
                        nome = JOptionPane.showInputDialog("Informe o novo nome da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set nome = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, nome);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Nome\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 6;
                    return;
                }

                case 1 -> {
                    String endereco = "";
                    while (endereco.equals("")) {
                        endereco = JOptionPane.showInputDialog("Informe o novo endereço da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set endereco = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, endereco);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Endereço\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 6;
                    return;
                }

                case 2 -> {
                    String cpf = "";
                    while (cpf.equals("")) {
                        cpf = JOptionPane.showInputDialog("Informe o novo CPF da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set cpf = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, cpf);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar CPF\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 6;
                    return;
                }

                case 3 -> {
                    String telefone = "";
                    while (telefone.equals("")) {
                        telefone = JOptionPane.showInputDialog("Informe o novo Telefone da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set telefone = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, telefone);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Telefone\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 6;
                    return;
                }

                case 4 -> {
                    String login = "";
                    while (login.equals("")) {
                        login = JOptionPane.showInputDialog("Informe o novo Login da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set login = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, login);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Login\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 6;
                    return;
                }

                case 5 -> {
                    String senha = "";
                    while (senha.equals("")) {
                        senha = JOptionPane.showInputDialog("Informe o novo Senha da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set senha = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, senha);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Senha\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 6;
                    return;
                }

                default -> {
                    opc = 6;
                    return;
                }
            }
        } while (opc != 6);
        return;
    }

    public void alterarTipoUsuario(Pessoa pessoa, int tipo) {
        pessoa.setTipoUsuario(tipo);

        String idString = Integer.toString(pessoa.getId());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

//        String x = Integer.toString(tipo);
        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "update pessoa set tipoUsuario = ?, dataModificacao = ? where idPessoa = " + idString;

            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, tipo);
            pstm.setDate(2, dataSQL);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Tipo Usuario\n" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
    }

    public void alterarPessoaZAdmin(Pessoa pessoa) {

        String idString = Integer.toString(pessoa.getId());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        int opc;
        String[] options = {"Nome", "Endereço", "CPF", "Telefone", "Login", "Senha", "Tipo de Usuário", "Voltar Menu"};

        String dados = pessoa.getNome() + "\n"
                + "Endereço = " + pessoa.getEndereco() + "\n"
                + "CPF = " + pessoa.getCpf() + "\n"
                + "Telefone = " + pessoa.getTelefone() + "\n"
                + "Login = " + pessoa.getLogin() + "\n"
                + "Senha = " + pessoa.getSenha() + "\n"
                + "Tipo de Usuário = " + pessoa.getTipoUsuario();
        
        do {
            opc = JOptionPane.showOptionDialog(null, "O que deseja alterar?\n" + dados, "Alterar Pessoa",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    String nome = "";
                    while (nome.equals("")) {
                        nome = JOptionPane.showInputDialog("Informe o novo nome da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set nome = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, nome);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Nome\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 7;
                    break;
                }

                case 1 -> {
                    String endereco = "";
                    while (endereco.equals("")) {
                        endereco = JOptionPane.showInputDialog("Informe o novo endereço da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set endereco = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, endereco);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Endereço\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 7;
                    break;
                }

                case 2 -> {
                    String cpf = "";
                    while (cpf.equals("")) {
                        cpf = JOptionPane.showInputDialog("Informe o novo CPF da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set cpf = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, cpf);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar CPF\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 7;
                    break;
                }

                case 3 -> {
                    String telefone = "";
                    while (telefone.equals("")) {
                        telefone = JOptionPane.showInputDialog("Informe o novo Telefone da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set telefone = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, telefone);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Telefone\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 7;
                    break;
                }

                case 4 -> {
                    String login = "";
                    while (login.equals("")) {
                        login = JOptionPane.showInputDialog("Informe o novo Login da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set login = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, login);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Login\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 7;
                    break;
                }

                case 5 -> {
                    String senha = "";
                    while (senha.equals("")) {
                        senha = JOptionPane.showInputDialog("Informe o novo Senha da pessoa:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update pessoa set senha = ?, dataModificacao = ? where idPessoa = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, senha);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "PessoaDAO - Alterar Senha\n" + e.getMessage());
                        break;
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pstm != null) {
                                pstm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    opc = 7;
                    break;
                }

                case 6 -> {
                    int oX;
                    String[] optX = {"Paciente", "Medico", "Administrativo", "Dono de Unidade de Franquia", "Dono de Franquia"};
                    do {
                        oX = JOptionPane.showOptionDialog(null, "Informe qual o tipo do usuário criado", "Menu Usuário",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, optX, optX[0]);
                        switch (oX) {
                            case 0 -> {
                                alterarTipoUsuario(pessoa, oX + 1);
                                oX = 4;
                                break;
                            }
                            case 1 -> {
                                alterarTipoUsuario(pessoa, oX + 1);
                                oX = 4;
                                MenuCadastrar menuCadastrar = new MenuCadastrar();
                                menuCadastrar.cadastrarMedicoP(pessoa);
                                break;
                            }
                            case 2 -> {
                                alterarTipoUsuario(pessoa, oX + 1);
                                oX = 4;
                                break;
                            }
                            case 3 -> {
                                alterarTipoUsuario(pessoa, oX + 1);
                                oX = 4;
                                break;
                            }
                            case 4 -> {
                                alterarTipoUsuario(pessoa, oX + 1);
                                oX = 4;
                                break;
                            }
                            default -> {
                                oX = 4;
                                break;
                            }

                        }
                    } while (oX != 4);
                    opc = 7;
                    break;
                }
                default -> {
                    opc = 7;
                    return;
                }
            }
        } while (opc != 7);
        return;
    }
}
