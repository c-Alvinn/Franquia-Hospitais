package view;

import javax.swing.JOptionPane;
import model.*;
import controller.*;

public class Menu {

    ConsultaController consultaC = new ConsultaController();
    FinanceiroAdmController financeiroAdmC = new FinanceiroAdmController();
    FinanceiroMedController financeiroMedC = new FinanceiroMedController();
    FranquiaController franquiaC =  new FranquiaController();
    InfoConsultaController infoConsultaC = new InfoConsultaController();
    MedicoController medicoC = new MedicoController();
    PessoaController pessoaC = new PessoaController();
    ProcedimentoController procedimentoC = new ProcedimentoController();
    UnidadeFController unidadeFC = new UnidadeFController();
    
//    ConsultaDAO consultaDAO = new ConsultaDAO();
//    FinanceiroAdmDAO financeiroAdmDAO = new FinanceiroAdmDAO();
//    FinanceiroMedDAO financeiroMedDAO = new FinanceiroMedDAO();
//    FranquiaDAO franquiaDAO = new FranquiaDAO();
//    InfoConsultaDAO infoConsultaDAO = new InfoConsultaDAO();
//    MedicoDAO medicoDAO = new MedicoDAO();
//    PessoaDAO pessoaDAO = new PessoaDAO();
//    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();
//    UnidadeFDAO unidadeFDAO = new UnidadeFDAO();
    MenuCadastrar menuCadastrar = new MenuCadastrar();
//    Calendario calendario = new Calendario();

    public void inicial() {

        int opc;
        String[] options = {"Entrar no Sistema", "Cadastrar (Paciente)", "Sair do Programa"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Menu Inicial", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    login();
                    break;
                }

                case 1 -> {
                    menuCadastrar.cadastrarPessoa();
                    break;
                }

                default -> {
                    System.exit(0);
                }
            }
        } while (opc != 2);
    }

    public void login() {
        Pessoa pessoa = null;
        try {
            String login = "";
            while (login.equals("")) {
                login = JOptionPane.showInputDialog("Usuário");
            };

            String senha = "";
            while (senha.equals("")) {
                senha = JOptionPane.showInputDialog("Senha");
            };

            pessoa = pessoaC.autenticacaoUsuario(login, senha);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Menu login\n" + e.getMessage());
        }

        if(pessoa==null){
            return;
        }
        
        switch (pessoa.getTipoUsuario()) {
            case 1 -> {
                menu1(pessoa);
                break;
            }
            case 2 -> {
                menu2(pessoa);
                break;
            }
            case 3 -> {
                menu3(pessoa);
                break;
            }
            case 4 -> {
                menu4(pessoa);
                break;
            }
            case 5 -> {
                menu5(pessoa);
                break;
            }
            case 6 -> {
                menu6(pessoa);
                break;
            }
        }
    }

    public void menu1(Pessoa pessoa) {
        int opc;
        String[] options = {"Solicita Consulta", "Visualiza Consulta", "Visualiza Procedimento", "Altera seus dados", "Retornar Menu anterior"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Bem vindo ao Sistema\nMenu Paciente", "Menu Paciente",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    menuCadastrar.cadastrarConsultaPaciente(pessoa);
                    break;
                }

                case 1 -> {
                    infoConsultaC.mostrarInfoConsultaPaciente(pessoa);
                    break;
                }

                case 2 -> {
                    procedimentoC.mostrarProcedimentoPaciente(pessoa);
                    break;
                }

                case 3 -> {
                    pessoaC.alterarPessoaZ(pessoa);
                    break;
                }

                default -> {
                    inicial();
                }
            }

        } while (opc != 4);
    }

    public void menu2(Pessoa pessoa) {
        Medico medico = medicoC.getMedicoPessoa(pessoa);

        int opc;
        String[] options = {"Agenda de Consultas", "Agenda de Procedimentos", "Alterar Dados Pessoais", "Retornar ao Inicio"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Bem vindo ao Sistema", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    String agenda = consultaC.agendaConsultasMedico(medico);
                    int o;
                    String[] opt = {"Agendar Consulta", "Realiza Consulta", "Cancelar Consulta", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, agenda, "Agenda Consultas",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                consultaC.agendarConsulta(medico);
                                o = 3;
                                break;
                            }

                            case 1 -> {
                                consultaC.realizarConsulta(medico);
                                o = 3;
                                break;
                            }

                            case 2 -> {
                                consultaC.cancelarConsulta(medico);
                                o = 3;
                                break;
                            }
                            default -> {
                                o = 3;
                                break;
                            }
                        }
                    } while (o != 3);
                    break;
                }

                case 1 -> {
                    String agenda = procedimentoC.agendaProcedimentosMedico(medico);
                    int o;
                    String[] opt = {"Criar Procedimento", "Agendar Procedimento", "Realizar Procedimento", "Cancelar Procedimento", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, agenda, "Agenda Procedimento",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarProcedimento(medico);
                                o = 4;
                                break;
                            }

                            case 1 -> {
                                procedimentoC.agendarProcedimento(medico);
                                o = 4;
                                break;
                            }

                            case 2 -> {
                                procedimentoC.realizarProcedimento(medico);
                                o = 4;
                                break;
                            }

                            case 3 -> {
                                procedimentoC.cancelarProcedimento(medico);
                                o = 4;
                                break;
                            }
                        }
                    } while (o != 4);
                    break;
                }

                case 2 -> {
                    pessoaC.alterarPessoaZ(pessoa);
                    break;
                }
                default -> {
                    inicial();
                }
            }

        } while (opc != 3);
    }

    public void menu3(Pessoa pessoa) {
        int opc;
        String[] options = {"Adicionar FinanceiroAdm", "Gerar Relatório Geral Adm PDF", "Gerar Relatório Geral Med PDF", "Alterar Dados Pessoais", "Retornar ao Inicio"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Bem vindo ao Sistema", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    menuCadastrar.cadastrarFinanceiroAdm();
                    break;
                }

                case 1 -> {
                    financeiroAdmC.relatorioFinanceiroAdm();
                    break;
                }

                case 2 -> {
                    financeiroMedC.relatorioFinanceiroMed();
                    break;
                }

                case 3 -> {
                    pessoaC.alterarPessoaZ(pessoa);
                    break;
                }
                default -> {
                    inicial();
                }
            }

        } while (opc != 4);
    }

    public void menu4(Pessoa pessoa) {
        UnidadeF unidadeF = unidadeFC.getUnidadeFUserId(pessoa.getId());
        int opc;
        String[] options = {"Menu Usuario", "Menu Medico", "Menu Financeiro", "Retornar ao Inicio"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Bem vindo ao Sistema", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    int o;
                    String[] opt = {"Cadastrar Pessoa", "Editar Pessoa", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Usuário", "Menu Usuário",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                Pessoa p = menuCadastrar.cadastrarPessoa();
                                int oX;
                                String[] optX = {"Paciente", "Medico", "Administrativo", "Dono de Unidade de Franquia"};
                                do {
                                    oX = JOptionPane.showOptionDialog(null, "Informe qual o tipo do usuário criado", "Menu Usuário",
                                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                                    switch (oX) {
                                        case 0 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 1 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            menuCadastrar.cadastrarMedicoP(pessoa);
                                            break;
                                        }
                                        case 2 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 3 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        default -> {
                                            oX = 4;
                                            break;
                                        }

                                    }
                                } while (oX != 4);
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                pessoaC.alterarPessoaMenosTipo(pessoa.getTipoUsuario());
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 1 -> {
                    int o;
                    String[] opt = {"Cadastrar Médico", "Editar Médico", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Médico", "Menu Médico",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarMedico();
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                medicoC.alterarMedico();
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 2 -> {
                    int o;
                    String[] opt = {"Criar Financeiro ADM", "Gerar Relatorio ADM Unidade", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Financeiro", "Menu Financeiro",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarFinanceiroAdm();
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                financeiroAdmC.relatorioFinanceiroAdmUnidade(unidadeF);
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }
                default -> {
                    inicial();
                }
            }

        } while (opc != 3);
    }

    public void menu5(Pessoa pessoa) {
        Franquia franquia = franquiaC.getFranquiaUserId(pessoa.getId());
        int opc;
        String[] options = {"Menu Usuário", "Menu Médico", "Menu Financeiro", "Menu Franquia", "Retornar ao Inicio"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Bem vindo ao Sistema", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    int o;
                    String[] opt = {"Cadastrar Pessoa", "Editar Pessoa", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Usuário", "Menu Usuário",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                Pessoa p = menuCadastrar.cadastrarPessoa();
                                int oX;
                                String[] optX = {"Paciente", "Medico", "Administrativo", "Dono de Unidade de Franquia", "Dono de Franquia"};
                                do {
                                    oX = JOptionPane.showOptionDialog(null, "Informe qual o tipo do usuário criado", "Menu Usuário",
                                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                                    switch (oX) {
                                        case 0 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 1 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            menuCadastrar.cadastrarMedicoP(pessoa);
                                            break;
                                        }
                                        case 2 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 3 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 4 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        default -> {
                                            oX = 4;
                                            break;
                                        }

                                    }
                                } while (oX != 4);
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                pessoaC.alterarPessoaMenosTipo(pessoa.getTipoUsuario());
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 1 -> {
                    int o;
                    String[] opt = {"Cadastrar Médico", "Editar Médico", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Médico", "Menu Médico",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarMedico();
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                medicoC.alterarMedico();
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 2 -> {
                    int o;
                    String[] opt = {"Criar Financeiro ADM", "Gerar Relatorio ADM Franquia", "Gerar Relatorio Med Franquia", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Financeiro", "Menu Financeiro",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarFinanceiroAdm();
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                financeiroAdmC.relatorioFinanceiroAdmFranquia(franquia);
                                o = 2;
                                break;
                            }

                            case 2 -> {
                                financeiroMedC.relatorioFinanceiroMedFranquia(franquia);
                            }
                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 3 -> {
                    int o;
                    String[] opt = {"Criar Franquia", "Alterar Minha(s) Franquia(s)", "Criar Unidade de Franquia", "Alterar Unidades de Franquias", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Franquia", "Menu Franquia",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarFranquia(pessoa);
                                o = 4;
                                break;
                            }

                            case 1 -> {
                                franquiaC.alterarFranquia(pessoa);
                                o = 4;
                                break;
                            }

                            case 2 -> {
                                menuCadastrar.cadastrarUnidadeF(pessoa);
                                o = 4;
                                break;
                            }

                            case 3 -> {
                                unidadeFC.alterarUnidadeF(franquia);
                                o = 4;
                                break;
                            }
                            default -> {
                                o = 4;
                                break;
                            }
                        }
                    } while (o != 4);
                    break;
                }
                default -> {
                    inicial();
                }
            }

        } while (opc != 4);
    }

    public void menu6(Pessoa pessoa) {
        int opc;
        String[] options = {"Menu Usuário", "Menu Médico", "Menu Consulta", "Menu Procedimento", "Menu Financeiro", "Menu Franquia", "Retornar ao Inicio"};
        do {
            opc = JOptionPane.showOptionDialog(null, "Bem vindo ao Sistema", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (opc) {
                case 0 -> {
                    int o;
                    String[] opt = {"Cadastrar Pessoa", "Editar Pessoa", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Usuário", "Menu Usuário",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                Pessoa p = menuCadastrar.cadastrarPessoa();
                                int oX;
                                String[] optX = {"Paciente", "Medico", "Administrativo", "Dono de Unidade de Franquia", "Dono de Franquia"};
                                do {
                                    oX = JOptionPane.showOptionDialog(null, "Informe qual o tipo do usuário criado", "Menu Usuário",
                                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, optX, optX[0]);
                                    switch (oX) {
                                        case 0 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 1 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            menuCadastrar.cadastrarMedicoP(p);
                                            break;
                                        }
                                        case 2 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 3 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        case 4 -> {
                                            pessoaC.alterarTipoUsuario(p, oX + 1);
                                            oX = 4;
                                            break;
                                        }
                                        default -> {
                                            oX = 4;
                                            break;
                                        }

                                    }
                                } while (oX != 4);
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                pessoaC.alterarPessoaMenosTipo(pessoa.getTipoUsuario());
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 1 -> {
                    int o;
                    String[] opt = {"Cadastrar Médico", "Editar Médico", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Médico", "Menu Médico",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarMedico();
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                medicoC.alterarMedico();
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 2 -> {
                    int o;
                    String[] opt = {"Visualizar Todas as Consultas", "Entrar no Menu Médico", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Consulta", "Menu Consulta",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                consultaC.mostrarConsultas();
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                JOptionPane.showMessageDialog(null, "A seguir informe qual medico utilizara o menu");
                                menu2(pessoaC.getPessoaTipo(2));
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 3 -> {
                    int o;
                    String[] opt = {"Visualizar Todas os Procedimentos", "Entrar no Menu Médico", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Procedimento", "Menu Procedimento",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                procedimentoC.mostrarTodosProcedimentos();
                                o = 2;
                                break;
                            }

                            case 1 -> {
                                JOptionPane.showMessageDialog(null, "A seguir informe qual medico utilizara o menu");
                                menu2(pessoaC.getPessoaTipo(2));
                                o = 2;
                                break;
                            }

                            default -> {
                                o = 2;
                                break;
                            }
                        }
                    } while (o != 2);
                    break;
                }

                case 4 -> {
                    int o;
                    String[] opt = {"Criar Financeiro ADM", "Relatorio ADM\n Franquia", "Relatorio Med\n Franquia", "Relatorio ADM\n Unidade", "Relatorio Geral\n ADM", "Relatorio Geral\n Med", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Financeiro", "Menu Financeiro",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarFinanceiroAdm();
                                o = 6;
                                break;
                            }

                            case 1 -> {
                                financeiroAdmC.relatorioFinanceiroAdmFranquia(franquiaC.getFranquiaZAdmin());
                                o = 6;
                                break;
                            }

                            case 2 -> {
                                financeiroMedC.relatorioFinanceiroMedFranquia(franquiaC.getFranquiaZAdmin());
                                o = 6;
                                break;
                            }
                            
                            case 3 -> {
                                financeiroAdmC.relatorioFinanceiroAdmUnidade(unidadeFC.getUnidadeFZ());
                                o = 6;
                                break;
                            }
                            
                            case 4 -> {
                                financeiroAdmC.relatorioFinanceiroAdm();
                                o = 6;
                                break;
                            }
                            
                            case 5 -> {
                                financeiroMedC.relatorioFinanceiroMed();
                                o = 6;
                                break;
                            }
                            default -> {
                                o = 6;
                                break;
                            }
                        }
                    } while (o != 6);
                    break;
                }

                case 5 -> {
                    int o;
                    String[] opt = {"Criar Franquia", "Alterar Minha(s) Franquia(s)", "Criar Unidade de Franquia", "Alterar Unidades de Franquias", "Voltar Menu"};
                    do {
                        o = JOptionPane.showOptionDialog(null, "Menu Franquia", "Menu Franquia",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
                        switch (o) {
                            case 0 -> {
                                menuCadastrar.cadastrarFranquia(pessoaC.getPessoaTipo(5));
                                o = 4;
                                break;
                            }

                            case 1 -> {
                                franquiaC.alterarFranquia(pessoaC.getPessoaTipo(5));
                                o = 4;
                                break;
                            }

                            case 2 -> {
                                menuCadastrar.cadastrarUnidadeF(pessoaC.getPessoaTipo(5));
                                o = 4;
                                break;
                            }

                            case 3 -> {
                                unidadeFC.alterarUnidadeFAdmin();
                                o = 4;
                                break;
                            }
                            default -> {
                                o = 4;
                                break;
                            }
                        }
                    } while (o != 4);
                    break;
                }

                default -> {
                    inicial();
                }
            }

        } while (opc != 6);
    }
}
