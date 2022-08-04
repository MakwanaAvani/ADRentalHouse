package com.ad.adrentalhouse;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class RentYourProperty extends Activity {
    // request code
    private final int PICK_IMAGE_REQUEST = 1;
    EditText owner_name, em, pho, add1, add2, floor, rent_price, rent_det;
    RadioButton homeaperrb, apartrb, roomrb, agerb;
    CheckBox fam, sm, sw;
    RadioGroup houaprt, roomg, ageofp;
    ImageView iv;
    Button complete, imagebt;
    int roomnoid, houapertid, ageid;
    String rent;
    StringBuffer rent_out;
    String rent_type;
    String roomno, age_pro;
    // instance for firebase storage and StorageReference
    FirebaseAuth fAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore fStroe;
    ProgressDialog progressDialog;
    // Uri indicates, where the image will be picked from'
    private String current_userid;
    private boolean isChecking = true;
    //chose image
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_your_property);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fStroe = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        current_userid = fAuth.getCurrentUser().getUid();
        //EditText
        owner_name = findViewById(R.id.edname);
        em = findViewById(R.id.edrent_your_email);
        pho = findViewById(R.id.edrent_your_phone);
        add1 = findViewById(R.id.edrent_your_locality);
        add2 = findViewById(R.id.edrent_your_sublocality);
        floor = findViewById(R.id.ed_total_floor);
        rent_price = findViewById(R.id.ed_price);
        rent_det = findViewById(R.id.edabout_property);
        complete = findViewById(R.id.btnsubimt);
        imagebt = findViewById(R.id.btnuploadimage);
        //RadioButton
        houaprt = findViewById(R.id.houapar);
        roomg = findViewById(R.id.roomgroup);
        ageofp = findViewById(R.id.ageofproperty);
        //CheckBox
        fam = findViewById(R.id.che_rent_out_fam);
        sm = findViewById(R.id.che_rent_out_single_men);
        sw = findViewById(R.id.che_rent_out_single_women);
        rent_out = new StringBuffer();
        //ImageView
        iv = findViewById(R.id.imageupload);
        // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        imagebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(RentYourProperty.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(RentYourProperty.this, "Perimssion Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(RentYourProperty.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Log.d("TAG", "Permission");
                    } else {
                        SelectImage();
                    }
                } else {
                    SelectImage();
                }
            }
        });
        fam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rent_out.append(fam.getText().toString() + " ");
                }
                if (isChecked == false) {
                    rent_out.toString().replace(fam.getText().toString(), " ");
                }
            }
        });
        sm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rent_out.append(sm.getText().toString() + " ");

                }
                if (isChecked == false) {
                    rent_out.toString().replace(sm.getText().toString(), " ");
                }
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rent_out.append(sw.getText().toString() + " ");
                }
                if (isChecked == false) {
                    rent_out.toString().replace(sw.getText().toString(), " ");

                }
            }
        });
        houaprt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    //isChecking = false;
                    //roomg.clearCheck();
                    houapertid = checkedId;
                }
                isChecking = true;
            }
        });
        roomg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    //  isChecking = false;
                    // ageofp.clearCheck();
                    roomnoid = checkedId;
                }
                isChecking = true;

            }
        });
        ageofp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    ageid = checkedId;
                }
                isChecking = true;

            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage("Storing data..");
//            progressDialog.show();
                final String ow_namw = owner_name.getText().toString();
                final String ema = em.getText().toString();
                final String ph = pho.getText().toString();
                final String addr1 = add1.getText().toString();
                final String addr2 = add2.getText().toString();
                final String flo = floor.getText().toString();
                final String reprice = rent_price.getText().toString();
                final String re_de = rent_det.getText().toString();
                if (ageid != 0 && houapertid != 0 && roomnoid != 0) {
                    agerb = (RadioButton) findViewById(ageid);
                    homeaperrb = (RadioButton) findViewById(houapertid);
                    roomrb = findViewById(roomnoid);
                    rent_type = homeaperrb.getText().toString();
                    roomno = roomrb.getText().toString();
                    age_pro = agerb.getText().toString();
                    Toast.makeText(RentYourProperty.this, rent_type + "\n" + age_pro + "\n" + roomno, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RentYourProperty.this, "Unselected.", Toast.LENGTH_SHORT).show();
                }
                String rent_ou = rent_out.toString();
                Toast.makeText(RentYourProperty.this, rent_ou, Toast.LENGTH_SHORT).show();
                //Check the isEmpty or not
                if (!validateEmail() | !validateUsername() | !validatePhoneNo() | !validateAddr1() | !validateAddr2() | !validateAddr1() | !validateFloorno() | !validateRentPrice() | !validateAboutPro()) {
                    return;
                } else {
                    if (TextUtils.isEmpty(ow_namw) && TextUtils.isEmpty(ema) && TextUtils.isEmpty(ph) && TextUtils.isEmpty(addr1) &&
                            TextUtils.isEmpty(addr2) && TextUtils.isEmpty(flo) && TextUtils.isEmpty(reprice) && TextUtils.isEmpty(re_de)
                            && TextUtils.isEmpty(rent_type) && TextUtils.isEmpty(roomno) && TextUtils.isEmpty(age_pro) && TextUtils.isEmpty(rent_ou) && imageUri == null) {
                        Toast.makeText(RentYourProperty.this, "Fill all the fieldls", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("imageUri--------------------------------------------------------------", String.valueOf(imageUri));

                        //ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.setMessage("Storing data..");
                        progressDialog.show();
                        //byte[] image = ImageViewToByte(iv);
                        //Here create the Firebase Storage in store the image
                        StorageReference ref = storageReference.child("rentimage/" + UUID.randomUUID().toString());
                        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Log.d("TAG_onsuccess------------------------", "onSuccess" + taskSnapshot.toString());
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.d("TAG_uri-----------------------------------------------------", "URI Path" + uri.getPath());
//                                        DocumentReference df = fStroe.collection("RentProperty").document("current_userid");
                                        //get data form string and store the Map
                                        Map<String, Object> rentProperty = new HashMap<>();
                                        rentProperty.put("Owner_name", ow_namw);
                                        rentProperty.put("Email", ema);
                                        rentProperty.put("Phone", ph);
                                        rentProperty.put("Address1", addr1);
                                        rentProperty.put("Address2", addr2);
                                        rentProperty.put("Floor", flo);
                                        rentProperty.put("Rent_price", reprice);
                                        rentProperty.put("Rent_Other_Detail", re_de);
                                        rentProperty.put("Property_Type", rent_type);
                                        rentProperty.put("Room", roomno);
                                        rentProperty.put("Age_of_Property", age_pro);
                                        rentProperty.put("Rent_Out_For", rent_ou);
                                        rentProperty.put("Image", uri);
                                        rentProperty.put("User_id", current_userid);
//                                          rentProperty.put("id","id");
                                        //here create the collection is RentProperty and that collection in create the document
                                        fStroe.collection("RentProperty").document(UUID.randomUUID().toString()).set(rentProperty).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(RentYourProperty.this, "Successfull add property. ", Toast.LENGTH_SHORT).show();
                                            }
                                        });
//                                        df.set(rentProperty);
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                        finish();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                Toast.makeText(RentYourProperty.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            // Progress Listener for loading
                            // percentage on the dialog box
                            @Override
                            public void onProgress(
                                    UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
                    }
                }
            }
        });
    }

    //Set ImageView in select image show
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                InputStream inputs = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputs);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Covert image are byte
    private byte[] ImageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //You phone image folder to display
    public void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // All Validation code start
    //Validate of Email
    private boolean validateEmail() {
        String emailInput = em.getText().toString().trim();
        //To check the Email is empty
        if (emailInput.isEmpty()) {
            em.setError("Field can't be empty");
            return false;
        }
        //to check the Email Address email are proper or not
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            em.setError("Please enter a valid email address");
            return false;
        } else {
            em.setError(null);
            return true;
        }
    }

    //validation of User name
    private boolean validateUsername() {
        String usernameInput = owner_name.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            owner_name.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 25) {
            owner_name.setError("Username too long");
            return false;
        } else if (!usernameInput.matches("[a-zA-Z ]+")) {
            owner_name.requestFocus();
            owner_name.setError("Enter only alphabatical character");
            return false;
        } else {
            owner_name.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNo() {
        String phoneInput = pho.getText().toString().trim();

        if (TextUtils.isEmpty(phoneInput)) {
            pho.setError("Field can't be empty");
            return false;
        } else if (phoneInput.length() < 10 || phoneInput.length() > 10) {
            pho.setError("Not valid Number");
            return false;
        } else if (!phoneInput.matches("[0-9]{10}")) {
            pho.requestFocus();
            pho.setError("Enter only number.");
            return false;
        } else {
            pho.setError(null);
            return android.util.Patterns.PHONE.matcher(phoneInput).matches();
        }
    }

    private boolean validateAddr1() {
        String addrInput = add1.getText().toString().trim();

        if (TextUtils.isEmpty(addrInput)) {
            add1.setError("Field can't be empty");
            return false;
        } else {
            add1.setError(null);
            return true;
        }
    }

    private boolean validateAddr2() {
        String addrInput = add2.getText().toString().trim();

        if (TextUtils.isEmpty(addrInput)) {
            add2.setError("Field can't be empty");
            return false;
        } else {
            add2.setError(null);
            return true;
        }
    }

    private boolean validateAboutPro() {
        String abouproInput = rent_det.getText().toString().trim();

        if (TextUtils.isEmpty(abouproInput)) {
            rent_det.setError("Field can't be empty");
            return false;
        } else {
            rent_det.setError(null);
            return true;
        }
    }


    private boolean validateFloorno() {
        String floorInput = floor.getText().toString().trim();

        if (TextUtils.isEmpty(floorInput)) {
            floor.setError("Field can't be empty");
            return false;
        }
//        else if (floorInput.length() < 2) {
//            floor.setError("Not valid Number");
//            return false;
//        }
//        else if (!floorInput.matches("[0-9]{1}")) {
//            floor.requestFocus();
//            floor.setError("Enter only number.");
//            return false;
//        }
        else {
            floor.setError(null);
            return true;
        }
    }

    private boolean validateRentPrice() {
        String rentInput = rent_price.getText().toString().trim();

        if (TextUtils.isEmpty(rentInput)) {
            rent_price.setError("Field can't be empty");
            return false;
        }
//        else if (rentInput.length() > 4) {
//            rent_price.setError("Not valid price");
//            return false;
//        }
        else if (!rentInput.matches("[0-9]{4}[0-9]{0,1}")) {
            rent_price.setError("Enter only number.");
            return false;
        } else {
            rent_price.setError(null);
            return true;
        }
    }
}