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

public class UnidadeFDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    FranquiaDAO franquiaDAO = new FranquiaDAO();
    PessoaDAO pessoaDAO = new PessoaDAO();

    public void cadastrarUnidadeF(UnidadeF unidadeF) {
        String sql = "insert into unidadeF (idFranquia, cidade, endereco, idResponsavel, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, unidadeF.getFranquia().getId());
            pstm.setString(2, unidadeF.getCidade());
            pstm.setString(3, unidadeF.getEndereco());
            pstm.setInt(4, unidadeF.getResponsavel().getId());
            java.sql.Date dataSQL = new java.sql.Date(unidadeF.getDataCriacao().getTime());
            pstm.setDate(5, dataSQL);
            pstm.setDate(6, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UnidadeFDAO - Cadastrar\n" + e.getMessage());
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

    public List<UnidadeF> pesquisarUnidadeF() {
        String sql = "select * from unidadef";

        List<UnidadeF> lista = new ArrayList<>();

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {
                UnidadeF unidadeF = new UnidadeF(franquiaDAO.getFranquiaId(rs.getInt("idFranquia")), rs.getString("cidade"),
                        rs.getString("endereco"), pessoaDAO.getPessoaId(rs.getInt("idResponsavel")),
                        rs.getDate("dataCriacao"), rs.getDate("dataModificacao"));

                unidadeF.setId(rs.getInt("idUnidadeF"));

                lista.add(unidadeF);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UnidadeF - pesquisaUnidadeF\n" + e.getMessage());
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

    public UnidadeF getUnidadeFId(int id) {

        List<UnidadeF> lista = pesquisarUnidadeF();

        for (UnidadeF u : lista) {
            if (u.getId() == id) {
                return u;
            }
        }

        return null;
    }

    public UnidadeF getUnidadeFUserId(int id) {

        List<UnidadeF> lista = pesquisarUnidadeF();

        for (UnidadeF u : lista) {
            if (u.getResponsavel().getId() == id) {
                return u;
            }
        }

        return null;
    }

    public void alterarUnidadeF(Franquia franquia) {

        UnidadeF unidadeF = getUnidadeFZF(franquia);

        String idString = Integer.toString(unidadeF.getId());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        int opc;
        String[] options = {"Cidade", "Endereço", "Responsável", "Voltar Menu"};

        String dados = "Unidade = " + idString + "\n"
                + "Franquia = " + unidadeF.getFranquia().getNome() + "\n"
                + "Cidade = " + unidadeF.getCidade() + "\n"
                + "Endereço = " + unidadeF.getEndereco() + "\n"
                + "Responsável = " + unidadeF.getResponsavel().getNome() + "\n";
        do {
            opc = JOptionPane.showOptionDialog(null, "O que deseja alterar?\n" + dados, "Alterar Unidade de Franquia",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    String cidade = "";
                    while (cidade.equals("")) {
                        cidade = JOptionPane.showInputDialog("Informe a nova cidade da Unidade:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update unidadef set cidade = ?, dataModificacao = ? where idUnidadeF = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, cidade);
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar Cidade\n" + e.getMessage());
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
                    String endereco = "";
                    while (endereco.equals("")) {
                        endereco = JOptionPane.showInputDialog("Informe a nova endereço da Unidade:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update unidadef set endereco = ?, dataModificacao = ? where idUnidadeF = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, endereco);
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar Endereço\n" + e.getMessage());
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
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        Pessoa responsavel = pessoaDAO.getPessoaTipo(5);

                        String sql = "update unidadef set idResponsavel = ?, dataModificacao = ? where idUnidadeF = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setInt(1, responsavel.getId());
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar Responsável\n" + e.getMessage());
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

    public UnidadeF getUnidadeFZ() {
        List<UnidadeF> unidadesF = pesquisarUnidadeF();

        String texto = "Lista de Unidades de Franquia\nId  |   Nome da Franquia       |   Cidade da Unidade     |   Endereço\n";
        for (UnidadeF u : unidadesF) {
            texto += Integer.toString(u.getId());
            texto += "   |   ";
            texto += u.getFranquia().getNome();
            texto += "   |   ";
            texto += u.getCidade();
            texto += "   |   ";
            texto += u.getEndereco();
            texto += "\n";
        }

        String x;
        boolean y = true;
        UnidadeF unidadeF = null;
        int id;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da Unidade de Franquia que deseja selecionar:");
                }
                id = Integer.parseInt(x);
                for (UnidadeF u : unidadesF) {
                    if (u.getId() == id) {
                        y = false;
                        unidadeF = u;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar UnidadeF try 1\n" + e.getMessage());
            }
        } while (y);

        return unidadeF;
    }

    public UnidadeF getUnidadeFZF(Franquia franquia) {
        List<UnidadeF> unidadesF = pesquisarUnidadeF();
        List<UnidadeF> unidadesF2 = new ArrayList<>();

        String texto = "Lista de Unidades de Franquia\nId  |   Nome da Franquia       |   Cidade da Unidade     |   Endereço\n";
        for (UnidadeF u : unidadesF) {
            if (u.getFranquia().getId() == franquia.getId()) {
                texto += Integer.toString(u.getId());
                texto += "   |   ";
                texto += u.getFranquia().getNome();
                texto += "   |   ";
                texto += u.getCidade();
                texto += "   |   ";
                texto += u.getEndereco();
                texto += "\n";
            }

        }

        String x;
        boolean y = true;
        UnidadeF unidadeF = null;
        int id;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da Unidade de Franquia que deseja selecionar:");
                }
                id = Integer.parseInt(x);
                for (UnidadeF u : unidadesF) {
                    if (u.getId() == id) {
                        y = false;
                        unidadeF = u;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar UnidadeF try 1\n" + e.getMessage());
            }
        } while (y);

        return unidadeF;
    }

    public void alterarUnidadeFAdmin() {

        UnidadeF unidadeF = getUnidadeFZ();

        String idString = Integer.toString(unidadeF.getId());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        int opc;
        String[] options = {"Franquia", "Cidade", "Endereço", "Responsável", "Voltar Menu"};

        String dados = "Unidade = " + idString + "\n"
                + "Franquia = " + unidadeF.getFranquia().getNome() + "\n"
                + "Cidade = " + unidadeF.getCidade() + "\n"
                + "Endereço = " + unidadeF.getEndereco() + "\n"
                + "Responsável = " + unidadeF.getResponsavel().getNome() + "\n";
        do {
            opc = JOptionPane.showOptionDialog(null, "O que deseja alterar?\n" + dados, "Alterar Unidade de Franquia",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    Franquia franquia = franquiaDAO.getFranquiaZAdmin();

                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update unidadef set idFranquia = ?, dataModificacao = ? where idUnidadeF = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setInt(1, franquia.getId());
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar Franquia Admin\n" + e.getMessage());
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
                        cidade = JOptionPane.showInputDialog("Informe a nova cidade da Unidade:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update unidadef set cidade = ?, dataModificacao = ? where idUnidadeF = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, cidade);
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar Cidade Admin\n" + e.getMessage());
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
                        endereco = JOptionPane.showInputDialog("Informe a nova endereço da Unidade:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update unidadef set endereco = ?, dataModificacao = ? where idUnidadeF = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, endereco);
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar Endereço Admin\n" + e.getMessage());
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

                case 3 -> {
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        Pessoa responsavel = pessoaDAO.getPessoaTipo(5);

                        String sql = "update unidadef set idResponsavel = ?, dataModificacao = ? where idUnidadeF = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setInt(1, responsavel.getId());
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "UnidadeFDAO - Alterar Responsável Admin\n" + e.getMessage());
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

}
