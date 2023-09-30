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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class FinanceiroAdmDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    MedicoDAO medicoDAO = new MedicoDAO();
    UnidadeFDAO unidadeFDAO = new UnidadeFDAO();

    public void cadastrarFinanceiroAdm(FinanceiroAdm financeiroAdm) {
        String sql = "insert into financeiroAdm (tipoMovimento, valor, idUnidade, descritivo, dataCriacao, dataModificacao) values (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, financeiroAdm.getTipoMovimento());
            pstm.setDouble(2, financeiroAdm.getValor());
            pstm.setInt(3, financeiroAdm.getUnidade().getId());
            pstm.setString(4, financeiroAdm.getDescritivo());
            java.sql.Date dataSQL = new java.sql.Date(financeiroAdm.getDataCriacao().getTime());
            pstm.setDate(5, dataSQL);
            pstm.setDate(6, null);

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - Cadastrar\n" + e.getMessage());
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

    public List<FinanceiroAdm> pesquisarFinanceiroAdm() {

        List<FinanceiroAdm> financeirosAdm = new ArrayList<>();

        String sql = "select * from financeiroadm";

        try (Connection conn = new ConnectionFactory().getConnection()) {
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {

                FinanceiroAdm financeiroAdm = new FinanceiroAdm(rs.getString("tipoMovimento"), rs.getDouble("valor"),
                        unidadeFDAO.getUnidadeFId(rs.getInt("idUnidade")), rs.getString("descritivo"),
                        rs.getDate("dataCriacao"), rs.getDate("dataModificacao"));

                financeiroAdm.setId(rs.getInt("idFinanceiroAdm"));

                financeirosAdm.add(financeiroAdm);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - pesquisar" + e.getMessage());
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

        return financeirosAdm;
    }

    public FinanceiroAdm getFinanceiroAdmId(int id) {

        List<FinanceiroAdm> lista = pesquisarFinanceiroAdm();

        for (FinanceiroAdm f : lista) {
            if (f.getId() == id) {
                return f;
            }
        }

        return null;
    }

    public void relatorioFinanceiroAdm() {

        List<FinanceiroAdm> financeirosAdm = pesquisarFinanceiroAdm();

        try {
            
            // Verifica se a pasta "RELATORIOS" existe, caso não exista, cria-a
            File relatoriosFolder = new File("RELATORIOS");
            if (!relatoriosFolder.exists()) {
                relatoriosFolder.mkdir();
            }

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RELATORIOS/relatorioFinanceiroAdm.pdf"));
            document.open();
            
            // Obtém a data atual
            Date dataAtual = new Date();
            // Formata a data no formato desejado
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = dateFormat.format(dataAtual);
            
            document.add(new Paragraph("|"));
            document.add(new Paragraph("|RELATORIO FINANCEIRO GERADO EM (" + dataFormatada + ")"));
            document.add(new Paragraph("|\n\n"));

            int cont = 1;
            
            for (FinanceiroAdm f : financeirosAdm) {
                document.add(new Paragraph("Relatório financeiro N°" + cont + ":"));
                document.add(new Paragraph("ID: " + f.getId()));
                document.add(new Paragraph("TIPO MOVIMENTO: " + f.getTipoMovimento()));
                document.add(new Paragraph("VALOR: " + f.getValor()));
                document.add(new Paragraph("UNIDADE: " + f.getUnidade().getId()));
                document.add(new Paragraph("DESCRITIVO: " + f.getDescritivo()));
                document.add(new Paragraph("DATA CRIACAO: " + f.getDataCriacao()));
                document.add(new Paragraph("DATA MODIFICACAO: " + f.getDataModificacao()));
                document.add(new Paragraph("==================================================================="));
                document.add(new Paragraph("\n")); // Quebra de linha para separar cada ficha
                cont++; //INCREMENTA CONTADOR
            }

            document.close();
            writer.close();
            JOptionPane.showMessageDialog(null, "Gerado com sucesso!");
        } catch (DocumentException e) {
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - relatorioFinanceiroAdm: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - relatorioFinanceiroAdm: " + e.getMessage());
        }
    }

    public void relatorioFinanceiroAdmUnidade(UnidadeF unidadeF) {

        List<FinanceiroAdm> financeirosAdm = pesquisarFinanceiroAdm();

        try {
            
            // Verifica se a pasta "RELATORIOS" existe, caso não exista, cria-a
            File relatoriosFolder = new File("RELATORIOS");
            if (!relatoriosFolder.exists()) {
                relatoriosFolder.mkdir();
            }

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RELATORIOS/relatorioFinanceiroAdmUnidadeF" + Integer.toString(unidadeF.getId()) + ".pdf"));
            document.open();
            
            // Obtém a data atual
            Date dataAtual = new Date();
            // Formata a data no formato desejado
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = dateFormat.format(dataAtual);
            
            document.add(new Paragraph("|"));
            document.add(new Paragraph("|RELATORIO FINANCEIRO UNIDADE " + Integer.toString(unidadeF.getId()) + " GERADO EM (" + dataFormatada + ")"));
            document.add(new Paragraph("|\n\n"));

            int cont = 1;

            for (FinanceiroAdm f : financeirosAdm) {
                if (f.getUnidade().getId() == unidadeF.getId()) {
                    document.add(new Paragraph("Relatório financeiro N°" + cont + ":"));
                    document.add(new Paragraph("ID: " + f.getId()));
                    document.add(new Paragraph("TIPO MOVIMENTO: " + f.getTipoMovimento()));
                    document.add(new Paragraph("VALOR: " + f.getValor()));
                    document.add(new Paragraph("UNIDADE: " + f.getUnidade().getId()));
                    document.add(new Paragraph("DESCRITIVO: " + f.getDescritivo()));
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
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - relatorioFinanceiroAdm: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - relatorioFinanceiroAdm: " + e.getMessage());
        }
    }

    public void relatorioFinanceiroAdmFranquia(Franquia franquia) {

        List<FinanceiroAdm> financeirosAdm = pesquisarFinanceiroAdm();

        try {
            
            // Verifica se a pasta "RELATORIOS" existe, caso não exista, cria-a
            File relatoriosFolder = new File("RELATORIOS");
            if (!relatoriosFolder.exists()) {
                relatoriosFolder.mkdir();
            }

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RELATORIOS/relatorioFinanceiroAdmFranquia" + Integer.toString(franquia.getId()) + ".pdf"));
            document.open();
            
            // Obtém a data atual
            Date dataAtual = new Date();
            // Formata a data no formato desejado
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = dateFormat.format(dataAtual);
            
            document.add(new Paragraph("|"));
            document.add(new Paragraph("|RELATORIO FINANCEIRO FRANQUIA " + Integer.toString(franquia.getId()) + " GERADO EM (" + dataFormatada + ")"));
            document.add(new Paragraph("|\n\n"));

            int cont = 1;

            for (FinanceiroAdm f : financeirosAdm) {
                if (f.getUnidade().getFranquia().getId() == franquia.getId()) {
                    document.add(new Paragraph("Relatório financeiro N°" + cont + ":"));
                    document.add(new Paragraph("ID: " + f.getId()));
                    document.add(new Paragraph("TIPO MOVIMENTO: " + f.getTipoMovimento()));
                    document.add(new Paragraph("VALOR: " + f.getValor()));
                    document.add(new Paragraph("UNIDADE: " + f.getUnidade().getId()));
                    document.add(new Paragraph("DESCRITIVO: " + f.getDescritivo()));
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
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - relatorioFinanceiroAdm: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FinanceiroAdmDAO - relatorioFinanceiroAdm: " + e.getMessage());
        }
    }

}
