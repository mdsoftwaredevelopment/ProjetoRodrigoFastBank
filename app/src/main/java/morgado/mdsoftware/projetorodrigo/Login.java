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
import android.widget.RelativeLayout;
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

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


/*
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }
*/

        Button buscar = (Button)findViewById(R.id.butLoginLogin);
        progressDialog = new ProgressDialog(Login.this);



        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.emailLogin)).getText().toString();
                String senha = ((EditText)findViewById(R.id.senhaLogin)).getText().toString();
                loguin(email, senha);
            }
        });


    }

    public void loguin(String email, String senha){



        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logando...");
        progressDialog.show();



        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if the task is successfull
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Login com sucesso",Toast.LENGTH_SHORT).show();
        

                    conferirConta();

                    finish();

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Email ou senha incorretos",Toast.LENGTH_SHORT).show();
                }

            }

        });

        /*
       if( conexoes.UserLogin(Login.this,email,senha)){
           progressDialog.dismiss();
           Toast.makeText(Login.this,"Login com sucesso",Toast.LENGTH_SHORT).show();
           // ir para menu, conexão com sucesso
       }else{
           progressDialog.dismiss();
           Toast.makeText(Login.this,"Email ou senha incorretos",Toast.LENGTH_SHORT).show();
       }
        */
    }

    public void conferirConta(){

        final ArrayList<String> id = new ArrayList<String>();
        final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child("Comum");
        regMonitoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Log.i("SCRIPT", "ENTROU NO FOR");

                    String x = d.getValue(String.class);
                    if (x.equals(mAuth.getCurrentUser().getUid())){

                       Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Tipo","comum");
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }else{
                        conferirContaAdmin();
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
                        startActivity(intent);

                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
    }
}
