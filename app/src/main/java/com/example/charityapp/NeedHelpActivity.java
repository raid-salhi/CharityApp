package com.example.charityapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.IOException;
import java.util.HashMap;

public class NeedHelpActivity extends AppCompatActivity {

    private AutoCompleteTextView Category;
    private EditText Title;
    private EditText Description;
    private ImageView SelectedImage;
    private Button Continue;
    private Uri imageUri;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);

        Category = findViewById(R.id.category);
        Title = findViewById(R.id.title_edit_text);
        Description = findViewById(R.id.description_edit_text);
        SelectedImage = findViewById(R.id.selected_image);
        Continue = findViewById(R.id.continue1);

        SelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

    }

    private void upload() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        final StorageReference filePath = FirebaseStorage.getInstance().getReference("NeedHelp").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        StorageTask uploadtask = filePath.putFile(imageUri);
        uploadtask.continueWithTask(new Continuation() {
            @Override
            public Object then(Task task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }

                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> task) {
                Uri downloadUri = task.getResult();
                imageUrl = downloadUri.toString();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("NeedHelp");
                String postId = ref.push().getKey();

                String title = Title.getText().toString();
                String categoryType = Category.getText().toString();
                String description = Description.getText().toString();

                HashMap<String , Object> map = new HashMap<>();
                map.put("postid" , postId);
                map.put("category" , categoryType);
                map.put("imageurl" , imageUrl);
                map.put("publisher" , FirebaseAuth.getInstance().getCurrentUser().getUid());
                map.put("title" , title);
                map.put("description" , description);

                ref.child(categoryType).child(postId).setValue(map);

                pd.dismiss();
                startActivity(new Intent(NeedHelpActivity.this , MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(NeedHelpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri uri) {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                SelectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}