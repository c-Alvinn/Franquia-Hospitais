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

public class MedicoDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    PessoaDAO pessoaDAO = new PessoaDAO();

    public void cadastrarMedico(Medico medico) {
        String sql = "insert into medico (crm, idPessoa, especialidade, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, medico.getCrm());
            pstm.setInt(2, medico.getPessoa().getId());
            pstm.setString(3, medico.getEspecialidade());
            java.sql.Date dataSQL = new java.sql.Date(medico.getDataCriacao().getTime());
            pstm.setDate(4, dataSQL);
            pstm.setDate(5, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "MedicoDAO - Cadastrar\n" + e.getMessage());
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

    public List<Medico> pesquisarMedico() {
        List<Medico> medicos = new ArrayList<>();

        String sql = "select * from medico";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {

                Medico medico = new Medico(rs.getString("crm"),
                        pessoaDAO.getPessoaId(rs.getInt("idPessoa")), rs.getString("especialidade"),
                        rs.getDate("dataCriacao"), rs.getDate("dataModificacao"));

                medico.setId(rs.getInt("idMedico"));

                medicos.add(medico);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "MedicoDAO - pesquisar" + e.getMessage());
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

        return medicos;
    }

    public Medico getMedicoId(int id) {

        List<Medico> lista = pesquisarMedico();

        for (Medico m : lista) {
            if (m.getId() == id) {
                return m;
            }
        }

        return null;
    }

    public void alterarMedico() {

        Medico medico = getMedicoZ();

        String idString = Integer.toString(medico.getId());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        int opc;
        String[] options = {"CRM", "Especialidade", "Dados Pessoais", "Voltar Menu"};

        String dados = medico.getPessoa().getNome() + "\n"
                + "CRM = " + medico.getCrm() + "\n"
                + "Especialidade(s) = " + medico.getEspecialidade();
        do {
            opc = JOptionPane.showOptionDialog(null, "O que deseja alterar?\n" + dados, "Alterar Médico",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    String crm = "";
                    while (crm.equals("")) {
                        crm = JOptionPane.showInputDialog("Informe o novo CRM do médico:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update medico set crm = ?, dataModificacao = ? where idMedico = " + idString;

                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, crm);
                        pstm.setDate(2, dataSQL);

                        JOptionPane.showMessageDialog(null, sql);
                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "MedicoDAO - Alterar CRM\n" + e.getMessage());
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
                    String especialidade = "";
                    while (especialidade.equals("")) {
                        especialidade = JOptionPane.showInputDialog("Informe a nova especialidade do médico:");
                    }
                    try (Connection conn = new ConnectionFactory().getConnection()) {
                        String sql = "update medico set especialidade = ?, dataModificacao = ? where idMedico = " + idString;
                        pstm = conn.prepareStatement(sql);
                        pstm.setString(1, especialidade);
                        pstm.setDate(2, dataSQL);

                        pstm.execute();
                        pstm.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "MedicoDAO - Alterar Especialidade\n" + e.getMessage());
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
                    pessoaDAO.alterarPessoaZ(medico.getPessoa());
                    break;
                }
                default -> {
                    return;
                }
            }
        } while (opc != 3);

    }

    public Medico getMedicoZ() {
        List<Medico> medicos = pesquisarMedico();

        String texto = "Lista de Medicos\nId  |   Nome\n";
        for (Medico m : medicos) {
            texto += Integer.toString(m.getId());
            texto += "   |   ";
            texto += m.getPessoa().getNome();
            texto += "\n";
        }

        String x;
        boolean y = true;
        Medico medico = null;
        int id;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id do Médico que deseja selecionar:");
                }
                id = Integer.parseInt(x);
                for (Medico m : medicos) {
                    if (m.getId() == id) {
                        y = false;
                        medico = m;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "MedicoDAO - Get Medico Z\n" + e.getMessage());
            }
        } while (y);

        return medico;
    }

    public Medico getMedicoPessoa(Pessoa pessoa) {
        List<Medico> lista = pesquisarMedico();

        for (Medico m : lista) {
            if (m.getPessoa().getId() == pessoa.getId()) {
                return m;
            }
        }

        return null;
    }
}
