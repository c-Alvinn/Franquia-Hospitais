package controller;

import model.*;

public class PessoaController {
    
    private PessoaDAO dao = new PessoaDAO();
    
    public Pessoa autenticacaoUsuario(String login, String senha){
        return this.dao.autenticacaoUsuario(login, senha);
    }
    
    public void alterarPessoaZ(Pessoa pessoa){
        this.dao.alterarPessoaZ(pessoa);
    }
    
    public void alterarTipoUsuario(Pessoa pessoa, int tipo){
        this.dao.alterarTipoUsuario(pessoa, tipo);
    }
    
    public void alterarPessoaMenosTipo(int tipo){
        this.dao.alterarPessoaMenosTipo(tipo);
    }
    
    public Pessoa getPessoaTipo(int tipo){
        return this.dao.getPessoaTipo(tipo);
    }
}
