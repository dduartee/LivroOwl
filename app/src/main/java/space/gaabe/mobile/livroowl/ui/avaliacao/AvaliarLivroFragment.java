package space.gaabe.mobile.livroowl.ui.avaliacao;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import space.gaabe.mobile.livroowl.MainActivity;
import space.gaabe.mobile.livroowl.R;
import space.gaabe.mobile.livroowl.model.Avaliacao;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvaliarLivroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvaliarLivroFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private TextView nomeLivro;
    private EditText comentarioAvalicao;
    private RatingBar ratingBarAvaliacao;
    private Button likeButton;
    private Button addToListButton;
    private Button submitAvaliacaoButton;
    Avaliacao avaliacao = new Avaliacao();

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

        // binding
        this.comentarioAvalicao = view.findViewById(R.id.idComentarioAvalicao);
        this.ratingBarAvaliacao = view.findViewById(R.id.idRatingBarAvaliacao);
        this.likeButton = view.findViewById(R.id.likeButton);
        this.addToListButton = view.findViewById(R.id.addToListButton);
        this.submitAvaliacaoButton = view.findViewById(R.id.submitAvaliacaoButton);
        this.nomeLivro = view.findViewById(R.id.idNomeLivro);
        this.nomeLivro.setText("A bruxa");
        this.likeButton.setOnClickListener(this);
        this.submitAvaliacaoButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if(viewId == R.id.likeButton) {
            try {
                boolean like = avaliacao.isLike();
                // objeto de negocio
                avaliacao.setLike(!like);
                if (!like) {
                    this.likeButton.setText("Liked");
                } else {
                    this.likeButton.setText("Disliked");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if(viewId == R.id.submitAvaliacaoButton) {
            try {
                avaliacao.setComentario(this.comentarioAvalicao.getText().toString());
                avaliacao.setEstrelas(this.ratingBarAvaliacao.getRating());
                avaliacao.setDataAvaliado(new Date());

                Toast.makeText(view.getContext(), "Avaliado!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}