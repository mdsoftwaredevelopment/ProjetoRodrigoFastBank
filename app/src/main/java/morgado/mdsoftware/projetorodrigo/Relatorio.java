package morgado.mdsoftware.projetorodrigo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Relatorio extends AppCompatActivity {
    WebView wvGrafico;
    String strURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        int n1 =(int) Math.round(Math.random() * 20)*2;;


        int n2 = (int) Math.round(Math.random() * 20)*2;


        int n3 = (int) Math.round(Math.random() * 20)*2;;


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
