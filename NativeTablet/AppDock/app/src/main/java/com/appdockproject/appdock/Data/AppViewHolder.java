package com.appdockproject.appdock.Data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdockproject.appdock.R;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by jangerhard on 02-May-17.
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    public ImageView logo;
    TextView appName;

    public void setAppName(String name){
        appName.setText(name);
    }
    public AppViewHolder(View itemView) {
        super(itemView);

        logo = (ImageView) itemView.findViewById(R.id.appLogo);
        appName = (TextView) itemView.findViewById(R.id.appName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
    }
    private AppViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(AppViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
