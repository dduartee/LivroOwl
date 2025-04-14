package space.gaabe.mobile.livroowl.ui.avaliacao;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import space.gaabe.mobile.livroowl.R;
import space.gaabe.mobile.livroowl.databinding.FragmentConsultaAvaliacaoBinding;
import space.gaabe.mobile.livroowl.model.Avaliacao;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Avaliacao}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AvaliacaoRecyclerViewAdapter extends RecyclerView.Adapter<AvaliacaoRecyclerViewAdapter.ViewHolder> {

    private final List<Avaliacao> mValues;
    public AvaliacaoRecyclerViewAdapter(List<Avaliacao> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentConsultaAvaliacaoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getNomeLivro());
        String numEstrelas = String.valueOf(mValues.get(position).getEstrelas());

        holder.mContentView.setText(String.format("%s estrelas", numEstrelas));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mIdView;
        public final TextView mContentView;
        public Avaliacao mItem;

        public ViewHolder(FragmentConsultaAvaliacaoBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            //CLICK - pegar posicao que foi clicada
            int adapterposition = this.getLayoutPosition();
            //mostrar posição clicada
            CharSequence text = "Posicao = " + adapterposition;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(view.getContext(), text, duration);
            toast.show();
        }
    }
}