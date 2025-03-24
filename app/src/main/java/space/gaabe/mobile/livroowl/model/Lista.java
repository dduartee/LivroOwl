package space.gaabe.mobile.livroowl.model;

import org.json.JSONArray;
import org.json.JSONObject;

// o livro pode ser adicionado a uma lista com varios outros livros
public class Lista {
    private String nome;
    private Livro[] livros;
    private int qtdeLivros;


    public Lista(JSONObject listaJSON) {
        try {
            this.setNome(listaJSON.getString("nome"));
            this.setQtdeLivros(listaJSON.getInt("qtdeLivros"));
            JSONArray livrosJSON = listaJSON.getJSONArray("livros");
            Livro[] livros = new Livro[livrosJSON.length()];
            for (int i = 0; i < livrosJSON.length(); i++) {
                JSONObject livroJSON = livrosJSON.getJSONObject(i);
                Livro livro = new Livro(livroJSON);
                livros[i] = livro;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Lista() {
        this.nome = "";
        this.livros = new Livro[0];
        this.qtdeLivros = 0;
    }

    public void addLivro(Livro livro) {
        Livro[] novosLivros = new Livro[qtdeLivros + 1];
        for (int i = 0; i < qtdeLivros; i++) {
            novosLivros[i] = livros[i];
        }
        novosLivros[qtdeLivros] = livro;
        livros = novosLivros;
    }

    public void removeLivro(Livro livro) {
        Livro[] novosLivros = new Livro[qtdeLivros-1];
        for (int i = 0; i < qtdeLivros; i++) {
            if(livro.getNome() != livros[i].getNome()) {
                novosLivros[i] = livros[i];
            }
        }
        livros = novosLivros;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Livro[] getLivros() {
        return livros;
    }

    public void setLivros(Livro[] livros) {
        this.livros = livros;
    }

    public int getQtdeLivros() {
        return qtdeLivros;
    }

    public void setQtdeLivros(int qtdeLivros) {
        this.qtdeLivros = qtdeLivros;
    }

}
