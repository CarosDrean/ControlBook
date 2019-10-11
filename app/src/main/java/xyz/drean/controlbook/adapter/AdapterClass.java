package xyz.drean.controlbook.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import xyz.drean.controlbook.R;
import xyz.drean.controlbook.fragment.Students;
import xyz.drean.controlbook.pojo.ClassRom;

public class AdapterClass extends FirestoreRecyclerAdapter<ClassRom, AdapterClass.ClassHolder> {

    private Activity activity;
    private String contx;

    public AdapterClass(@NonNull FirestoreRecyclerOptions<ClassRom> options, Activity activity, String contx) {
        super(options);
        this.activity = activity;
        this.contx = contx;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ClassHolder holder, final int i, @NonNull final ClassRom model) {
        holder.name.setText(model.getName());
        holder.turno.setText(model.getTurno());

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lead(model.getId());
            }
        });

        holder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertDelete(i);
                return true;
            }
        });
    }

    private void lead(String idClass) {
        Bundle args = new Bundle();
        args.putString("id", idClass);
        args.putString("contx", contx);
        Students students = new Students();
        students.setArguments(args);
        students.show(((AppCompatActivity)activity).getSupportFragmentManager(), "Add Class Rom");
    }

    @NonNull
    @Override
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classrom, parent, false);
        return new AdapterClass.ClassHolder(v);
    }

    private void alertDelete(final int position) {
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.delete_item, null);
        LinearLayout content = v.findViewById(R.id.delete_item);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
                dialog.dismiss();
            }
        });
        dialog.setContentView(v);
        dialog.show();
    }

    private void removeItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
        Toast.makeText(activity, "¡Aula Eliminada!", Toast.LENGTH_SHORT).show();
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
