package com.example.fileuploader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    Button button;
    ImageView imageView;

    private static final int GALLERY = 4;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        progressDialog = new ProgressDialog(this);

    }

    public void click(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/+");

        startActivityForResult(intent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY){

            progressDialog.setMessage("Uploading...");
            Uri uri = data.getData();
            progressDialog.show();

            imageView.setImageURI(uri);

            StorageReference folder1 = storageReference.child("Photos/"+ uri.getLastPathSegment()+".png");
            folder1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "UPLOADED", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}























