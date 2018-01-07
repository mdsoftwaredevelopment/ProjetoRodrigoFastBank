package morgado.mdsoftware.projetorodrigo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {
    //   private MaterialDialog mMaterialDialog;
    private String VERSAO = "0.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //     TextView textView = (TextView)findViewById(R.id.tvCreditos);
        //    textView.setText("Desenvolvido por M&D Software");

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("versao");
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String x = d.getValue(String.class);

                    if (!(VERSAO.equals(x))) {
                   //     showUpdateAppDialog();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void irParaLogin(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));

    }

    public void irParaCadastro(View view) {
        startActivity(new Intent(getApplicationContext(), RegistrarUsuario.class));

    }

/*
    public void showUpdateAppDialog(){

        mMaterialDialog = new MaterialDialog(this)
                .setTitle( "Atualize o MonitoriaCEFET")
                .setMessage("Nova versão disponível na PlayStore. O aplicativo está chegando a sua perfeição com novas funções, correção de bugs, entre outros detalhes.")
                .setCanceledOnTouchOutside(false)
                .setPositiveButton( "Atualizar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String packageName = getPackageName();
                        Intent intent;

                        try {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                            startActivity( intent );
                        }
                        catch (android.content.ActivityNotFoundException e) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                            startActivity( intent );
                        }
                    }
                });


        mMaterialDialog.show();
    }
}
*/

}