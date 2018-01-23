package morgado.mdsoftware.projetorodrigo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class RegistrarUsuario extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Set set = new HashSet();
    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        mAuth = FirebaseAuth.getInstance();

        Button buscar = (Button) findViewById(R.id.butRegistrarRegistrar);
        progressDialog = new ProgressDialog(RegistrarUsuario.this);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ((EditText) findViewById(R.id.emailCadastro)).getText().toString();
                String senha = ((EditText) findViewById(R.id.senhaCadastro)).getText().toString();
                String confirm = ((EditText) findViewById(R.id.confirmSenhaRegistrarSenha)).getText().toString();
                cadastro(email, senha, confirm);
            }
        });

    }


    public void cadastro(final String email, final String senha, final String confirmarSenha) {


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email não digitado",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Senha não digitada", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmarSenha)) {
            Toast.makeText(this, "Confirme sua senha", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(senha.equals(confirmarSenha))){
            Toast.makeText(this, "Suas senhas estão diferentes", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Criando sua conta...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(RegistrarUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if the task is successfull
                if (task.isSuccessful()) {

                    tipoConta();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrarUsuario.this,"Erro ao cadastrar usuário",Toast.LENGTH_SHORT).show();

                }
            }

        });

        /*
        if( conexoes.UserCadastro(RegistrarUsuario.this,email,senha)){
            progressDialog.dismiss();
            Toast.makeText(RegistrarUsuario.this,"Cadastrado com sucesso",Toast.LENGTH_SHORT).show();
            // ir para menu, conexão com sucesso
        }else{
            progressDialog.dismiss();
            Toast.makeText(RegistrarUsuario.this,"Erro ao cadastrar usuário",Toast.LENGTH_SHORT).show();
        }
        */
    }

    public void tipoConta(){

        // Usuários -> Comum/Administrador/ -> usuárioID
        final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child("Comum").child(mAuth.getCurrentUser().getUid());
        Usuario usuario = new Usuario();
        usuario.setId(mAuth.getCurrentUser().getUid());
        regMonitoria.setValue(usuario);
        conferirConta();


    }

    public void conferirConta(){

        final ArrayList<String> id = new ArrayList<String>();
        final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child("Comum");
        regMonitoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    // erro tratado
                }

                int cont = 0;
                for (Object i : set) {
                    String palavra = (String) i;
                    Log.i("Makers",palavra);
                    if (palavra.equals(mAuth.getCurrentUser().getUid())){

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Tipo","comum");
                        intent.putExtras(bundle);
                        progressDialog.dismiss();
                        Toast.makeText(RegistrarUsuario.this,"Cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }else{
                        cont = cont+1;
                        if (cont==set.size()){

                            final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child("Comum");

                                regMonitoria.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            map = (Map<String, Object>) dataSnapshot.getValue();
                                        }
                                        try {
                                            set = map.keySet();
                                        }catch (NullPointerException n){
                                            // erro tratado
                                        }

                                        int cont = 0;
                                        for (Object i : set) {
                                            String palavra = (String) i;
                                            Log.i("Makers", palavra);
                                            if (palavra.equals(mAuth.getCurrentUser().getUid())) {

                                                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("Tipo", "Admim");
                                                intent.putExtras(bundle);
                                                progressDialog.dismiss();

                                                startActivity(intent);

                                            }
                                            else{
                                                cont = cont+1;
                                                if (cont==set.size()){
                                                    Toast.makeText(RegistrarUsuario.this, "Gerente", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        }



                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    public void conferirContaAdmin(){

        final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child("Admin");
        regMonitoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Log.i("SCRIPT", "ENTROU NO FOR");

                    String x = d.getValue(String.class);
                    if (x.equals(mAuth.getCurrentUser().getUid())){

                        Log.i("Usuário Admin", mAuth.getCurrentUser().getUid());
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Tipo","admin");
                        intent.putExtras(bundle);
                        progressDialog.dismiss();
                        Toast.makeText(RegistrarUsuario.this,"Cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}