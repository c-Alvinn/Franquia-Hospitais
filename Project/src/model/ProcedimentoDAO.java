package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.ConnectionFactory;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;

public class ProcedimentoDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    ConsultaDAO consultaDAO = new ConsultaDAO();
    FinanceiroAdmDAO financeiroAdmDAO = new FinanceiroAdmDAO();
    FinanceiroMedDAO financeiroMedDAO = new FinanceiroMedDAO();

    public void cadastrarProcedimento(Procedimento procedimento) {
        String sql = "insert into procedimento (nome, idConsulta, dia, horario, estado, valor, laudo, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, procedimento.getNome());
            pstm.setInt(2, procedimento.getConsulta().getId());
            java.sql.Date dataDiaSQL = new java.sql.Date(procedimento.getDia().getTime());
            pstm.setDate(3, dataDiaSQL);
            java.sql.Time timeSQL = java.sql.Time.valueOf(procedimento.getHorario());
            pstm.setTime(4, timeSQL);
            pstm.setString(5, procedimento.getEstado());
            pstm.setDouble(6, procedimento.getValor());
            pstm.setString(7, procedimento.getLaudo());
            java.sql.Date dataSQL = new java.sql.Date(procedimento.getDataCriacao().getTime());
            pstm.setDate(8, dataSQL);
            pstm.setDate(9, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ProcedimentoDAO - Cadastrar\n" + e.getMessage());
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

    public List<Procedimento> pesquisarProcedimento() {

        List<Procedimento> procedimentos = new ArrayList<>();

        String sql = "select * from procedimento";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {

                Procedimento procedimento = new Procedimento(rs.getString("nome"), consultaDAO.getConsultaId(rs.getInt("idConsulta")),
                        rs.getDate("dia"), rs.getTime("horario").toLocalTime(), rs.getString("estado"), rs.getDouble("valor"),
                        rs.getString("laudo"), rs.getDate("dataCriacao"), rs.getDate("dataModificacao"));

                procedimento.setId(rs.getInt("idProcedimento"));

                procedimentos.add(procedimento);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ProcedimentoDAO - pesquisar" + e.getMessage());
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

        return procedimentos;
    }

    public Procedimento getProcedimento(int id) {

        List<Procedimento> lista = pesquisarProcedimento();

        for (Procedimento p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public void mostrarProcedimentoPaciente(Pessoa pessoa) {
        List<Procedimento> procedimentos = pesquisarProcedimento();

        String texto = "Sua Lista de Procedimento\nProcedimento |   Medico  |   Dia        |   Horario    |    Valor    |   Estado\n";
        for (Procedimento p : procedimentos) {
            if (p.getConsulta().getPaciente().getId() == pessoa.getId()) {
                String dia = p.getDia().toString();
                String horario = p.getHorario().toString();
                String valor = Double.toString(p.getValor());

                texto += p.getNome() + "   |   "
                        + p.getConsulta().getMedico().getPessoa().getNome() + "   |   "
                        + dia + "   |   "
                        + horario + "   |   "
                        + valor + "   |   "
                        + p.getConsulta().getEstado() + "\n";
            }
        }

        JOptionPane.showMessageDialog(null, texto);

    }

    public String agendaProcedimentosMedico(Medico medico) {
        List<Procedimento> procedimentos = pesquisarProcedimento();

        String texto = "Agenda\nId  |   Nome do Procedimento    |   Nome do Paciente    |   Dia |   Horário |   Estado  |   Laudo   |   Valor\n";
        for (Procedimento p : procedimentos) {
            if (p.getConsulta().getMedico().getId() == medico.getId()) {

                String dia = p.getDia().toString();
                String horario = p.getHorario().toString();
                String valor = Double.toString(p.getValor());

                texto += p.getId() + "   |   "
                        + p.getNome() + "   |   "
                        + p.getConsulta().getPaciente().getNome() + "   |   "
                        + dia + "   |   "
                        + horario + "   |   "
                        + p.getEstado() + "   |   "
                        + p.getLaudo() + "   |   "
                        + valor + "\n";
            }
        }
        texto += "\nO que deseja fazer com os procedimentos?";

        return texto;
    }

    public void agendarProcedimento(Medico medico) {
        List<Procedimento> procedimentos = pesquisarProcedimento();
        List<Procedimento> procedimentos2 = new ArrayList<>();

        String texto = "Lista de Procedimentos que podem ser agendadas\nId  |   Nome do Paciente    |   Dia |   Horario\n";
        for (Procedimento c : procedimentos) {
            if (c.getConsulta().getMedico().getId() == medico.getId() && c.getEstado().equalsIgnoreCase("vazio")) {
                texto += Integer.toString(c.getId()) + "   |   "
                        + c.getConsulta().getPaciente().getNome() + "   |   "
                        + c.getDia().toString() + "   |   "
                        + c.getHorario().toString() + "   |   "
                        + "\n";
                procedimentos2.add(c);
            }
        }

        String x;
        boolean y = true;
        Procedimento procedimento = null;
        double valor = 0.0;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da procedimento que deseja selecionar:");
                }
                int id = Integer.parseInt(x);
                for (Procedimento c : procedimentos2) {
                    if (c.getId() == id) {
                        y = false;
                        procedimento = c;
                    }
                }
//                x = "";
//                while (x.equals("")) {
//                    x = JOptionPane.showInputDialog("Informe o valor a ser cobrado na procedimento:");
//                }
//                valor = Double.parseDouble(x);

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "ProcedimentoDAO - Agendar Procedimento\n" + e.getMessage());
            }
        } while (y);

        procedimento.setEstado("agendado");
        procedimento.setDataModificacao(new Date());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "update procedimento set estado = ?, dataModificacao = ?, valor = ? where idProcedimento = " + Integer.toString(procedimento.getId());

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, procedimento.getEstado());
            pstm.setDate(2, dataSQL);
            pstm.setString(3, Double.toString(valor));

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ProcedimentoDAO - AgendarProcedimento SQL\n" + e.getMessage());
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

    public void realizarProcedimento(Medico medico) {
        List<Procedimento> procedimentos = pesquisarProcedimento();
        List<Procedimento> procedimentos2 = new ArrayList<>();

        String texto = "Lista de Procedimentos que podem ser realizadas\nId  |   Nome do Paciente    |   Dia |   Horario\n";
        for (Procedimento c : procedimentos) {
            if (c.getConsulta().getMedico().getId() == medico.getId() && c.getEstado().equalsIgnoreCase("agendado")) {
                texto += Integer.toString(c.getId()) + "   |   "
                        + c.getConsulta().getPaciente().getNome() + "   |   "
                        + c.getDia().toString() + "   |   "
                        + c.getHorario().toString() + "   |   "
                        + "\n";
                procedimentos2.add(c);
            }
        }

        String x;
        boolean y = true;
        Procedimento procedimento = null;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da procedimento que deseja selecionar:");
                }
                int id = Integer.parseInt(x);
                for (Procedimento c : procedimentos2) {
                    if (c.getId() == id) {
                        y = false;
                        procedimento = c;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "ProcedimentoDAO - Realizar Procedimento\n" + e.getMessage());
            }
        } while (y);

        procedimento.setEstado("realizado");
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "update procedimento set estado = ?, dataModificacao = ? where idProcedimento = " + Integer.toString(procedimento.getId());

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, procedimento.getEstado());
            pstm.setDate(2, dataSQL);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ProcedimentoDAO - RealizarProcedimento SQL\n" + e.getMessage());
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

        FinanceiroAdm financeiroAdm = new FinanceiroAdm("entrada", procedimento.getValor(), procedimento.getConsulta().getUnidade(), "procedimento", dataSQL, null);
        financeiroAdmDAO.cadastrarFinanceiroAdm(financeiroAdm);

        FinanceiroMed financeiroMed = new FinanceiroMed((0.7 * procedimento.getValor()), medico, "agendado", procedimento.getConsulta().getUnidade().getFranquia(), dataSQL, null);
        financeiroMedDAO.cadastrarFinanceiroMed(financeiroMed);

        JOptionPane.showMessageDialog(null, "Processo concluido!");

    }

    public void cancelarProcedimento(Medico medico) {
        List<Procedimento> procedimentos = pesquisarProcedimento();
        List<Procedimento> procedimentos2 = new ArrayList<>();

        String texto = "Lista de Procedimentos que podem ser canceladas\nId  |   Nome do Paciente    |   Dia |   Horario\n";
        for (Procedimento c : procedimentos) {
            if (c.getConsulta().getMedico().getId() == medico.getId() && c.getEstado().equalsIgnoreCase("agendado")) {
                texto += Integer.toString(c.getId()) + "   |   "
                        + c.getConsulta().getPaciente().getNome() + "   |   "
                        + c.getDia().toString() + "   |   "
                        + c.getHorario().toString() + "   |   "
                        + "\n";
                procedimentos2.add(c);
            }
        }

        String x;
        boolean y = true;
        Procedimento procedimento = null;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da procedimento que deseja cancelar:");
                }
                int id = Integer.parseInt(x);
                for (Procedimento c : procedimentos2) {
                    if (c.getId() == id) {
                        y = false;
                        procedimento = c;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "ProcedimentoDAO - Cancelar Procedimento\n" + e.getMessage());
            }
        } while (y);

        procedimento.setEstado("cancelado");
        procedimento.setDataModificacao(new Date());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "update procedimento set estado = ?, dataModificacao = ? where idProcedimento = " + Integer.toString(procedimento.getId());

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, procedimento.getEstado());
            pstm.setDate(2, dataSQL);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ProcedimentoDAO - Cancelar Procedimento SQL\n" + e.getMessage());
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

    public void mostrarTodosProcedimentos() {
        List<Procedimento> procedimentos = pesquisarProcedimento();

        String texto = "Todos os Procedimentos\nId  |   Nome do Procedimento    |   Nome do Paciente    |   Nome do Médico    |   Dia |   Horário |   Estado  |   Laudo   |   Valor\n";
        for (Procedimento p : procedimentos) {

            String dia = p.getDia().toString();
            String horario = p.getHorario().toString();
            String valor = Double.toString(p.getValor());

            texto += p.getId() + "   |   "
                    + p.getNome() + "   |   "
                    + p.getConsulta().getPaciente().getNome() + "   |   "
                    + p.getConsulta().getMedico().getPessoa().getNome() + "   |   "
                    + dia + "   |   "
                    + horario + "   |   "
                    + p.getEstado() + "   |   "
                    + p.getLaudo() + "   |   "
                    + valor + "\n";

        }
        
        JOptionPane.showMessageDialog(null, texto);
        return;
    }

}
