package space.gaabe.mobile.livroowl.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Livro {
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCapaId() {
        return this.capaId;
    }

    public void setCapaId(String capaId) {
        this.capaId = capaId;
    }

    private String capaId;
    private String nome;
    private String autor;
    private String genero;
    private int qtdeAvaliacoes;
    private float mediaAvaliacao;
    public Livro() {
        this.setId("");
        this.setCapaId("");
        this.setNome("");
        this.setAutor("");
        this.setGenero("");
        this.setQtdeAvaliacoes(0);
        this.setMediaAvaliacao(0);
    }
    public Livro(JSONObject livroJSON) {
        try {
            this.setNome(livroJSON.getString("nome"));
            this.setCapaId(livroJSON.getString("capaId"));
            this.setId(livroJSON.getString("id"));
            this.setAutor(livroJSON.getString("autor"));
            this.setGenero(livroJSON.getString("genero"));
            this.setQtdeAvaliacoes(livroJSON.getInt("qtdeAvaliacoes"));
            this.setMediaAvaliacao((float) livroJSON.getDouble("mediaAvaliacao"));
        } catch (Exception e) {
            Log.e("Livro", e.getMessage());
        }
    }

    public JSONObject toJSON() {
        JSONObject livroJSON = new JSONObject();
        try {
            livroJSON.put("nome", this.getNome());
            livroJSON.put("capaId", this.getCapaId());
            livroJSON.put("id", this.getId());
            livroJSON.put("autor", this.getAutor());
            livroJSON.put("genero", this.getGenero());
            livroJSON.put("qtdeAvaliacoes", this.getQtdeAvaliacoes());
            livroJSON.put("mediaAvaliacao", this.getMediaAvaliacao());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livroJSON;
    }


    /**
     * Método para atualizar as avaliações do livro
     * @param avaliacao Classe avaliacao preenchida
     */
    public void avaliar(Avaliacao avaliacao) {
        avaliacao.setNomeLivro(this.nome);
        int novaQtdeAvaliacoes = qtdeAvaliacoes + 1;
        float novasEstrelas = mediaAvaliacao * qtdeAvaliacoes+ avaliacao.getEstrelas();
        float novaMediaAvaliacao = novasEstrelas / novaQtdeAvaliacoes;
        mediaAvaliacao = novaMediaAvaliacao;
        qtdeAvaliacoes++;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setQtdeAvaliacoes(int qtdeAvaliacoes) {
        this.qtdeAvaliacoes = qtdeAvaliacoes;
    }

    public void setMediaAvaliacao(float mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public String getNome() {
        return nome;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public int getQtdeAvaliacoes() {
        return qtdeAvaliacoes;
    }

    public float getMediaAvaliacao() {
        return mediaAvaliacao;
    }

}
