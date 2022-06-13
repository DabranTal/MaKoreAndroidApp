package com.example.makoreandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.makoreandroid.R;
import com.example.makoreandroid.entities.RemoteUser;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<RemoteUser> {
    LayoutInflater inflater;
    private ArrayList<RemoteUser> rList;

    public CustomListAdapter(Context ctx, ArrayList<RemoteUser>remoteUserArrayList) {
        super(ctx, R.layout.custom_list_item, remoteUserArrayList);
        this.inflater = LayoutInflater.from(ctx);
        rList = new ArrayList<>(remoteUserArrayList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            ArrayList<RemoteUser> suggestions = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0)
                suggestions.addAll(rList);
            else {
                String filterPattern = charSequence.toString();
                for(RemoteUser r: rList) {
                    if(r.getId().contains(filterPattern))
                        suggestions.add(r);
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((RemoteUser)resultValue).getId();
        }

    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RemoteUser remote = getItem(position);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView remoteName = convertView.findViewById(R.id.contact_name);
        TextView lastMessage = convertView.findViewById(R.id.last_message);
        TextView time = convertView.findViewById(R.id.time);

        imageView.setImageResource(remote.getAvatar());
        remoteName.setText(remote.getId());
        lastMessage.setText(remote.getLast());
        time.setText(remote.getLastdate());
        //notifyDataSetChanged();
        return convertView;
    }

    public int getItemCount() {
        if (rList != null) {
            return rList.size();
        } else {
            return 0;
        }
    }

    public ArrayList<RemoteUser> getRemoteUser() {
        return rList;
    }

    public void setAdapter(ArrayList<RemoteUser>r) {
        this.rList = r;
    }
}