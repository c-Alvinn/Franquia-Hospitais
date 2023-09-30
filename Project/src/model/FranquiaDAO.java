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

public class FranquiaDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    PessoaDAO pessoaDAO = new PessoaDAO();

    public void cadastrarFranquia(Franquia franquia) {
        String sql = "insert into franquia (nome, cnpj, cidade, endereco, idResponsavel, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, franquia.getNome());
            pstm.setString(2, franquia.getCnpj());
            pstm.setString(3, franquia.getCidade());
            pstm.setString(4, franquia.getEndereco());
            pstm.setInt(5, franquia.getResponsavel().getId());
            java.sql.Date dataSQL = new java.sql.Date(franquia.getDataCriacao().getTime());
            pstm.setDate(6, dataSQL);
            pstm.setDate(7, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FranquiaDAO - Cadastrar\n" + e.getMessage());
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

    public int idFranquia(){
        
        List<Franquia> franquias = pesquisarFranquia();
        
        int i = 0;
        for(Franquia f : franquias){
            i = f.getId();
        }

        return i;
    }
    
    public List<Franquia> pesquisarFranquia() {
        List<Franquia> franquias = new ArrayList<>();

        String sql = "select * from franquia";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {
                int i = rs.getInt("idResponsavel");

                Pessoa pessoa = pessoaDAO.getPessoaId(i);

                Franquia franquia = new Franquia(rs.getString("nome"), rs.getString("cnpj"),
                        rs.getString("cidade"), rs.getString("endereco"),
                        pessoa, rs.getDate("dataCriacao"),
                        rs.getDate("dataModificacao"));
                franquia.setId(rs.getInt("idFranquia"));

                franquias.add(franquia);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FranquiaDAO - pesquisar" + e.getMessage());
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

        return franquias;
    }

    public Franquia getFranquiaId(int id) {

        List<Franquia> lista = pesquisarFranquia();

        for (Franquia f : lista) {
            if (f.getId() == id) {
                return f;
            }
        }

        return null;
    }

    public Franquia getFranquiaUserId(int id) {

        List<Franquia> lista = pesquisarFranquia();

        for (Franquia f : lista) {
            if (f.getResponsavel().getId() == id) {
                return f;
            }
        }

        return null;
    }

    public void alterarFranquia(Pessoa pessoa) {

        Franquia franquia = getFranquiaZ(pessoa);

        String idString = Integer.toString(franquia.getId());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        int opc;
        String[] options = {"Nome", "Cidade", "Endereço", "Voltar Menu"};
        do {
            opc = JOptionPane.showOptionDialog(null, "O que deseja alterar da franquia", "Alterar Franquia",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    String nome = "";
                    while (nome.equals("")) {
                        nome = JOptionPane.showInputDialog("Informe o novo nome da Franquia:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update franquia set nome = ?, dataModificacao = ? where idFranquia = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, nome);
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "FranquiaDAO - Alterar Nome\n" + e.getMessage());
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
                    break;
                }

                case 1 -> {
                    String cidade = "";
                    while (cidade.equals("")) {
                        cidade = JOptionPane.showInputDialog("Informe a nova cidade da Franquia:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update franquia set cidade = ?, dataModificacao = ? where idFranquia = " + idString;
                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, cidade);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "FranquiaDAO - Alterar Cidade\n" + e.getMessage());
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
                    break;
                }

                case 2 -> {
                    String endereco = "";
                    while (endereco.equals("")) {
                        endereco = JOptionPane.showInputDialog("Informe o novo endereco da Franquia:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update franquia set endereco = ?, dataModificacao = ? where idFranquia = " + idString;
                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, endereco);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "FranquiaDAO - Alterar Endereço\n" + e.getMessage());
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
                    break;
                }
                default -> {
                    return;
                }
            }
        } while (opc != 3);

    }

    public Franquia getFranquiaZ(Pessoa pessoa) {
        List<Franquia> franquias = pesquisarFranquia();
        List<Franquia> franquias2 = new ArrayList<>();

        int id = pessoa.getId();

        String texto = "Lista de franquias\nId  |   Nome\n";
        for (Franquia f : franquias) {
            if (f.getResponsavel().getId() == id) {
                texto += Integer.toString(f.getId());
                texto += "   |   ";
                texto += f.getNome();
                texto += "\n";
                franquias2.add(f);
            }
        }

        String x;
        boolean y = true;
        Franquia franquia = null;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da Franquia que deseja selecionar:");
                }
                id = Integer.parseInt(x);
                for (Franquia f : franquias2) {
                    if (f.getId() == id) {
                        y = false;
                        franquia = f;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "FranquiaDAO - Get Franquia Z\n" + e.getMessage());
            }
        } while (y);

        return franquia;
    }

    public Franquia getFranquiaZAdmin() {
        List<Franquia> franquias = pesquisarFranquia();

        String texto = "Lista de franquias\nId  |   Nome\n";
        for (Franquia f : franquias) {
            texto += Integer.toString(f.getId());
            texto += "   |   ";
            texto += f.getNome();
            texto += "\n";

        }

        String x;
        boolean y = true;
        Franquia franquia = null;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da Franquia que deseja selecionar:");
                }
                int id = Integer.parseInt(x);
                for (Franquia f : franquias) {
                    if (f.getId() == id) {
                        y = false;
                        franquia = f;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "FranquiaDAO - Get Franquia Z Admin\n" + e.getMessage());
            }
        } while (y);

        return franquia;
    }
}
