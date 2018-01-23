package morgado.mdsoftware.projetorodrigo;

/**
 * Created by MoorG on 16/01/2018.
 */

public class MarkerPersonalizado {

    String id;
    String titulo;
    String descricao;
    String latitude, longetude;
    String endereçoBancoProxUm, endereçoBancoProxDois;
    String idBancoProxUm, idBancoProxDois;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdBancoProxUm() {
        return idBancoProxUm;
    }

    public void setIdBancoProxUm(String idBancoProxUm) {
        this.idBancoProxUm = idBancoProxUm;
    }

    public String getIdBancoProxDois() {
        return idBancoProxDois;
    }

    public void setIdBancoProxDois(String idBancoProxDois) {
        this.idBancoProxDois = idBancoProxDois;
    }

    int valor;

    public String getEndereçoBancoProxUm() {
        return endereçoBancoProxUm;
    }

    public void setEndereçoBancoProxUm(String endereçoBancoProxUm) {
        this.endereçoBancoProxUm = endereçoBancoProxUm;
    }

    public String getEndereçoBancoProxDois() {
        return endereçoBancoProxDois;
    }

    public void setEndereçoBancoProxDois(String endereçoBancoProxDois) {
        this.endereçoBancoProxDois = endereçoBancoProxDois;
    }

    public int getValor() {
        return valor;

    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongetude() {
        return longetude;
    }

    public void setLongetude(String longetude) {
        this.longetude = longetude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
