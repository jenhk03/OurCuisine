package com.example.ourcuisine.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ourcuisine.Model.CuisineModel;
import com.example.ourcuisine.R;
import java.util.List;

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
        holder.tvName.setText(cm.getId());
        holder.tvOrigin.setText(cm.getId());
        holder.tvDescription.setText(cm.getId());
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
        }
    }
}