package space.gaabe.mobile.livroowl.model;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que representa uma avaliação, com um comentário, uma quantidade de estrelas, like e data
 */
public class Avaliacao {
    private String nomeLivro;

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    private String comentario;
    private float estrelas;
    private boolean isLike;
    private Date dataAvaliado;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public Avaliacao() {
        this.setComentario("");
        this.setEstrelas(0);
        this.setLike(false);
        this.setDataAvaliado(new Date());
    }

    public Avaliacao(JSONObject avaliacaoJSON) {
        try {
            this.setComentario(avaliacaoJSON.getString("comentario"));
            this.setLike(avaliacaoJSON.getBoolean("liked"));
            this.setEstrelas(avaliacaoJSON.getInt("estrelas"));
            long timestampAvaliado = avaliacaoJSON.getLong("timestamp_avaliado");
            this.setDataAvaliado(new Date(timestampAvaliado));
            this.setNomeLivro(avaliacaoJSON.getString("nomeLivro"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject toJSON() {
        JSONObject avaliacaoJSON = new JSONObject();
        try {
            avaliacaoJSON.put("comentario", this.getComentario());
            avaliacaoJSON.put("liked", this.isLike());
            avaliacaoJSON.put("estrelas", this.getEstrelas());
            avaliacaoJSON.put("timestamp_avaliado", this.getTimestampAvaliado());
            avaliacaoJSON.put("nomeLivro", this.getNomeLivro());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return avaliacaoJSON;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario.trim();
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
    public long getTimestampAvaliado() {
        return dataAvaliado.getTime();
    }
    public String getDataAvaliadoString() {
        return dateFormat.format(dataAvaliado);
    }

}
