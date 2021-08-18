package com.dentalvalet.dentalvaletApp.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dentalvalet.dentalvaletApp.Activities.Appointments;
import com.dentalvalet.dentalvaletApp.DentalValet.MyApplication;
import com.dentalvalet.dentalvaletApp.Extras.Keys;
import com.dentalvalet.dentalvaletApp.Model.AppointentModel;
import com.dentalvalet.dentalvaletApp.Model.RewardsModel;
import com.dentalvalet.dentalvaletApp.Model.ServicesModel;
import com.dentalvalet.dentalvaletApp.Network.VolleySingleton;
import com.dentalvalet.www.dentalvaletApp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 14-Dec-15.
 */
public class RewardViewAdaptor extends RecyclerView.Adapter<RewardViewAdaptor.MyViewHolder> {

    private clickListener clickListener;
    Context context;
    private final LayoutInflater inflator;
    private ImageLoader imageLoader;
    RequestQueue queue;
    ArrayList<RewardsModel> rewardInfo = new ArrayList<>();

    public RewardViewAdaptor(Context context) {
        this.context = context;
        inflator = LayoutInflater.from(context);
    }

    public void setRewardInfo(ArrayList<RewardsModel> rewardInfo) {
        this.rewardInfo = rewardInfo;

        notifyItemRangeChanged(0, rewardInfo.size());
    }


    public void setClickListener(clickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_reward_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // ListDataItem current =data.get(position);

        RewardsModel currentInfo =rewardInfo.get(position);
        holder.rewardsModel = currentInfo;
        holder.title.setText(currentInfo.getTitle());
        holder.scoreRequired.setText(currentInfo.getScoreRequired());
        holder.descp.setText(currentInfo.getDescription());

       imageLoader = VolleySingleton.getsInstance().getImageLoader();
        String imageUrl = Keys.URL_REWARD_IMAGES +currentInfo.getImage();
        if (imageUrl != null) {
            Log.v("MOULIA", "Reward url" + imageUrl);
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
                        holder.progress.setVisibility(View.GONE);
                        Bitmap bm  = response.getBitmap();
                        holder.image.setImageBitmap(bm);
                        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
                        holder.image.setVisibility(View.VISIBLE);
                    } else {
                        //set Some loader image here
                    }

                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.progress.setVisibility(View.GONE);
                    holder.image.setVisibility(View.VISIBLE);

                }
            });
        }


    }


    @Override
    public int getItemCount() {


        //   return data.size();
        return rewardInfo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;

        TextView scoreRequired;
        ImageView image;
        TextView descp;
        ProgressBar progress;

        Button redeme;

        RewardsModel rewardsModel;

        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.reward_title);
            scoreRequired = (TextView) itemView.findViewById(R.id.reward_score_required);
            descp = (TextView) itemView.findViewById(R.id.reward_description);
            image = (ImageView) itemView.findViewById(R.id.reward_image);
            redeme = (Button) itemView.findViewById(R.id.reward_redeme);
            progress = (ProgressBar) itemView.findViewById(R.id.reward_banner_progress);

            redeme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        }

        @Override
        public void onClick(View v) {

        }

    }


    public interface clickListener {
        public void itemCicked(View v, int position);
    }
}
