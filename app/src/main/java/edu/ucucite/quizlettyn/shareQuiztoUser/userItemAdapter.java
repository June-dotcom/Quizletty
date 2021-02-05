package edu.ucucite.quizlettyn.shareQuiztoUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.ucucite.quizlettyn.R;

public class userItemAdapter extends RecyclerView.Adapter<userItemAdapter.shareUserViewHolder> {
    private ArrayList<UserQueryInfo> userItemsShare;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onShareToUser(int index);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class shareUserViewHolder extends RecyclerView.ViewHolder {
        public TextView user_ed;
        public TextView user_email;
        public CircleImageView profile_image;
        public ImageView user_provider;
        public CardView card_user_item;

        public shareUserViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            user_ed = v.findViewById(R.id.recylcer_user_name);
            user_email = v.findViewById(R.id.recycler_user_share_email);
            profile_image = v.findViewById(R.id.recycler_user_profile);
            user_provider = v.findViewById(R.id.recycler_user_provider);
            card_user_item = v.findViewById(R.id.card_container_user);

            card_user_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int index = getAdapterPosition();
                        listener.onShareToUser(index);

                    }
                }
            });
        }
    }

    public userItemAdapter(ArrayList<UserQueryInfo> exampleList) {
        userItemsShare = exampleList;
    }

    @Override
    public userItemAdapter.shareUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user_share, parent, false);
        userItemAdapter.shareUserViewHolder evh = new userItemAdapter.shareUserViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(final shareUserViewHolder holder, final int position) {
        UserQueryInfo UserQueryInfo = userItemsShare.get(position);
        holder.user_ed.setText(UserQueryInfo.getName());
        holder.user_email.setText(UserQueryInfo.getEmail());
        if (UserQueryInfo.getProvider_data().equals("facebook.com")) {
            holder.user_provider.setImageResource(R.drawable.ic_baseline_facebook_24);
        } else if (UserQueryInfo.getProvider_data().equals("google.com")) {
            holder.user_provider.setImageResource(R.drawable.ic_google__g__logo);
        }
        Picasso.get().load(UserQueryInfo.getProfile_uri()).into(holder.profile_image);
//        Glide.with(holder.itemView).load(UserQueryInfo.getProfile_uri()).into(holder.profile_image);


    }

    @Override
    public int getItemCount() {
        return userItemsShare.size();
    }

}