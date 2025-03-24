package space.gaabe.mobile.livroowl.model;

import java.util.Date;

/**
 * Classe que representa uma avaliação, com um comentário, uma quantidade de estrelas, like e data
 */
public class Avaliacao {
    private String comentario;
    private float estrelas;
    private boolean isLike;
    private Date dataAvaliado;


    public Avaliacao() {}
    public void setComentario(String comentario) {
        String comentarioLimpo = comentario.trim();
        if (comentarioLimpo.length() > 0) {
            this.comentario = comentarioLimpo;
        } else {
            this.comentario = "";
        }
    }
    public void setEstrelas(float estrelas) {
        if (estrelas < 0.5) {
            estrelas = 0.5f;
        } else if (estrelas > 5) {
            estrelas = 5;
        }
        this.estrelas = estrelas;
    }
    public void setLike(boolean like) {
        isLike = like;
    }
    public void setDataAvaliado(Date dataAvaliado) {
        this.dataAvaliado = dataAvaliado;
    }
    public float getEstrelas() {
        return estrelas;
    }
    public String getComentario() {
        return comentario;
    }
    public boolean isLike() {
        return isLike;
    }
    public Date getDataAvaliado() {
        return dataAvaliado;
    }

}
