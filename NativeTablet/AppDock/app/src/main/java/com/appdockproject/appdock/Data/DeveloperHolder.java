package com.appdockproject.appdock.Data;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdockproject.appdock.R;

import static com.appdockproject.appdock.R.string.app;

/**
 * Created by jangerhard on 02-May-17.
 */

public class DeveloperHolder extends RecyclerView.ViewHolder{

    public ImageView devPic;
    public ImageView appLogo;
    private TextView developers;

    public void setDevPic(ImageView iv) {
        this.devPic = iv;
    }

    public void setAppLogo(ImageView iv) {
        this.appLogo = iv ;
    }

    public void setDevelopers(Developer dev) {

        StringBuilder sb = new StringBuilder();

        sb.append(dev.getDev1());

        if (dev.getDev2() != null)
            sb.append("\n").append(dev.getDev2());

        if (dev.getDev3() != null)
            sb.append("\n").append(dev.getDev3());

        this.developers.setText(sb.toString());
    }

    public DeveloperHolder(View itemView) {
        super(itemView);
        devPic = (ImageView) itemView.findViewById(R.id.developerIcon);
        appLogo = (ImageView) itemView.findViewById(R.id.devAppLogo);
        developers = (TextView) itemView.findViewById(R.id.devNames);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
    }

    private DeveloperHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(DeveloperHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
