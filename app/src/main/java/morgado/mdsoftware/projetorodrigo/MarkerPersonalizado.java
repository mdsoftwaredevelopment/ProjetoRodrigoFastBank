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
    int n1G, n2G, n3G;

    public int getN1G() {
        return n1G;
    }

    public void setN1G(int n1G) {
        this.n1G = n1G;
    }

    public int getN2G() {
        return n2G;
    }

    public void setN2G(int n2G) {
        this.n2G = n2G;
    }

    public int getN3G() {
        return n3G;
    }

    public void setN3G(int n3G) {
        this.n3G = n3G;
    }

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
