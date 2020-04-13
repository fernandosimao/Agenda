package com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.agenda.R;
import com.alura.agenda.model.Aluno;
import com.alura.agenda.ui.ListaAlunosView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Lista de Alunos";
    private String TAG = "Aluno";
    private final ListaAlunosView listaAlunosView = new ListaAlunosView(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(this, "Fernando Simão", Toast.LENGTH_LONG).show();
        //        TextView aluno = new TextView(this);
        //        aluno.setText("Fernando Simão");
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR);
        configuraFabNovoAluno();
        configuraLista();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_alunos_menu_remover) {
            listaAlunosView.confirmaRemocao(item);

        }

        return super.onContextItemSelected(item);
    }


    private void configuraFabNovoAluno() {
        //linkando o botão criado via xml/design ao código java
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(this,
                FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaAlunosView.atualizaAlunos();
        //o  configuraLista(); foi enviado para onCreate, já que é custoso toda vez que voltarmos para a lista ter
        //que refazer o trabalho inteiro contido nele. Por outro lado através do adapter, informamos que ele deve limpar
        //os dados pois virá coisa nova (clear). Agora adicione novamente todos os dados contidos
        // na fonte de dados segura (addAll - dao.todos)

    }


    private void configuraLista() {
        //linkando a listview criada via xml/design ao código java
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
         /*31/12/2019
            Cria-se primeiramente uma ListView no layout activity_main e faz-se o link com ele através do
            findViewById (o id que setamos é o activity_main_Lista_de_Alunos). A ListView não tem
            um método setText, então é necessário usar um Adapter para linkar os dados da lista de
            strings criadas acima dentro da lista de views.
            A Classe ArrayAdapter facilita esse processo. Nesse caso é utilizado um layout padrão
            que já vem na biblioteca android, acessíveis através do comando android.R.layout.
            A simple_expandable_List_item_1 faz esse trabalho de dinamicamente alocar elementos
            em um layout dinamicamente expansível
        */
        listaAlunosView.configuraAdapter(listaDeAlunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }


    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(position);
                Log.i("idAluno", String.valueOf(alunoEscolhido.getId()));
                Log.i(TAG, "onItemClick: " + alunoEscolhido);
                abreFormularioModoEditaAluno(alunoEscolhido);

            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno); //putExtra envia algo para outra activity, no nosso caso um Aluno.
        // Necessário implementar Serializable em Alunos para que os dados possam ser transformados em bytes antes do envio para outra activity

        startActivity(vaiParaFormularioActivity);
    }


}
