//como o código era e como ficou após a refatoração
// https://github.com/alura-cursos/fundamentos-android-parte-1/commit/20572e3c6588ad2f5d5cf1f18206700651892fe

package com.alura.agenda.ui.activity;
//Ctrl Alt O - remove os imports que não estão sendo utilizados e adiciona os faltantes
//Ctrl Alt l - alinha as identações do código

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.alura.agenda.R;
import com.alura.agenda.dao.AlunoDAO;
import com.alura.agenda.model.Aluno;
import static com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {
    //AppCompatActivity - Entrega uma AppBar e também compatibilidade com versões anteriores

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();
    private Aluno aluno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        //setTitle("Novo aluno"); o atalho Crtl Alt C transforma em uma CONSTANTE, que o é desejável
        //para o título da APP bar

//O atalho Ctrl Alt F cria variáveis para ajudar a
// extrair o comportamento das linhas abaixo. Veja o exemplo do campoEmail que estava
// assim: final EditText campoEmail = findViewById(R.id.activity_formulario_aluno_email);
//e ficou assim campoEmail = findViewById(R.id.activity_formulario_aluno_email); Agora sim
// é possível extrair essas ações para um método separado, através do atalho Ctrl Alt M - ver
//comentário do método onClick abaixo
        inicializacaoDosCampos();
        //configuraBotaoSalvar();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }

//    private void configuraBotaoSalvar() {
//        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
//        botaoSalvar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //O atalho Crtl Alt M serve para executar a técnica de extração, ou seja,
//                // colocar o código que estava abaixo, que é complexo de ler, em uma nova
//                // classe, substituindo esse conteúdo por uma chamada de método, muito mais legível
//
//                finalizaFormulario();
//            }
//        });
//    }

    private void finalizaFormulario() {
        preencheAluno();
        if(aluno.temIdValido()){
            dao.edita(aluno);
        } else {
            dao.salva(aluno);
        }
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

       aluno.setNome(nome);
       aluno.setTelefone(telefone);
       aluno.setEmail(email);

    }
}
