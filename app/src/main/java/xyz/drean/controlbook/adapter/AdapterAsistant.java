package xyz.drean.controlbook.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import xyz.drean.controlbook.R;
import xyz.drean.controlbook.pojo.Assistant;

public class AdapterAsistant extends FirestoreRecyclerAdapter<Assistant, AdapterAsistant.AsistantHolder> {

    private Activity activity;

    public AdapterAsistant(@NonNull FirestoreRecyclerOptions<Assistant> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull AsistantHolder holder, int i, @NonNull Assistant model) {
        holder.name.setText(model.getName());
        holder.user.setText(model.getUser());
    }

    @NonNull
    @Override
    public AsistantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asistant, parent, false);
        return new AdapterAsistant.AsistantHolder(v);
    }

    class AsistantHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView user;

        public AsistantHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_aux);
            user = itemView.findViewById(R.id.txt_user);
        }
    }
}
