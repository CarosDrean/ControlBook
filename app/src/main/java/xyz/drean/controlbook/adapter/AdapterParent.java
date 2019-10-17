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
import xyz.drean.controlbook.pojo.Parent;

public class AdapterParent extends FirestoreRecyclerAdapter<Parent, AdapterParent.ParentHolder> {

    private Activity activity;

    public AdapterParent(@NonNull FirestoreRecyclerOptions<Parent> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ParentHolder holder, int i, @NonNull Parent model) {
        holder.name.setText(model.getName());
        holder.user.setText(model.getUser());
    }

    @NonNull
    @Override
    public ParentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
        return new AdapterParent.ParentHolder(v);
    }

    class ParentHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView user;

        public ParentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_par);
            user = itemView.findViewById(R.id.txt_user_par);
        }
    }
}
