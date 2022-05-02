package com.example.recipecartnew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.storage.StorageTask;


public class ProfilePictureFragment extends Fragment {
    private ImageView imageView;
    private Button save, close;
    private Uri imageURI;
    private StorageTask uploadTask;
    private User currentUser = User.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_picture, container, false);
        imageView = (ImageView) view.findViewById(R.id.change_Picture);
        close = (Button) view.findViewById(R.id.closeBtn);
        save = (Button) view.findViewById(R.id.saveBtn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(ProfilePictureFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        close.setOnClickListener(this::onClick);
        save.setOnClickListener(this::onClick);
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
    public boolean uploadProfileImage() {
        return true;
    }
}