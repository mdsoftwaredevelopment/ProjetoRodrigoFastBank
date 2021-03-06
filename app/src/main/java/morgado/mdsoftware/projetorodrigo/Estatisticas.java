package morgado.mdsoftware.projetorodrigo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class Estatisticas extends AppCompatActivity {

    String valor, statusString, banco1, banco2,  banco1Valor, banco2Valor, agencia;
    WebView wvGrafico;
    String strURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        TextView tempoEspera = (TextView) findViewById(R.id.tempoEspera);
        TextView bancoTV1 = (TextView)findViewById(R.id.enderecoBanco1);
        TextView bancoTV2 = (TextView)findViewById(R.id.enderecoBanco2);
        TextView status = (TextView)findViewById(R.id.status);
        TextView valorBanco2 = (TextView)findViewById(R.id.valorBanco2);
        TextView valorBanco1 = (TextView)findViewById(R.id.valorBanco1);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {

                valor = params.getString("Valor");
                statusString = params.getString("status");
                banco1 = params.getString("prox1Endereco");
                banco2 = params.getString("prox2Endereco");
                banco1Valor = params.getString("proxValor1");
                banco2Valor = params.getString("proxValor2");
                agencia = params.getString("agencia");

                Log.i("bancoEst",banco1 + " " + banco2);

                tempoEspera.setText(valor + " Minutos");
                status.setText("Relatório do tempo de espera total: \n"+agencia );
                bancoTV1.setText(banco1);
                bancoTV2.setText(banco2);
                valorBanco1.setText(banco1Valor + " minutos");
                valorBanco2.setText(banco2Valor + " minutos \n \n");

                int n1 =params.getInt("n1G");
                int n2 =params.getInt("n2G");
                int n3 =params.getInt("n3G");


                strURL = "https://chart.googleapis.com/chart?" +
                        "cht=bvg&" + //define o tipo do gráfico "linha"
                        "chxt=x,y&" + //imprime os valores dos eixos X, Y
                        "chs=300x300&" + //define o tamanho da imagem
                        "chd=t:"+n1+",0,0,"+n2+",0,0,"+n3+"&" + //valor de cada coluna do gráfico
                        "chl=15m|||16-29m|||30m&" + //rótulo para cada coluna
                        "chdl=Baixo|||Médio|||Alto&" + //legenda do gráfico
                        "chxr=1,0,50&" + //define o valor de início e fim do eixo
                        "chds=0,50&" + //define o valor de escala dos dados
                        "chg=0,5,0,0&" + //desenha linha horizontal na grade
                        "chco=009933|ffffff|ffffff|ff6600|ffffff|ffffff|ff0000&" +//cor da linha do gráfico
                        "chtt=Numero de cliente / tempo de espera&"  //cabeçalho do gráfico
                ;


                wvGrafico = (WebView)findViewById(R.id.wvGrafico);
                //   wvGrafico.setBackgroundColor(Color.parseColor("#fff200"));
                wvGrafico.loadUrl(strURL);


            }
        }
    }



}
