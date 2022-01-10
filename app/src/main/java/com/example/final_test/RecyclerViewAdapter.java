package com.example.final_test;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Activity> mDatas;
    public RecyclerViewAdapter(List<Activity> data) {
        this.mDatas = data;
    }

    public void clearDevice(){
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    public void addDevice(List<Activity> arrayList){
        this.mDatas = arrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvid,tvname,tvtime;
        Button info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tv_id);
            tvname = itemView.findViewById(R.id.tv_na);
            tvtime = itemView.findViewById(R.id.tv_time);
            info = itemView.findViewById(R.id.button_info);
        }

        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_infoFragment);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Activity value = mDatas.get(position);
        holder.tvid.setText("活動序號：" + mDatas.get(position).getNum());
        holder.tvname.setText("活動名稱" + mDatas.get(position).getName());
        holder.tvtime.setText("活動時間：" + mDatas.get(position).getTime());
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                menuFragmentDirections.ActionMenuFragmentToInfoFragment action = menuFragmentDirections.actionMenuFragmentToInfoFragment(value.getNum(),value.getName(),value.getTime(),value.getAdress(),value.getPriority(),value.getRecord());
                navController.navigate(action);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}
