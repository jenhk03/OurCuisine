package com.example.ourcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ourcuisine.API.APIRequestData;
import com.example.ourcuisine.API.RetroServer;
import com.example.ourcuisine.Model.ResponseModel;
import com.example.ourcuisine.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeActivity extends AppCompatActivity
{
    private String yId, yName, yOrigin, yDescription;
    private EditText etName, etOrigin, etDescription;
    private Button btnChange;
    private String name, origin, description;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        Intent take = getIntent();
        yId = take.getStringExtra("xId");
        yName = take.getStringExtra("xName");
        yOrigin = take.getStringExtra("xOrigin");
        yDescription = take.getStringExtra("xDescription");
        etName = findViewById(R.id.et_name);
        etOrigin = findViewById(R.id.et_origin);
        etDescription = findViewById(R.id.et_description);
        btnChange = findViewById(R.id.btn_change);
        etName.setText(yName);
        etOrigin.setText(yOrigin);
        etDescription.setText(yDescription);
        btnChange.setOnClickListener(new View.OnClickListener()
                                     {
                                       @Override
                                       public void onClick(View v)
                                       {
                                           name = etName.getText().toString();
                                           origin = etOrigin.getText().toString();
                                           description = etDescription.getText().toString();
                                           if (name.trim().isEmpty())
                                           {
                                               etName.setError("Nama tidak boleh kosong");
                                           }
                                           else if (origin.trim().isEmpty())
                                           {
                                               etOrigin.setError("Daerah asal tidak boleh kosong");
                                           }
                                           else if (description.trim().isEmpty())
                                           {
                                               etDescription.setError("Deskripsi tidak boleh kosong");
                                           }
                                           else
                                           {
                                               changeCuisine();
                                           }
                                       }
                                     }
        );
    }
    private void changeCuisine()
    {
        APIRequestData ard = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = ard.ardUpdate(yId, name, origin, description);
        process.enqueue(new Callback<ResponseModel>()
                        {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response)
                            {
                                String code = response.body().getCode();
                                String message = response.body().getMessage();
                                Toast.makeText(ChangeActivity.this, "Kode: " + code + ", Pesan: " + message, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t)
                            {
                                Toast.makeText(ChangeActivity.this, "Gagal Menghubungi Server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
        );
    }
}