package com.example.desk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desk.R;
import com.example.desk.entity.Desk;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeskAdapter extends RecyclerView.Adapter<DeskAdapter.ViewHolder> {
    public interface Two{
        void RequestDesk(Desk desk);
    }
    private List<Desk> desks;
    private Context mContext;
    Two two;
    public void setTwo(Two two){
        this.two = two;
    }
    public DeskAdapter(Context mContext,List<Desk> desks) {
        this.desks = desks;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.deskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=viewHolder.getAdapterPosition();
                Desk desk=desks.get(p);
                if (two != null){
                    two.RequestDesk(desk);
                }

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Desk desk = desks.get(position);
        switch (desk.getState()){
            case "已签到":
                holder.deskImage.setImageResource(R.mipmap.seat_03);
                break;
            case "可用":
                holder.deskImage.setImageResource(R.mipmap.seat_01);
                break;
            case "已预约":
                holder.deskImage.setImageResource(R.mipmap.seat_04);
                break;
            case "不可用":
                holder.deskImage.setImageResource(R.mipmap.seat_03);
                break;
        }
        holder.deskNumber.setText(desk.getSeatid().substring(4));
    }

    @Override
    public int getItemCount() {
        return desks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.desk_image)
        ImageView deskImage;
        @BindView(R.id.desk_number)
        TextView deskNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
