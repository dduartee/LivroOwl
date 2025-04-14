package space.gaabe.mobile.livroowl.ui.avaliacao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import space.gaabe.mobile.livroowl.R;
import space.gaabe.mobile.livroowl.model.Avaliacao;
import space.gaabe.mobile.livroowl.model.Lista;
import space.gaabe.mobile.livroowl.model.Livro;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvaliarLivroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvaliarLivroFragment extends Fragment implements View.OnClickListener, Response.ErrorListener, Response.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private TextView nomeLivro;
    private ImageView capaLivro;
    private EditText comentarioAvalicao;
    private RatingBar ratingBarAvaliacao;
    private MaterialButton likeButton;
    private MaterialButton addToListButton;
    private Button submitAvaliacaoButton;
    Avaliacao avaliacao = new Avaliacao();
    Livro livro = new Livro();
    Lista lista = new Lista();

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;


    public AvaliarLivroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvaliarLivroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvaliarLivroFragment newInstance(String param1, String param2) {
        AvaliarLivroFragment fragment = new AvaliarLivroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_avaliar_livro, container, false);
        // https://openlibrary.org/people/mekBot/books/want-to-read.json

        // binding
        this.comentarioAvalicao = view.findViewById(R.id.idComentarioAvalicao);
        this.ratingBarAvaliacao = view.findViewById(R.id.idRatingBarAvaliacao);
        this.likeButton = view.findViewById(R.id.likeButton);
        this.addToListButton = view.findViewById(R.id.addToListButton);
        this.submitAvaliacaoButton = view.findViewById(R.id.submitAvaliacaoButton);
        this.nomeLivro = view.findViewById(R.id.idNomeLivro);
        this.capaLivro = view.findViewById(R.id.CoverImageView);

        this.likeButton.setOnClickListener(this);
        this.addToListButton.setOnClickListener(this);
        this.submitAvaliacaoButton.setOnClickListener(this);

        // request do livro
        this.requestQueue = Volley.newRequestQueue(view.getContext());
        this.requestQueue.start();
        fetchLivro();
        // this.nomeLivro.setText(this.livro.getNome());
        this.lista.setNome("Minha lista");
        return view;
    }
    private void handleResetAvaliacao() {
        this.resetLikeButton();
        this.addToListButton.setClickable(true);
        this.comentarioAvalicao.setText("");
        this.ratingBarAvaliacao.setRating(0);
    }
    private void fetchLivro() {
        handleResetAvaliacao();
        JSONObject jsonObject = new JSONObject();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://openlibrary.org/people/mekBot/books/want-to-read.json",
                jsonObject, response -> {
            try {
                JSONObject JSONresponse = new JSONObject((response.toString()));
                JSONArray readingLogEntries = JSONresponse.getJSONArray("reading_log_entries");

                if (readingLogEntries.length() > 0) {
                    // get random number between 0 and readingLogEntries.length()
                    int randomIndex = (int) (Math.random() * readingLogEntries.length());
                    JSONObject firstEntry = readingLogEntries.getJSONObject(randomIndex);
                    JSONObject work = firstEntry.getJSONObject("work");
                    String title = work.getString("title");
                    this.livro.setNome(title);
                    this.livro.setCapaId(work.getString("cover_id"));
                    this.avaliacao.setNomeLivro(title);
                    this.nomeLivro.setText(title);
                    String coverURL = "https://covers.openlibrary.org/b/id/" + this.livro.getCapaId() + "-M.jpg";
                    Log.d("AvaliarLivroFragment", "Cover URL: " + coverURL);
                    Glide.with(getContext())
                            .load(coverURL)
                            .into(capaLivro);
                    JSONArray authors = work.getJSONArray("author_names");
                    String author = authors.getString(0);
                    this.livro.setAutor(author);
                    Log.d("AvaliarLivroFragment", "Title of the first book: " + title);
                } else {
                    Log.w("AvaliarLivroFragment", "No reading log entries found.");
                }
            } catch (JSONException e) {
                Log.e("AvaliarLivroFragment", "Error parsing JSON: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }, this);
        jsonObjectRequest.setTag("GetWantToReadBooks");
        requestQueue.add(jsonObjectRequest);
    }

    private void handleLikeButton() {
        // objeto de negocio
        avaliacao.setLike(true);
        this.likeButton.setText("Liked");
        this.likeButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        this.likeButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.baseline_sentiment_very_satisfied_24)); // Keep the satisfied icon
        this.likeButton.setClickable(false);
        this.likeButton.setTextColor(ContextCompat.getColor(getContext(), R.color.addToList_button_text_added));

    }
    private void resetLikeButton() {
        this.likeButton.setText("Like");
        this.likeButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.like_button_background));
        this.likeButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.baseline_sentiment_very_satisfied_24));
        this.likeButton.setClickable(true);
    }
    private void handleAddToListButton() {
        // this.addToListButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        this.addToListButton.setText("Adicionado");
        // this.addToListButton.setEnabled(false);
        this.addToListButton.setClickable(false);
        this.addToListButton.setTextColor(ContextCompat.getColor(getContext(), R.color.addToList_button_text_added));
        this.addToListButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.outline_bookmark_added_24));
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        Log.d("AvaliarLivroFragment", "Botão clicado: " + viewId);
        Log.d("AvaliarLivroFragment", "addToListId: " + R.id.addToListButton);
        if(viewId == R.id.likeButton) {
            try {
                this.handleLikeButton();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if(viewId == R.id.submitAvaliacaoButton) {
            try {
                avaliacao.setComentario(this.comentarioAvalicao.getText().toString());
                avaliacao.setEstrelas(this.ratingBarAvaliacao.getRating());
                avaliacao.setDataAvaliado(new Date());
                this.jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://10.0.2.2:8080/avaliacoes",
                        avaliacao.toJSON(), this, this);
                requestQueue.add(jsonObjectRequest);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if(viewId == R.id.addToListButton) {
            try {
                Log.d("AvaliarLivroFragment", "Livro adicionado à lista: " + this.livro.getNome());
                // this.lista.addLivro(this.livro);
                Toast.makeText(view.getContext(), "Livro adicionado a lista!", Toast.LENGTH_LONG).show();
                this.handleAddToListButton();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Snackbar messageErro = Snackbar.make(view.getContext(), view, "Opa! Ocorreu algum erro ao enviar a avaliação " + error.toString(), Snackbar.LENGTH_LONG);
        messageErro.show();
        fetchLivro();
    }

    @Override
    public void onResponse(Object response) {
        fetchLivro();
        try {

            JSONObject json = new JSONObject(response.toString());
            CharSequence message = json.getString("id");
            long timestampAvaliado = json.getLong("timestamp_avaliado");
            if(timestampAvaliado > 0) {
                this.handleResetAvaliacao();
                Toast.makeText(view.getContext(), "Avaliado!", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}