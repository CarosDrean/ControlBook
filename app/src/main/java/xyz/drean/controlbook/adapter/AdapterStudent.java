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
import xyz.drean.controlbook.pojo.Student;

public class AdapterStudent extends FirestoreRecyclerAdapter<Student, AdapterStudent.StudentHolder> {

    private Activity activity;

    public AdapterStudent(@NonNull FirestoreRecyclerOptions<Student> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull StudentHolder holder, int i, @NonNull Student model) {
        holder.name.setText(model.getName());
        holder.lastname.setText(model.getLastname());
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new AdapterStudent.StudentHolder(v);
    }

    class StudentHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView lastname;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_student);
            lastname = itemView.findViewById(R.id.txt_lastname_student);
        }
    }
}
