package com.alura.agenda;

import android.app.Application;

import com.alura.agenda.dao.AlunoDAO;
import com.alura.agenda.model.Aluno;


@SuppressWarnings("WeakerAccess")
public class AgendaApplication extends Application {
//colocar no Manifests, no atributo name

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AlunoDAO dao = new AlunoDAO();
        dao.salva(new Aluno("Alex", "11-2222-4444", "alex@gmail.com"));
        dao.salva(new Aluno("Fran", "11-2222-4444", "fran@gmail.com"));
    }
}
