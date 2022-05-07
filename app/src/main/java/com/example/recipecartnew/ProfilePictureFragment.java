package com.example.recipecartnew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;


public class ProfilePictureFragment extends Fragment {
    private ImageView imageView;
    private Button save, close;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pantry-ae39f-default-rtdb.firebaseio.com/");
    private Uri imageURI;
    private UploadTask uploadTask;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pantry-ae39f.appspot.com");
    private User currentUser = User.getInstance();
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_picture, container, false);
        imageView = (ImageView) view.findViewById(R.id.change_Picture);
        close = (Button) view.findViewById(R.id.closeBtn);
        save = (Button) view.findViewById(R.id.saveBtn);
        if(currentUser.getImageURL() != null) {
            imageView.setImageBitmap(null);
            final long ONE_MEGABYTE = 1024 * 1024;
            storageReference.child("images/").child(currentUser.getImageURL()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(ProfilePictureFragment.this)
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
            }
        });
        close.setOnClickListener(this::onClick);
        save.setOnClickListener(this::onClick);
        progressDialog = new ProgressDialog(getContext());
        return view;
    }

    
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.closeBtn:
                getParentFragmentManager().beginTransaction().replace(getId(),
                        new SettingsFragment()).commit();
                return;
            case R.id.saveBtn:
                if (uploadProfileImage()) {
                    getParentFragmentManager().beginTransaction().replace(getId(),
                            new SettingsFragment()).commit();
                    return;
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            imageURI = data.getData();
            imageView.setImageURI(imageURI);
        }
    }

    public boolean uploadProfileImage() {
        if(imageURI != null){
            uploadTask = storageReference.child("images/"+ imageURI.getLastPathSegment()).putFile(imageURI);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    return;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            });

            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_SHORT).show();
            currentUser.setImageURL(imageURI.toString());
            databaseReference.child("users").child(currentUser.getUsername()).child("imageURI").setValue(imageURI.getLastPathSegment().toString());
            return true;
        }

        Toast.makeText(getActivity(), "No changes made to profile picture", Toast.LENGTH_SHORT).show();
        return false;
    }
}