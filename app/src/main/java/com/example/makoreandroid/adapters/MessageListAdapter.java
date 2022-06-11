package com.example.makoreandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.makoreandroid.R;
import com.example.makoreandroid.entities.Message;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessagesViewHolder> {

    class MessagesViewHolder extends RecyclerView.ViewHolder {
        private final TextView content;
        private final TextView time;

        private MessagesViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content_sender_message);
            time = itemView.findViewById(R.id.time_sender_message);

        }
    }

    private final LayoutInflater mInflater;
    private List<Message> messages;

    public MessageListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.sender_message, parent, false);
        return new MessagesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.content.setText(current.getContent());
            holder.time.setText(current.getTime());
            int a = 5;
        }
    }

    public void setMessages(List<Message> m) {
        messages = m;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        } else {
            return 0;
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

}
