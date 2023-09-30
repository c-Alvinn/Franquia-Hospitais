package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;
import model.*;
import connection.ConnectionFactory;

public class MenuCadastrar {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    
    ConsultaDAO consultaDAO = new ConsultaDAO();
    FinanceiroAdmDAO financeiroAdmDAO = new FinanceiroAdmDAO();
    FinanceiroMedDAO financeiroMedDAO = new FinanceiroMedDAO();
    FranquiaDAO franquiaDAO = new FranquiaDAO();
    InfoConsultaDAO infoConsultaDAO = new InfoConsultaDAO();
    MedicoDAO medicoDAO = new MedicoDAO();
    PessoaDAO pessoaDAO = new PessoaDAO();
    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();
    UnidadeFDAO unidadeFDAO = new UnidadeFDAO();

    public Pessoa cadastrarPessoa() {

        Pessoa pessoa = null;

        String nome = "";
        while (nome.equals("")) {
            nome = JOptionPane.showInputDialog("Informe o nome da Pessoa:");
        };

        String endereco = "";
        while (endereco.equals("")) {
            endereco = JOptionPane.showInputDialog("Informe o endereco da Pessoa:");
        };

        String cpf = "";
        while (cpf.equals("")) {
            cpf = JOptionPane.showInputDialog("Informe o CPF da Pessoa:");
        };

        String telefone = "";
        while (telefone.equals("")) {
            telefone = JOptionPane.showInputDialog("Informe o telefone da Pessoa:");
        };

        String login = "";
        while (login.equals("")) {
            login = JOptionPane.showInputDialog("Informe o login da Pessoa:");
        };

        String senha = "";
        while (senha.equals("")) {
            senha = JOptionPane.showInputDialog("Informe o senha da Pessoa:");
        };

        int tipoUsuario = 1;
        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            pessoa = new Pessoa(nome, endereco, cpf, telefone, login, senha, tipoUsuario, dataCriacao, dataModificacao);

            pessoaDAO.cadastrarPessoa(pessoa);

            JOptionPane.showMessageDialog(null, "Cadastro concluido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro Pessoa\n" + e.getMessage());
        }
        
        List<Pessoa> pessoas = pessoaDAO.pesquisarPessoa();
        for(Pessoa p : pessoas){
            if(p.getLogin().equals(login)){
                
                pessoa.setId(p.getId());
                break;
            }
        }
        return pessoa;

    }

    public Franquia cadastrarFranquia(Pessoa pessoa) {

        Franquia franquia = null;

        String nome = "";
        while (nome.equals("")) {
            nome = JOptionPane.showInputDialog("Informe o nome da Franquia:");
        };

        String cnpj = "";
        while (cnpj.equals("")) {
            cnpj = JOptionPane.showInputDialog("Informe o CNPJ da Franquia:");
        };

        String cidade = "";
        while (cidade.equals("")) {
            cidade = JOptionPane.showInputDialog("Informe o cidade da Franquia:");
        };

        String endereco = "";
        while (endereco.equals("")) {
            endereco = JOptionPane.showInputDialog("Informe o endereço da Franquia:");
        };

        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            franquia = new Franquia(nome, cnpj, cidade, endereco, pessoa, dataCriacao, dataModificacao);

            franquiaDAO.cadastrarFranquia(franquia);

            JOptionPane.showMessageDialog(null, "Cadastro concluido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro Franquia\n" + e.getMessage());
        }

        return franquia;

    }
    
    public void cadastrarMedico() {

        Pessoa pessoa = null;
        int opc;
        String[] options = {"Pessoa Cadastrada", "Cadastrar uma Nova Pessoa"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Informe se o médico que será criado já está cadastrado no Sistema ou se deverá ser Cadastrado",
                    "Pessoa -> Médico",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    pessoa = pessoaDAO.getPessoaTipo(1);
                    if (pessoa == null) {
                        JOptionPane.showMessageDialog(null, "Não existe pessoa cadastrada apto para tal.");
                        opc = 1;
                    } else {
                        opc = 2;
                    }
                    pessoaDAO.alterarTipoUsuario(pessoa, 2);
                    break;
                }

                case 1 -> {
                    pessoa = cadastrarPessoa();
                    pessoaDAO.alterarTipoUsuario(pessoa, 2);
                    opc = 2;
                    break;
                }

                default -> {
                    opc = 1;
                }
            }
        } while (opc != 2);

        String crm = "";
        while (crm.equals("")) {
            crm = JOptionPane.showInputDialog("Informe o CRM do Medico:");
        };

        String especialidade = "";
        while (especialidade.equals("")) {
            especialidade = JOptionPane.showInputDialog("Informe a especialidade do Medico:");
        };

        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            Medico medico = new Medico(crm, pessoa, especialidade, dataCriacao, dataModificacao);

            medicoDAO.cadastrarMedico(medico);

            JOptionPane.showMessageDialog(null, "Cadastro concluido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro Medico\n" + e.getMessage());
        }
    }

    public void cadastrarMedicoP(Pessoa pessoa) {

        String crm = "";
        while (crm.equals("")) {
            crm = JOptionPane.showInputDialog("Informe o CRM do Medico:");
        };

        String especialidade = "";
        while (especialidade.equals("")) {
            especialidade = JOptionPane.showInputDialog("Informe a especialidade do Medico:");
        };

        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            Medico medico = new Medico(crm, pessoa, especialidade, dataCriacao, dataModificacao);

            medicoDAO.cadastrarMedico(medico);

            JOptionPane.showMessageDialog(null, "Cadastro concluido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro Medico\n" + e.getMessage());
        }
    }

    public void cadastrarUnidadeF(Pessoa p) {

        UnidadeF unidadeF = null;

        Franquia franquia = null;
        int opc;
        do {
            String[] options = {"Franquia Cadastrada", "Cadastrar uma Nova Franquia"};
            opc = JOptionPane.showOptionDialog(null, "Informe se a Franquia já está cadastrada no Sistema ou se deverá ser Cadastrada",
                    "Franquia -> UnidadeF",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    franquia = franquiaDAO.getFranquiaZ(p);
                    if (franquia == null) {
                        JOptionPane.showMessageDialog(null, "Não existe Franquia cadastrada apto para tal.");
                        opc = 1;
                    } else {
                        opc = 2;
                    }
                    break;
                }

                case 1 -> {
                    franquia = cadastrarFranquia(p);
                    franquia.setId(franquiaDAO.idFranquia());
                    opc = 2;
                    break;
                }

                default -> {
                    opc = 1;
                }
            }
        } while (opc != 2);

        Pessoa pessoa = null;
        opc = 0;
        do {
            String[] options = {"Pessoa Cadastrada", "Cadastrar uma Nova Pessoa"};
            opc = JOptionPane.showOptionDialog(null, "Informe se o responsável já está cadastrado no Sistema ou se deverá ser Cadastrado",
                    "Pessoa -> UnidadeF",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    pessoa = pessoaDAO.getPessoaTipo(4);
                    if (pessoa == null) {
                        JOptionPane.showMessageDialog(null, "Não existe pessoa cadastrada apto para tal.");
                        opc = 1;
                    } else {
                        opc = 2;
                    }
                    break;
                }

                case 1 -> {
                    pessoa = cadastrarPessoa();
                    pessoaDAO.alterarTipoUsuario(pessoa, 4);
                    opc = 2;
                    break;
                }

                default -> {
                    opc = 1;
                }
            }
        } while (opc != 2);

        String cidade = "";
        while (cidade.equals("")) {
            cidade = JOptionPane.showInputDialog("Informe o cidade da Unidade de Franquia:");
        };

        String endereco = "";
        while (endereco.equals("")) {
            endereco = JOptionPane.showInputDialog("Informe o endereço da Unidade de Franquia:");
        };

        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            unidadeF = new UnidadeF(franquia, cidade, endereco, pessoa, dataCriacao, dataModificacao);

            unidadeFDAO.cadastrarUnidadeF(unidadeF);
            
            JOptionPane.showMessageDialog(null, "Cadastro concluido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro UnidadeF\n" + e.getMessage());
        }
    }

    public void cadastrarAdministriativo() {

        try {
            Pessoa pessoa = cadastrarPessoa();
            pessoaDAO.alterarTipoUsuario(pessoa, 3);

            JOptionPane.showMessageDialog(null, "Cadastrado concluido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro Administrativo\n" + e.getMessage());
        }

    }

    public void cadastrarConsultaPaciente(Pessoa pessoa) {

        Consulta consulta = null;

        boolean y = true;
        String x = "";
        Date dia = null;
        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog("Informe o dia da Consulta:\n\nExemplo: 10/05/2023");
                }
//                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//                SimpleDateFormat formato2 = new SimpleDateFormat("yyyy-mm-dd");
//                Date fon = formato.parse(x);
//                String dale = formato2.format(fon);
//                dia = formato2.parse(dale);

                dia = new SimpleDateFormat("dd/MM/yyyy").parse(x);
                JOptionPane.showMessageDialog(null, dia);
//                JOptionPane.showMessageDialog(null, fon);
                y = false;
            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "MenuCadastrar - CadastrarConsulta Dia\n" + e.getMessage());
            }
        } while (y);

        y = true;
        x = "";
        LocalTime horario = null;
        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog("Informe o horario da Consulta:\n\nExemplo: 14:30");
                }
                horario = LocalTime.parse(x);
                y = false;
            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "MenuCadastrar - CadastrarConsulta Horario\n" + e.getMessage());
            }
        } while (y);

        String estado = "vazio";

        JOptionPane.showMessageDialog(null, "A seguir informe qual Médico realizará a consulta.");
        Medico medico = medicoDAO.getMedicoZ();

        double valor = 0.0;

        JOptionPane.showMessageDialog(null, "A seguir informe qual Unidade será realizada a consulta.");
        UnidadeF unidadeF = unidadeFDAO.getUnidadeFZ();

        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            consulta = new Consulta(dia, horario, estado, medico, pessoa, valor, unidadeF, dataCriacao, dataModificacao);

            consultaDAO.cadastrarConsulta(consulta);

            JOptionPane.showMessageDialog(null, "Consulta cadastrada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro Consulta Paciente\n" + e.getMessage());
        }
        
        consulta.setId(consultaDAO.idConsulta());
        
        cadastrarInfoConsulta(consulta);
        
    }

    public void cadastrarInfoConsulta(Consulta consulta){
        if(consulta == null){
            return;
        }
        
        String descrição = "consulta";
        
        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            InfoConsulta infoConsulta = new InfoConsulta(consulta, descrição, dataCriacao, dataModificacao);
            
            infoConsultaDAO.cadastrarInfoConsulta(infoConsulta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro InfoConsulta\n" + e.getMessage());
        }
        
    }
    
    public void cadastrarFinanceiroAdm() {

        FinanceiroAdm financeiroAdm;

        String tipoMovimento = "";
        int opc;
        String[] options = {"Entrada", "Saída"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Informe qual o tipo de movimento que será cadastrado:",
                    "Tipo Movimento -> FinanceiroAdm",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    tipoMovimento = "entrada";
                    opc = 2;
                    break;
                }

                case 1 -> {
                    tipoMovimento = "saida";
                    opc = 2;
                    break;
                }
                default -> {
                    opc = 1;
                }
            }
        } while (opc != 2);

        boolean y = true;
        String x = "";
        double valor = 0.0;
        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog("Informe o valor do movimento:");
                }
                valor = Double.parseDouble(x);
                if (valor < 0.0) {
                    JOptionPane.showMessageDialog(null, "Valor informado é negativo.\n\nInforme um novo valor.");
                    y = true;
                } else {
                    y = false;
                }
            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "MenuCadastrar - CadastrarFinanceiroAdm Valor\n" + e.getMessage());
            }
        } while (y);

        JOptionPane.showMessageDialog(null, "A seguir informe em qual Unidade ocorreu a movimentação:");
        UnidadeF unidadeF = unidadeFDAO.getUnidadeFZ();

        String descritivo = "";
        while (descritivo.equals("")) {
            descritivo = JOptionPane.showInputDialog("Informe o descritivo do movimento:");
        }

        Date dataCriacao = new Date();
        Date dataModificacao = null;

        try {
            financeiroAdm = new FinanceiroAdm(tipoMovimento, valor, unidadeF, descritivo, dataCriacao, dataModificacao);

            financeiroAdmDAO.cadastrarFinanceiroAdm(financeiroAdm);

            JOptionPane.showMessageDialog(null, "FinanceiroAdm cadastrado com sucesso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro FinanceiroAdm\n" + e.getMessage());
        }

    }
    
    public void cadastrarProcedimento(Medico medico) {
        Procedimento procedimento;
        
        String nome = "";
        while(nome.equals("")){
            nome = JOptionPane.showInputDialog("Informe o nome do procedimento:");
        }
        
        JOptionPane.showMessageDialog(null, "A seguir informe a partir de qual Consulta acontecerá o Procedimento:");
        Consulta consulta = consultaDAO.getConsultaZE(medico);
        
        boolean y = true;
        String x = "";
        Date dia = null;
        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog("Informe o dia do Procedimento:\n\nExemplo: 10/05/2023");
                }
                dia = new SimpleDateFormat("dd/MM/yyyy").parse(x);
                y = false;
            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "MenuCadastrar - CadastrarProcedimento Dia\n" + e.getMessage());
            }
        } while (y);

        y = true;
        x = "";
        LocalTime horario = null;
        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog("Informe o horario do Procedimento:\n\nExemplo: 14:30");
                }
                horario = LocalTime.parse(x);
                y = false;
            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "MenuCadastrar - CadastrarProcedimento Horario\n" + e.getMessage());
            }
        } while (y);
        
        String estado = "vazio";
        
        y = true;
        x = "";
        double valor = 0.0;
        do {
            try {
                x = "";
                while (x.equals("")) {
                    x = JOptionPane.showInputDialog("Informe o valor do procedimento:");
                }
                valor = Double.parseDouble(x);
                y = false;
            } catch (Exception e) {
                y = true;
                JOptionPane.showMessageDialog(null, "MenuCadastrar - CadastrarProcedimento Valor\n" + e.getMessage());
            }
        } while (y);
        
        String laudo = "";
        while(laudo.equals("")){
            laudo = JOptionPane.showInputDialog("Informe o laudo do procedimento:");
        }
        
        Date dataCriacao = new Date();
        Date dataModificacao = null;
        
        try {
            procedimento = new Procedimento(nome, consulta, dia, horario, estado, valor, laudo, dataCriacao, dataModificacao);

            procedimentoDAO.cadastrarProcedimento(procedimento);

            JOptionPane.showMessageDialog(null, "Procedimento cadastrado concluido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MenuCadastrar - Cadastro Procedimento\n" + e.getMessage());
        }
        
    }

}
