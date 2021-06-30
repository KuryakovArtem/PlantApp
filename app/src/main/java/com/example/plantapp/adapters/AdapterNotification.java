package com.example.plantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantapp.R;
import com.example.plantapp.models.ModelNotification;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.HolderNotification> {

    private Context context;
    private ArrayList<ModelNotification> notificationArrayList;

    public AdapterNotification(Context context, ArrayList<ModelNotification> notificationArrayList) {
        this.context = context;
        this.notificationArrayList = notificationArrayList;
    }

    @NonNull
    @Override
    public HolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_notification, parent, false);

        return new HolderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotification.HolderNotification holder, int position) {
        ModelNotification modelNotification = notificationArrayList.get(position);
        String name = modelNotification.getsName();
        String notification = modelNotification.getNotification();
        String image = modelNotification.getsImage();
        String timestamp = modelNotification.getTimestamp();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String pTime = DateFormat.getTimeInstance().format(calendar).toString();
//                format("dd/MM/yyyy hh:mm aa", calendar).toString();
        holder.plantName.setText(name);
        holder.notificationTv.setText(notification);
        holder.timeTv.setText(pTime);

        }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    //holder class for views of row_notifications.xml
    class HolderNotification extends RecyclerView.ViewHolder {
        ImageView plantIv;
        TextView plantName, notificationTv, timeTv;


        public HolderNotification(@NonNull View itemView) {
            super(itemView);
            plantIv = itemView.findViewById(R.id.plant_image);
            plantName = itemView.findViewById(R.id.plant_name);
            notificationTv = itemView.findViewById(R.id.notification);
            timeTv = itemView.findViewById(R.id.time);
        }
    }
}
