package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.ConnectionFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;

public class FinanceiroMedDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    MedicoDAO medicoDAO = new MedicoDAO();
    FranquiaDAO franquiaDAO = new FranquiaDAO();


    public void cadastrarFinanceiroMed(FinanceiroMed financeiroMed) {
        String sql = "insert into financeiroMed (valorMedico, idMedico, estado, idFranquia, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            pstm.setDouble(1, financeiroMed.getValorMedico());
            pstm.setInt(2, financeiroMed.getMedico().getId());
            pstm.setString(3, financeiroMed.getEstado());
            pstm.setDouble(4, financeiroMed.getFranquia().getId());
            java.sql.Date dataSQL = new java.sql.Date(financeiroMed.getDataCriacao().getTime());
            pstm.setDate(5, dataSQL);
            pstm.setDate(6, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FinanceiroMedDAO - Cadastrar\n" + e.getMessage());
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

    public List<FinanceiroMed> pesquisarFinanceiroMed() {

        List<FinanceiroMed> financeirosMed = new ArrayList<>();

        String sql = "select * from financeiromed";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {

                FinanceiroMed financeiroMed = new FinanceiroMed(rs.getDouble("valorMedico"), medicoDAO.getMedicoId(rs.getInt("idMedico")),
                        rs.getString("estado"), franquiaDAO.getFranquiaId(rs.getInt("idFranquia")),
                        rs.getDate("dataCriacao"), rs.getDate("dataModificacao"));

                financeiroMed.setId(rs.getInt("idFinanceiroMed"));

                financeirosMed.add(financeiroMed);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FinanceiroMedDAO - pesquisar\n" + e.getMessage());
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

        return financeirosMed;
    }

    public FinanceiroMed getFinanceiroMedId(int id) {

        List<FinanceiroMed> lista = pesquisarFinanceiroMed();

        for (FinanceiroMed f : lista) {
            if (f.getId() == id) {
                return f;
            }
        }

        return null;
    }

    public void gerarReceitaMedicos() {

        ConsultaDAO consultaDAO = new ConsultaDAO();
        ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();

        List<Consulta> consultas = consultaDAO.pesquisarConsulta();
        List<Procedimento> procedimentos = procedimentoDAO.pesquisarProcedimento();
        List<Medico> medicos = medicoDAO.pesquisarMedico();
        List<FinanceiroMed> financeirosMed = new ArrayList<>();

        for (Consulta c : consultas) {
            for (Medico m : medicos) {
                if (c.getMedico().getId() == m.getId() && c.getEstado().equals("realizado")) {
                    FinanceiroMed f = new FinanceiroMed(c.getValor(), m, "agendado", c.getUnidade().getFranquia(), new Date(), null);
                    financeirosMed.add(f);
                }
            }

        }
        for (Procedimento c : procedimentos) {
            for (Medico m : medicos) {
                if (c.getConsulta().getMedico().getId() == m.getId() && c.getEstado().equals("realizado")) {
                    FinanceiroMed f = new FinanceiroMed(c.getValor(), m, "agendado", c.getConsulta().getUnidade().getFranquia(), new Date(), null);
                    financeirosMed.add(f);
                }
            }

        }

        for (FinanceiroMed f : financeirosMed) {
            cadastrarFinanceiroMed(f);
        }

    }

    public void pagar() {
        List<FinanceiroMed> financeirosMed = pesquisarFinanceiroMed();

        for (FinanceiroMed f : financeirosMed) {
            if (f.getEstado().equals("agendado")) {
                f.setEstado("pago");

                String idString = Integer.toString(f.getId());
                java.sql.Date dataSQL = new java.sql.Date(new Date().getTime());
                String sql = "update financeiromed set estado = ?, dataModificacao = ? where idFinanceiroMed = " + idString;

                try (Connection conn = new ConnectionFactory().getConnection()) {
                    pstm = conn.prepareStatement(sql);
                    
                    pstm.setString(1, f.getEstado());
                    pstm.setDate(2, dataSQL);

                    pstm.execute();
                    pstm.close();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "FinanceiroMedDAO - Pagar\n" + e.getMessage());
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
        }

    }

    public void relatorioFinanceiroMed() {

        List<FinanceiroMed> financeirosMed = pesquisarFinanceiroMed();

        try {

            // Verifica se a pasta "RELATORIOS" existe, caso não exista, cria-a
            File relatoriosFolder = new File("RELATORIOS");
            if (!relatoriosFolder.exists()) {
                relatoriosFolder.mkdir();
            }

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RELATORIOS/relatorioFinanceiroMed.pdf"));
            document.open();

            // Obtém a data atual
            Date dataAtual = new Date();
            // Formata a data no formato desejado
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = dateFormat.format(dataAtual);

            document.add(new Paragraph("|"));
            document.add(new Paragraph("|RELATORIO FINANCEIRO-MED GERADO EM (" + dataFormatada + ")"));
            document.add(new Paragraph("|\n\n"));

            int cont = 1;

            for (FinanceiroMed f : financeirosMed) {
                document.add(new Paragraph("Relatório financeiro N°" + cont + ":"));
                document.add(new Paragraph("ID: " + f.getId()));
                document.add(new Paragraph("VALOR: " + f.getValorMedico()));
                document.add(new Paragraph("MEDICO: " + f.getMedico().getPessoa().getNome()));
                document.add(new Paragraph("ESTADO: " + f.getEstado()));
                document.add(new Paragraph("FRANQUIA: " + f.getFranquia().getNome()));
                document.add(new Paragraph("DATA CRIACAO: " + f.getDataCriacao()));
                document.add(new Paragraph("DATA MODIFICACAO: " + f.getDataModificacao()));
                document.add(new Paragraph("==================================================================="));
                document.add(new Paragraph("\n")); // Quebra de linha para separar cada ficha
                cont++;
            }

            document.close();
            writer.close();
            JOptionPane.showMessageDialog(null, "Gerado com sucesso!");
        } catch (DocumentException e) {
            JOptionPane.showMessageDialog(null, "FinanceiroMedDAO - relatorioFinanceiroMed: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FinanceiroMedDAO - relatorioFinanceiroMed: " + e.getMessage());
        }
    }

    public void relatorioFinanceiroMedFranquia(Franquia franquia) {

        List<FinanceiroMed> financeirosMed = pesquisarFinanceiroMed();

        try {

            // Verifica se a pasta "RELATORIOS" existe, caso não exista, cria-a
            File relatoriosFolder = new File("RELATORIOS");
            if (!relatoriosFolder.exists()) {
                relatoriosFolder.mkdir();
            }

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RELATORIOS/relatorioFinanceiroMedFranquia" + Integer.toString(franquia.getId()) + ".pdf"));
            document.open();

            // Obtém a data atual
            Date dataAtual = new Date();
            // Formata a data no formato desejado
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = dateFormat.format(dataAtual);

            document.add(new Paragraph("|"));
            document.add(new Paragraph("|RELATORIO FINANCEIRO-MED FRANQUIA " + Integer.toString(franquia.getId()) + " GERADO EM (" + dataFormatada + ")"));
            document.add(new Paragraph("|\n\n"));

            int cont = 1;

            for (FinanceiroMed f : financeirosMed) {
                if (f.getFranquia().getId() == franquia.getId()) {
                    document.add(new Paragraph("Relatório financeiro N°" + cont + ":"));
                    document.add(new Paragraph("ID: " + f.getId()));
                    document.add(new Paragraph("VALOR: " + f.getValorMedico()));
                    document.add(new Paragraph("MEDICO: " + f.getMedico().getPessoa().getNome()));
                    document.add(new Paragraph("ESTADO: " + f.getEstado()));
                    document.add(new Paragraph("FRANQUIA: " + f.getFranquia().getNome()));
                    document.add(new Paragraph("DATA CRIACAO: " + f.getDataCriacao()));
                    document.add(new Paragraph("DATA MODIFICACAO: " + f.getDataModificacao()));
                    document.add(new Paragraph("==================================================================="));
                    document.add(new Paragraph("\n")); // Quebra de linha para separar cada ficha
                    cont++;
                }

            }

            document.close();
            writer.close();
            JOptionPane.showMessageDialog(null, "Gerado com sucesso!");
        } catch (DocumentException e) {
            JOptionPane.showMessageDialog(null, "FinanceiroMedDAO - relatorioFinanceiroMed: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FinanceiroMedDAO - relatorioFinanceiroMed: " + e.getMessage());
        }
    }

}
