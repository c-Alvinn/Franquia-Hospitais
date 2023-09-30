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

public class ConsultaDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    PessoaDAO pessoaDAO = new PessoaDAO();
    MedicoDAO medicoDAO = new MedicoDAO();
    UnidadeFDAO unidadeFDAO = new UnidadeFDAO();
    FinanceiroAdmDAO financeiroAdmDAO = new FinanceiroAdmDAO();
    

    public void cadastrarConsulta(Consulta consulta) {
        String sql = "insert into consulta (dia, horario, estado, idMedico, idPaciente, valor, idUnidade, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            java.sql.Date dataDiaSQL = new java.sql.Date(consulta.getDia().getTime());
            pstm.setDate(1, dataDiaSQL);
            java.sql.Time timeSQL = java.sql.Time.valueOf(consulta.getHorario());
            pstm.setTime(2, timeSQL);
            pstm.setString(3, consulta.getEstado());
            pstm.setInt(4, consulta.getMedico().getId());
            pstm.setInt(5, consulta.getPaciente().getId());
            pstm.setDouble(6, consulta.getValor());
            pstm.setInt(7, consulta.getUnidade().getId());
            java.sql.Date dataSQL = new java.sql.Date(consulta.getDataCriacao().getTime());
            pstm.setDate(8, dataSQL);
            pstm.setDate(9, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ConsultaDAO - Cadastrar\n" + e.getMessage());
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

    public List<Consulta> pesquisarConsulta() {
        List<Consulta> consultas = new ArrayList<>();

        String sql = "select * from consulta";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {

                Consulta consulta = new Consulta(rs.getDate("dia"), rs.getTime("horario").toLocalTime(), rs.getString("estado"),
                        medicoDAO.getMedicoId(rs.getInt("idMedico")), pessoaDAO.getPessoaId(rs.getInt("idPaciente")), rs.getDouble("valor"),
                        unidadeFDAO.getUnidadeFId(rs.getInt("idUnidade")), rs.getDate("dataCriacao"), rs.getDate("dataModificacao"));

                consulta.setId(rs.getInt("idConsulta"));

                consultas.add(consulta);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ConsultaDAO - pesquisar" + e.getMessage());
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

        return consultas;
    }

    public Consulta getConsultaId(int id) {

        List<Consulta> lista = pesquisarConsulta();

        for (Consulta c : lista) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public Consulta getConsultaZE(Medico medico) {
        List<Consulta> consultas = pesquisarConsulta();

        String texto = "Lista de Consultas Realizadas\nId  |   Nome do Paciente\n";
        for (Consulta c : consultas) {
            if (c.getMedico().getId() == medico.getId() && c.getEstado().equalsIgnoreCase("realizado")) {
                texto += Integer.toString(c.getId());
                texto += "   |   ";
                texto += c.getPaciente().getNome();
                texto += "\n";
            }
        }

        String x;
        boolean y = true;
        Consulta consulta = null;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da consulta que deseja selecionar:");
                }
                int id = Integer.parseInt(x);
                for (Consulta c : consultas) {
                    if (c.getId() == id) {
                        y = false;
                        consulta = c;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "ConsultaDAO - Get Franquia Z E\n" + e.getMessage());
            }
        } while (y);

        return consulta;
    }

    public String agendaConsultasMedico(Medico medico) {
        List<Consulta> consultas = pesquisarConsulta();

        String texto = "Agenda\nId  |   Nome do Paciente    |   Dia |   Horário |   Estado  |   Valor  |   Unidade\n";
        for (Consulta c : consultas) {
            if (c.getMedico().getId() == medico.getId()) {

                String dia = c.getDia().toString();
                String horario = c.getHorario().toString();
                String valor = Double.toString(c.getValor());

                texto += c.getId() + "   |   "
                        + c.getPaciente().getNome() + "   |   "
                        + dia + "   |   "
                        + horario + "   |   "
                        + c.getEstado() + "   |   "
                        + valor + "   |   "
                        + c.getUnidade().getId() + "\n";
            }
        }
        texto += "\nO que deseja fazer com as consultas?";

        return texto;
    }

    public void agendarConsulta(Medico medico) {
        List<Consulta> consultas = pesquisarConsulta();
        List<Consulta> consultas2 = new ArrayList<>();

        String texto = "Lista de Consultas que podem ser agendadas\nId  |   Nome do Paciente    |   Dia |   Horario\n";
        for (Consulta c : consultas) {
            if (c.getMedico().getId() == medico.getId() && c.getEstado().equalsIgnoreCase("vazio")) {
                texto += Integer.toString(c.getId()) + "   |   "
                        + c.getPaciente().getNome() + "   |   "
                        + c.getDia().toString() + "   |   "
                        + c.getHorario().toString() + "   |   "
                        + "\n";
                consultas2.add(c);
            }
        }

        String x;
        boolean y = true;
        Consulta consulta = null;
        double valor = 0.0;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da consulta que deseja selecionar:");
                }
                int id = Integer.parseInt(x);
                for (Consulta c : consultas2) {
                    if (c.getId() == id) {
                        y = false;
                        consulta = c;
                    }
                }
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog("Informe o valor a ser cobrado na consulta:");
                }
                valor = Double.parseDouble(x);

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "ConsultaDAO - Agendar Consulta\n" + e.getMessage());
            }
        } while (y);

        consulta.setEstado("agendado");
        consulta.setValor(valor);
        consulta.setDataModificacao(new Date());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "update consulta set estado = ?, dataModificacao = ?, valor = ? where idConsulta = " + Integer.toString(consulta.getId());

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, consulta.getEstado());
            pstm.setDate(2, dataSQL);
            pstm.setString(3, Double.toString(valor));

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ConsultaDAO - AgendarConsulta SQL\n" + e.getMessage());
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
    
    public int idConsulta(){
        
        List<Consulta> consultas = pesquisarConsulta();
        
        int i = 0;
        for(Consulta c : consultas){
            i = c.getId();
        }

        return i;
    }

    public void realizarConsulta(Medico medico) {
        List<Consulta> consultas = pesquisarConsulta();
        List<Consulta> consultas2 = new ArrayList<>();

        String texto = "Lista de Consultas que podem ser realizadas\nId  |   Nome do Paciente    |   Dia |   Horario\n";
        for (Consulta c : consultas) {
            if (c.getMedico().getId() == medico.getId() && c.getEstado().equalsIgnoreCase("agendado")) {
                texto += Integer.toString(c.getId()) + "   |   "
                        + c.getPaciente().getNome() + "   |   "
                        + c.getDia().toString() + "   |   "
                        + c.getHorario().toString() + "   |   "
                        + "\n";
                consultas2.add(c);
            }
        }

        String x;
        boolean y = true;
        Consulta consulta = null;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da consulta que deseja selecionar:");
                }
                int id = Integer.parseInt(x);
                for (Consulta c : consultas2) {
                    if (c.getId() == id) {
                        y = false;
                        consulta = c;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "ConsultaDAO - Realizar Consulta\n" + e.getMessage());
            }
        } while (y);

        consulta.setEstado("realizado");
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "update consulta set estado = ?, dataModificacao = ? where idConsulta = " + Integer.toString(consulta.getId());

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, consulta.getEstado());
            pstm.setDate(2, dataSQL);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ConsultaDAO - RealizarConsulta SQL\n" + e.getMessage());
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

        FinanceiroAdm financeiroAdm = new FinanceiroAdm("entrada", consulta.getValor(), consulta.getUnidade(), "consulta", dataSQL, null);
        financeiroAdmDAO.cadastrarFinanceiroAdm(financeiroAdm);
        
        FinanceiroMedDAO financeiroMedDAO = new FinanceiroMedDAO();
        FinanceiroMed financeiroMed = new FinanceiroMed((0.7 * consulta.getValor()), medico, "agendado", consulta.getUnidade().getFranquia(), dataSQL, null);
        financeiroMedDAO.cadastrarFinanceiroMed(financeiroMed);

        JOptionPane.showMessageDialog(null, "Processo concluido!");

    }

    public void cancelarConsulta(Medico medico) {
        List<Consulta> consultas = pesquisarConsulta();
        List<Consulta> consultas2 = new ArrayList<>();

        String texto = "Lista de Consultas que podem ser canceladas\nId  |   Nome do Paciente    |   Dia |   Horario\n";
        for (Consulta c : consultas) {
            if (c.getMedico().getId() == medico.getId() && c.getEstado().equalsIgnoreCase("agendado")) {
                texto += Integer.toString(c.getId()) + "   |   "
                        + c.getPaciente().getNome() + "   |   "
                        + c.getDia().toString() + "   |   "
                        + c.getHorario().toString() + "   |   "
                        + "\n";
                consultas2.add(c);
            }
        }

        String x;
        boolean y = true;
        Consulta consulta = null;
        double valor = 0.0;

        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog(texto + "\nInforme o id da consulta que deseja cancelar:");
                }
                int id = Integer.parseInt(x);
                for (Consulta c : consultas2) {
                    if (c.getId() == id) {
                        y = false;
                        consulta = c;
                    }
                }

            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "ConsultaDAO - Cancelar Consulta\n" + e.getMessage());
            }
        } while (y);

        consulta.setEstado("cancelado");
        consulta.setDataModificacao(new Date());
        java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());

        try (Connection conn = new ConnectionFactory().getConnection()) {
            String sql = "update consulta set estado = ?, dataModificacao = ? where idConsulta = " + Integer.toString(consulta.getId());

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, consulta.getEstado());
            pstm.setDate(2, dataSQL);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ConsultaDAO - Cancelar Consulta SQL\n" + e.getMessage());
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

    public void mostrarConsultas() {
        List<Consulta> consultas = pesquisarConsulta();

        String texto = "Lista de Consultas\nId  |   Nome do Paciente    |   Dia |   Horário |   Estado  |   Valor  |   Unidade\n";
        for (Consulta c : consultas) {

            String dia = c.getDia().toString();
            String horario = c.getHorario().toString();
            String valor = Double.toString(c.getValor());

            texto += c.getId() + "   |   "
                    + c.getPaciente().getNome() + "   |   "
                    + dia + "   |   "
                    + horario + "   |   "
                    + c.getEstado() + "   |   "
                    + valor + "   |   "
                    + c.getUnidade().getId() + "\n";

        }
        
        JOptionPane.showMessageDialog(null, texto);
        return;
    }
    
    
}
