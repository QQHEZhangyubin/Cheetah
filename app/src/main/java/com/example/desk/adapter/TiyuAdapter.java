package com.example.desk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.entity.TiYu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TiyuAdapter extends RecyclerView.Adapter<TiyuAdapter.ViewHolder> {
    private Context mContext;
    private List<TiYu> tiYus;

    public TiyuAdapter(Context mContext, List<TiYu> tiYus) {
        this.mContext = mContext;
        this.tiYus = tiYus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiyu, parent, false);
        //final DeskAdapter.ViewHolder viewHolder=new DeskAdapter.ViewHolder(view);
        ViewHolder viewholder = new ViewHolder(view);
        viewholder.tvTiyuItemText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int p = viewholder.getAdapterPosition();
                TiYu ti = tiYus.get(p);
                Toast.makeText(mContext,"卡ID:" +ti.getCardId(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TiYu tiyuitem = tiYus.get(position);
        holder.tvTiyuItemText.setText(tiyuitem.getD());
        if (tiyuitem.getIsvalid().equals("有效")){
            holder.ivTiyuItemImg.setImageResource(R.mipmap.rxlikeyou);
        }else {
            int m = mContext.getResources().getColor(R.color.red);
            holder.tvTiyuItemText.setTextColor(m);
            holder.ivTiyuItemImg.setImageResource(R.mipmap.psmile);
        }
    }

    @Override
    public int getItemCount() {
        return tiYus.size();
    }


    static class ViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_tiyu_item_text)
        TextView tvTiyuItemText;
        @BindView(R.id.iv_tiyu_item_img)
        ImageView ivTiyuItemImg;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
