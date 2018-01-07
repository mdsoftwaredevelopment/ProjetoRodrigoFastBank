package morgado.mdsoftware.projetorodrigo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by MoorG on 23/10/2017.
 */

public class Tarefa {

    private LatLng latLng;
    private String versão;
    private String endereço,numero,localidade,areaAdministrativa, pais, lat,lng;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getAreaAdministrativa() {
        return areaAdministrativa;
    }

    public void setAreaAdministrativa(String areaAdministrativa) {
        this.areaAdministrativa = areaAdministrativa;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getVersão() {
        return versão;
    }

    public void setVersão(String versão) {
        this.versão = versão;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}


