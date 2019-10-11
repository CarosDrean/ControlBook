package xyz.drean.controlbook.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import xyz.drean.controlbook.R;
import xyz.drean.controlbook.pojo.Obervation;
import xyz.drean.controlbook.pojo.Student;

public class AdapterStudent extends FirestoreRecyclerAdapter<Student, AdapterStudent.StudentHolder> {

    private Activity activity;

    public AdapterStudent(@NonNull FirestoreRecyclerOptions<Student> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    // TODO: CORREGIR ESTE ESPAGUETI

    @Override
    protected void onBindViewHolder(@NonNull StudentHolder holder, int i, @NonNull final Student model) {
        holder.name.setText(model.getName());
        holder.lastname.setText(model.getLastname());

        verifyAssistance(model.getId(), "11/10/2019", holder.assistance);

        holder.assistance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkAssistance(model.getId(), "true", "11/10/2019");
                } else {
                    checkAssistance(model.getId(), "false", "11/10/2019");
                }
            }
        });
    }

    private void verifyAssistance(String idStudent, String date, final CheckBox box) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("observations")
                .whereEqualTo("date", date)
                .whereEqualTo("idStudent", idStudent)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        assert value != null;
                        for(QueryDocumentSnapshot doc: value){
                            Obervation obs = doc.toObject(Obervation.class);
                            box.setChecked(Boolean.valueOf(obs.getAssistance()));
                            Toast.makeText(activity, "ver" + obs.getAssistance(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkAssistance(final String idStudent, final String assistance, final String date) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("observations")
                .whereEqualTo("date", date)
                .whereEqualTo("idStudent", idStudent)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            assert task.getResult() != null;
                            int au = 0;
                            for(QueryDocumentSnapshot value: task.getResult()) {
                                au = 1;
                                Obervation obs = value.toObject(Obervation.class);
                                Toast.makeText(activity, "Que? " + obs, Toast.LENGTH_SHORT).show();
                                obs.setAssistance(assistance);
                                db.collection("observations")
                                        .document(value.getId()).set(obs);
                            }
                            if(au == 0) {
                                saveAssistance(db, idStudent, assistance, date);
                            }
                        }
                    }
                });


    }

    private void saveAssistance(FirebaseFirestore db, final String idStudent, final String assistance, final String date) {
        Obervation obs = new Obervation();
        obs.setId(String.valueOf(System.currentTimeMillis()));
        obs.setDate(date);
        obs.setAssistance(assistance);
        obs.setIdStudent(idStudent);

        db.collection("observations")
                .add(obs)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(activity, "Asistencia Guardada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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
        private CheckBox assistance;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_student);
            lastname = itemView.findViewById(R.id.txt_lastname_student);
            assistance = itemView.findViewById(R.id.check_student);
        }
    }
}
