package com.itcraftsolution.allfirebaseauth.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itcraftsolution.allfirebaseauth.Model.UserModel;
import com.itcraftsolution.allfirebaseauth.R;
import com.itcraftsolution.allfirebaseauth.databinding.ActivityFirebaseDbBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class FirebaseDbActivity extends AppCompatActivity {

    private ActivityFirebaseDbBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private Uri filePath;
    private Bitmap bitmap;
    private boolean checkImage = false;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirebaseDbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(FirebaseDbActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edName.getText().toString().length() <= 2)
                {
                    binding.edName.setError("Plz Enter Valid Name!!");
                    binding.edName.requestFocus();

                }else if(binding.edGraduation.getText().toString().length() < 1)
                {
                    binding.edGraduation.setError("Plz Enter Valid Graduation!!");
                    binding.edGraduation.requestFocus();
                }else if(binding.edSemester.getText().toString().isEmpty())
                {
                    binding.edSemester.setError("Plz Enter Semester!!");
                    binding.edSemester.requestFocus();
                }else if (!checkImage)
                {
                    Toast.makeText(FirebaseDbActivity.this, "Browse any Image", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.show();
                    String name = binding.edName.getText().toString();
                    String graduation = binding.edGraduation.getText().toString();
                    String semester = binding.edSemester.getText().toString();

                    uploadIntoFirebase(name , graduation, semester);
                }
            }
        });

        binding.btnShowRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirebaseDbActivity.this, ShowDeatilsActivity.class));
            }
        });
        binding.btnBrowserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(FirebaseDbActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    launcher.launch(intent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK)
                {
                    if (result.getData() != null) {
                        filePath = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(filePath);
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.igPreviewImage.setImageBitmap(bitmap);
                            checkImage = true;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void uploadIntoFirebase(String name, String graduation, String semester)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("Students").child(name);
        reference.putFile(filePath)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(FirebaseDbActivity.this, "Image Stored successfully", Toast.LENGTH_SHORT).show();

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dialog.dismiss();
                                    UserModel model = new UserModel(name , graduation, semester, String.valueOf(uri));

                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = db.getReference("Students");
                                    reference.child(name).setValue(model);

                                    Toast.makeText(FirebaseDbActivity.this, "Data Stored successfully", Toast.LENGTH_SHORT).show();
                                    binding.edName.setText("");
                                    binding.edGraduation.setText("");
                                    binding.edSemester.setText("");
                                    binding.igPreviewImage.setImageResource(R.mipmap.ic_launcher_round);
                                }
                            });
                        }else{
                            Toast.makeText(FirebaseDbActivity.this, "Image Store Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}