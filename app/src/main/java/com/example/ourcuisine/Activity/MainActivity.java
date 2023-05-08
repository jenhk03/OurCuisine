package com.example.ourcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.ourcuisine.API.APIRequestData;
import com.example.ourcuisine.API.RetroServer;
import com.example.ourcuisine.Adapter.CuisineAdapter;
import com.example.ourcuisine.Model.CuisineModel;
import com.example.ourcuisine.Model.ResponseModel;
import com.example.ourcuisine.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView rvCuisine;
    private FloatingActionButton fabAdd;
    private ProgressBar pbCuisine;
    private RecyclerView.Adapter adCuisine;
    private RecyclerView.LayoutManager lmCuisine;
    private List<CuisineModel> listCuisine = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvCuisine = findViewById(R.id.rv_cuisine);
        fabAdd = findViewById(R.id.fab_add);
        pbCuisine = findViewById(R.id.pb_cuisine);
        lmCuisine = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCuisine.setLayoutManager(lmCuisine);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        retrieveCuisine();
    }

    public void retrieveCuisine()
    {
        pbCuisine.setVisibility(View.VISIBLE);
        APIRequestData ard = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = ard.ardRetrieve();
        process.enqueue(new Callback<ResponseModel>()
        {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response)
            {
                String code = response.body().getCode();
                String message = response.body().getMessage();
                listCuisine = response.body().getData();
                adCuisine = new CuisineAdapter(MainActivity.this, listCuisine);
                rvCuisine.setAdapter(adCuisine);
                adCuisine.notifyDataSetChanged();
                pbCuisine.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbCuisine.setVisibility(View.GONE);
            }
        }
        );
    }
}