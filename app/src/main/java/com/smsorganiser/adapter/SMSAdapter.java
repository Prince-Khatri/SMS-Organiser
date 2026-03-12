package com.smsorganiser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.smsorganiser.R;
import com.smsorganiser.model.SMSMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.SmsViewHolder> {

    private List<SMSMessage> smsList;
    private Context context;

    public SMSAdapter(Context context, List<SMSMessage> smsList) {
        this.context = context;
        this.smsList = smsList;
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.sms_items, parent, false);

        return new SmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {

        SMSMessage sms = smsList.get(position);

        holder.txtSender.setText("Sender name");
        holder.txtBody.setText(sms.getSmsBody());
        holder.chipCategory.setText(sms.getSmsCategory());

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String time = sdf.format(new Date(1710235200000L));
        holder.txtTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public static class SmsViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSender;
        TextView txtSender;
        TextView txtBody;
        TextView txtTime;
        Chip chipCategory;

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSender = itemView.findViewById(R.id.senderImage);
            txtSender = itemView.findViewById(R.id.senderName);
            txtBody = itemView.findViewById(R.id.smsMessageBody);
            txtTime = itemView.findViewById(R.id.smsTimeStamp);
            chipCategory = itemView.findViewById(R.id.smsCategory);
        }
    }
}