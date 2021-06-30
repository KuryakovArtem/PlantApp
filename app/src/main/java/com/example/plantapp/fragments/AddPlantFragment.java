package com.example.plantapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantapp.Plant;
import com.example.plantapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class AddPlantFragment extends Fragment {

    private EditText plantName;
    private Button addPhotoButton;
    private Button pushToDBButton;
    private Uri uri1;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance("gs://plant-app-78986-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Plants");
    private StorageReference storageReference = FirebaseStorage.getInstance("gs://plant-app-78986.appspot.com").getReference("images/*");
//    FirebaseStorage storage = FirebaseStorage.getInstance("gs://plant-app-78986.appspot.com");
    public static final int PICK_IMAGE = 1;

    private ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    uri1 = uri;
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_plant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plantName = view.findViewById(R.id.plant_name);
        addPhotoButton = view.findViewById(R.id.add_photo_button);
        pushToDBButton = view.findViewById(R.id.push_to_db);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });



        pushToDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadTask uploadTask = null;
                try {
                    uploadTask = storageReference.putBytes(parseUri());
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("aaa",exception.getMessage());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                        }
                    }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                String id = UUID.randomUUID().toString();
                                databaseReference.child(id).setValue(new Plant(plantName.getText().toString(),downloadUri.toString(), id));
                                Log.d("aqwe",String.valueOf(task.isSuccessful()));
                                getParentFragmentManager().popBackStack();
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });



    }

    private byte[] parseUri() throws IOException {
        return getBytes(getContext().getContentResolver().openInputStream(uri1));
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE) {
//
//        }
//    }


}
