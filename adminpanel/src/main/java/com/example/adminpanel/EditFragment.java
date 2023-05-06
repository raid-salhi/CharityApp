package com.example.adminpanel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class EditFragment extends Fragment {

    private String id;
    private String category;
    private AutoCompleteTextView subCategory;
    private AutoCompleteTextView wilaya;
    private EditText title;
    private EditText description;
    private EditText phone;
    private EditText amount;
    private Button Continue;
    private FirebaseFirestore db;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        bundle = getArguments();
        id = bundle.getString("pid");
        category = bundle.getString("category");
        db = FirebaseFirestore.getInstance();
        subCategory = view.findViewById(R.id.sub_category);
        wilaya = view.findViewById(R.id.wilaya);
        title = view.findViewById(R.id.title_edit_text);
        description = view.findViewById(R.id.description_edit_text);
        phone = view.findViewById(R.id.phone_edit_text);
        amount = view.findViewById(R.id.amount_edit_text);
        Continue = view.findViewById(R.id.continue_button);

        DocumentReference ref = db.collection(category).document(id);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                subCategory.setText((CharSequence) documentSnapshot.get("subCategory"));
                wilaya.setText((CharSequence) documentSnapshot.get("location"));
                title.setText((CharSequence) documentSnapshot.get("title"));
                description.setText((CharSequence) documentSnapshot.get("description"));
//                phone.setText((String) documentSnapshot.get("contact"));
//                amount.setText((String) documentSnapshot.get("amountGoal"));
            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String SubCategory = subCategory.getText().toString();
               String Location = wilaya.getText().toString();
               String Title = title.getText().toString();
               String Description = description.getText().toString();
               String Contact = phone.getText().toString();
               String AmountGoal =  amount.getText().toString();

               if(TextUtils.isEmpty(SubCategory) || TextUtils.isEmpty(Location) || TextUtils.isEmpty(Title)
                 || TextUtils.isEmpty(Description) || TextUtils.isEmpty(Contact) || TextUtils.isEmpty(AmountGoal)){

                   Toast.makeText(getContext(), "Empty credentials", Toast.LENGTH_SHORT).show();
               }else {
                   Map<String, Object> data = new HashMap<>();
                   data.put("subCategory", subCategory.getText().toString());
                   data.put("location", wilaya.getText().toString());
                   data.put("title", title.getText().toString());
                   data.put("description", description.getText().toString());
//                   data.put("contact", phone.getText().toString());
//                   data.put("amountGoal", amount.getText().toString());

                   ref.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Toast.makeText(getContext(), "post updated successfully", Toast.LENGTH_SHORT).show();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getContext(), "failed to update the post", Toast.LENGTH_SHORT).show();
                       }
                   });
               }
            }
        });

        return view;
    }
}