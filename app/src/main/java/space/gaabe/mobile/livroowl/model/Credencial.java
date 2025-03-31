package space.gaabe.mobile.livroowl.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Credencial {
    private String username;
    private String nomeCompleto;
    private String email;
    private String senha;
    private Date dataNascimento;
    public Credencial() {
        this.setDataNascimento(new Date());
        this.setNomeCompleto("");
        this.setUsername("");
        this.setSenha("");
        this.setEmail("");
    }

    public Credencial(JSONObject credencialJSON) {
        try {
            this.setNomeCompleto(credencialJSON.getString("nomeCompleto"));
            this.setEmail(credencialJSON.getString("email"));
            this.setSenha(credencialJSON.getString("senha"));
            this.setUsername(credencialJSON.getString("username"));
            this.setDataNascimento(new Date(credencialJSON.getLong("timestamp_dataNascimento")));
        } catch (Exception e) {
            Log.e("Usuario", e.getMessage());
        }
    }

    public JSONObject toJSON() {
        JSONObject credencialJSON = new JSONObject();
        try {
            credencialJSON.put("nomeCompleto", this.getNomeCompleto());
            credencialJSON.put("username", this.getUsername());
            credencialJSON.put("senha", this.getSenha());
            credencialJSON.put("email", this.getEmail());
            credencialJSON.put("timestamp_dataNascimento", this.getTimestampDataNascimento());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return credencialJSON;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public long getTimestampDataNascimento() {
        return dataNascimento.getTime();
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
