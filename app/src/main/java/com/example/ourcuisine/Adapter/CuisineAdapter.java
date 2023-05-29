package com.example.ourcuisine.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ourcuisine.API.APIRequestData;
import com.example.ourcuisine.API.RetroServer;
import com.example.ourcuisine.Activity.ChangeActivity;
import com.example.ourcuisine.Activity.MainActivity;
import com.example.ourcuisine.Model.CuisineModel;
import com.example.ourcuisine.Model.ResponseModel;
import com.example.ourcuisine.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.VHCuisine>
{
    private Context ctx;
    private List<CuisineModel> listCuisine;
    public CuisineAdapter(Context ctx, List<CuisineModel> listCuisine)
    {
        this.ctx = ctx;
        this.listCuisine = listCuisine;
    }
    @NonNull
    @Override
    public VHCuisine onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cuisine, parent, false);
        return new VHCuisine(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VHCuisine holder, int position)
    {
        CuisineModel cm = listCuisine.get(position);
        holder.tvId.setText(cm.getId());
        holder.tvName.setText(cm.getName());
        holder.tvOrigin.setText(cm.getOrigin());
        holder.tvDescription.setText(cm.getDescription());
    }
    @Override
    public int getItemCount()
    {
        return listCuisine.size();
    }
    public class VHCuisine extends RecyclerView.ViewHolder
    {
        TextView tvId, tvName, tvOrigin, tvDescription;
        public VHCuisine(@NonNull View itemView)
        {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvOrigin = itemView.findViewById(R.id.tv_origin);
            tvDescription = itemView.findViewById(R.id.tv_description);
            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(ctx);
                    message.setTitle("Perhatian");
                    message.setMessage("Operasi apa yang akan dilakukan?");
                    message.setCancelable(true);
                    message.setNegativeButton("Hapus", new DialogInterface.OnClickListener()
                            {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            delCuisine(tvId.getText().toString());
                            dialog.dismiss();
                        }
                    }
                    );
                    message.setPositiveButton("Ubah", new DialogInterface.OnClickListener()
                            {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent move = new Intent(ctx, ChangeActivity.class);
                            move.putExtra("xId", tvId.getText().toString());
                            move.putExtra("xName", tvName.getText().toString());
                            move.putExtra("xOrigin", tvOrigin.getText().toString());
                            move.putExtra("xDescription", tvDescription.getText().toString());
                            ctx.startActivity(move);
                        }
                    }
                    );
                    message.show();
                    return false;
                }
            }
            );
        }
        private void delCuisine(String idCuisine)
        {
            APIRequestData ard = RetroServer.connectRetrofit().create(APIRequestData.class);
            Call<ResponseModel> process = ard.ardDelete(idCuisine);
            process.enqueue(new Callback<ResponseModel>()
            {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response)
                {
                    String code = response.body().getCode();
                    String message = response.body().getMessage();
                    Toast.makeText(ctx, "Kode: " + code + ", Pesan: " + message, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveCuisine();
                }
                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t)
                {
                    Toast.makeText(ctx, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                }
            }
            );
        }
    }
}