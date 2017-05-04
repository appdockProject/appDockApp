package com.appdockproject.appdock.Data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdockproject.appdock.R;

/**
 * Created by jangerhard on 04-May-17.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public ImageView vidLogo;
    TextView vidName;
    public Class vidClass;

    public void setVidName(String name){
        vidName.setText(name);
    }

    public VideoViewHolder(View itemView) {
        super(itemView);

        vidLogo = (ImageView) itemView.findViewById(R.id.vidIcon);
        vidName = (TextView) itemView.findViewById(R.id.vidName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });

    }

    private VideoViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(VideoViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
