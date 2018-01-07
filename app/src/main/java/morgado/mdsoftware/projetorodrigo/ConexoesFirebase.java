package morgado.mdsoftware.projetorodrigo;

import android.app.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by MoorG on 28/12/2017.
 */

public class ConexoesFirebase {
    private FirebaseAuth mAuth;
    private boolean aux;

    public boolean getAux() {
        return aux;
    }

    public void setTrueAux() {
        aux = true;
    }

    public void setFalseAux() {
        aux = false;
    }

    public boolean UserLogin(Activity activity, String email, String senha) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if the task is successfull
                if (task.isSuccessful()) {
                    setTrueAux();
                } else {

                    setFalseAux();
                }

            }

        });
        return getAux();
    }

    public boolean UserCadastro(Activity activity, String email, String senha){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                         //   progressDialog.dismiss();
                            //Toast.makeText(C,"Cadastrado com sucesso",Toast.LENGTH_SHORT).show();

                        } else {
                            setFalseAux();
                            Log.i("Return", "Fora do IF"+getAux());

                        }
                    }
                });
        Log.i("Return", "Return final "+getAux());
        return getAux();
    }


    /*
    public void InserirDados(Object object,){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    }
    */

}
