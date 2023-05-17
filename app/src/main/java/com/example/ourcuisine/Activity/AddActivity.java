package com.example.ourcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
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

public class AddActivity extends AppCompatActivity
{
    private EditText etName, etOrigin, etDescription;
    private Button btnSave;
    private String name, origin, description;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etName = findViewById(R.id.et_name);
        etOrigin = findViewById(R.id.et_origin);
        etDescription = findViewById(R.id.et_description);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener()
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
                    addCuisine();
                }
            }
        }
        );
    }
    private void addCuisine()
    {
        APIRequestData ard = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = ard.ardCreate(name, origin, description);
        process.enqueue(new Callback<ResponseModel>()
        {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response)
            {
                String code = response.body().getCode();
                String message = response.body().getMessage();
                Toast.makeText(AddActivity.this, "Kode: " + code + ", Pesan: " + message, Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t)
            {
                Toast.makeText(AddActivity.this, "Gagal Menghubungi Server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );
    }
}