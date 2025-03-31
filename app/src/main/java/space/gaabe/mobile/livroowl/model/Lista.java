package space.gaabe.mobile.livroowl.model;

import org.json.JSONArray;
import org.json.JSONObject;

// o livro pode ser adicionado a uma lista com varios outros livros
public class Lista {
    private String nome;
    private Livro[] livros; // TODO: usar a biblioteca ArrayList
    private int qtdeLivros;

    public Lista() {
        this.nome = "";
        this.livros = new Livro[0];
        this.qtdeLivros = 0;
    }
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

    public JSONObject toJSON() {
        JSONObject listaJSON = new JSONObject();

        try {
            listaJSON.put("nome", this.getNome());
            listaJSON.put("qtdeLivros", this.getQtdeLivros());
            JSONArray livrosArrayJSON = new JSONArray();
            Livro[] livros = this.getLivros();
            for (int i = 0; i < this.getQtdeLivros(); i++) {
                JSONObject livroJSON = livros[i].toJSON();
                livrosArrayJSON.put(livroJSON);
            }
            listaJSON.put("livros", livrosArrayJSON);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaJSON;
    }
    public void addLivro(Livro livro) {
        int novaQtdeLivros = qtdeLivros + 1;
        Livro[] newLivros = new Livro[novaQtdeLivros];
        for (int i = 0; i < qtdeLivros; i++) {
            newLivros[i] = livros[i];
        }
        newLivros[novaQtdeLivros] = livro;
        qtdeLivros = novaQtdeLivros;
        setLivros(newLivros);
    }

    public void removeLivro(Livro livroRemover) {
        if (qtdeLivros == 0) {
            throw new RuntimeException("Lista vazia");
        }
        int novaQtdeLivros = qtdeLivros - 1;
        Livro[] newLivros = new Livro[novaQtdeLivros];
        for (int i = 0; i < qtdeLivros; i++) {
            Livro livro = livros[i];
            // se for a ultima iteração e não conter o livro na lista
            if(livroRemover.getNome() != livro.getNome()) {
                newLivros[i] = livro;
            } else if (i == novaQtdeLivros) {
                throw new RuntimeException("Livro não encontrado na lista");
            }
        }
        qtdeLivros = novaQtdeLivros;
        setLivros(newLivros);
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
