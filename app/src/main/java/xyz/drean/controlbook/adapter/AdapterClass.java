package xyz.drean.controlbook.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import xyz.drean.controlbook.R;
import xyz.drean.controlbook.fragment.Students;
import xyz.drean.controlbook.pojo.ClassRom;

public class AdapterClass extends FirestoreRecyclerAdapter<ClassRom, AdapterClass.ClassHolder> {

    private Activity activity;

    public AdapterClass(@NonNull FirestoreRecyclerOptions<ClassRom> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ClassHolder holder, int i, @NonNull ClassRom model) {
        holder.name.setText(model.getName());
        holder.turno.setText(model.getTurno());

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Students().show(((AppCompatActivity)activity).getSupportFragmentManager(), "Add Class Rom");
            }
        });
    }

    @NonNull
    @Override
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classrom, parent, false);
        return new AdapterClass.ClassHolder(v);
    }

    class ClassHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView turno;
        private RelativeLayout content;

        public ClassHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            turno = itemView.findViewById(R.id.txt_turno);
            content = itemView.findViewById(R.id.content_class);
        }
    }
}
