package space.gaabe.mobile.livroowl.ui.avaliacao;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import space.gaabe.mobile.livroowl.R;
import space.gaabe.mobile.livroowl.model.Avaliacao;

/**
 * A fragment representing a list of Items.
 */
public class ConsultaAvaliacaoFragment extends Fragment implements Response.Listener, Response.ErrorListener{
    private ArrayList<Avaliacao> avaliacoes;
    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private View view;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ConsultaAvaliacaoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ConsultaAvaliacaoFragment newInstance(int columnCount) {
        ConsultaAvaliacaoFragment fragment = new ConsultaAvaliacaoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_consulta_avaliacao_list, container, false);

        this.requestQueue = Volley.newRequestQueue(getContext());
        this.requestQueue.start();
        JSONArray jsonArray = new JSONArray();
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setLike(true);
        jsonArray.put(avaliacao.toJSON());
        this.jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, "http://10.0.2.2:8080/avaliacoes", jsonArray, this, this);
        requestQueue.add(jsonArrayRequest);
        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Snackbar.make(view.getContext(), view, "Ocorreu um erro ao buscar as avaliações: " + error.toString(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.toString());
            if(jsonArray.length() > 0) {
                Avaliacao avaliacao = null;
                this.avaliacoes = new ArrayList<Avaliacao>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    avaliacao = new Avaliacao(jsonArray.getJSONObject(i));
                    this.avaliacoes.add(avaliacao);
                }
                // Set the adapter
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new AvaliacaoRecyclerViewAdapter(this.avaliacoes));
                }
            } else {
                Snackbar.make(view.getContext(), view, "Nenhuma avaliação encontrada", Snackbar.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}