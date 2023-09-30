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

public class InfoConsultaDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    ConsultaDAO consultaDAO = new ConsultaDAO();

    public void cadastrarInfoConsulta(InfoConsulta infoConsulta) {
        String sql = "insert into infoConsulta (idConsulta, descricao, dataCriacao, dataModificacao) values (?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, infoConsulta.getConsulta().getId());
            pstm.setString(2, infoConsulta.getDescricao());
            java.sql.Date dataSQL = new java.sql.Date(infoConsulta.getDataCriacao().getTime());
            pstm.setDate(3, dataSQL);
            pstm.setDate(4, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "InfoConsultaDAO - Cadastrar\n" + e.getMessage());
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

    public List<InfoConsulta> pesquisarInfoConsulta() {

        List<InfoConsulta> infoConsultas = new ArrayList<>();

        String sql = "select * from infoconsulta";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {

                InfoConsulta infoConsulta = new InfoConsulta(consultaDAO.getConsultaId(rs.getInt("idConsulta")), rs.getString("descricao"),
                        rs.getDate("dataCriacao"), rs.getDate("dataModificacao"));

                infoConsulta.setId(rs.getInt("idInfoConsulta"));

                infoConsultas.add(infoConsulta);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "InfoConsultaDAO - pesquisar" + e.getMessage());
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

        return infoConsultas;
    }

    public InfoConsulta getInfoConsultaId(int id) {

        List<InfoConsulta> lista = pesquisarInfoConsulta();

        for (InfoConsulta i : lista) {
            if (i.getId() == id) {
                return i;
            }
        }

        return null;
    }

    public void mostrarInfoConsultaPaciente(Pessoa pessoa) {
        List<InfoConsulta> infoConsultas = pesquisarInfoConsulta();

        String texto = "Sua Lista de Consultas\nMedico  |   Dia        |   Horario    |    Valor    |   Estado\n";
        for (InfoConsulta iF : infoConsultas) {
            if (iF.getConsulta().getPaciente().getId() == pessoa.getId()) {
                String dia = iF.getConsulta().getDia().toString();
                String horario = iF.getConsulta().getHorario().toString();
                String valor = Double.toString(iF.getConsulta().getValor());

                texto += iF.getConsulta().getMedico().getPessoa().getNome() + "   |   "
                        + dia + "   |   "
                        + horario + "   |   "
                        + valor + "   |   "
                        + iF.getConsulta().getEstado() + "\n";
            }
        }

        JOptionPane.showMessageDialog(null, texto);

    }
}
