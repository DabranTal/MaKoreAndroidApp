package com.example.makoreandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makoreandroid.R;
import com.example.makoreandroid.entities.Message;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private final LayoutInflater mInflater;
    private List<Message> messages;

    private class senderMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        public senderMessageHolder(View view) {
            super(view);
            messageText = (TextView) itemView.findViewById(R.id.content_sender_message);
            timeText = (TextView) itemView.findViewById(R.id.time_sender_message);
        }

        void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setText(message.getCreated().substring(11,16));
        }
    }

    private class partnerMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        public partnerMessageHolder(View view) {
            super(view);
            messageText = (TextView) itemView.findViewById(R.id.content_partner_message);
            timeText = (TextView) itemView.findViewById(R.id.time_partner_message);
        }
        void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setText(message.getCreated().substring(11,16));
        }
    }


    public MessageListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sender_message, parent, false);
            return new senderMessageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.partner_message, parent, false);
            return new partnerMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = (Message) messages.get(position);

        // case the message is from the local user
        if (holder.getItemViewType() == 1)
            ((senderMessageHolder) holder).bind(message);
        // case the message is from the partner
        else
            ((partnerMessageHolder) holder).bind(message);

    }

    public String extractTime(String pattern) {
        return pattern.substring(11,16);
    }

    public void setMessages(List<Message> m) {
        messages = m;
        notifyDataSetChanged();
    }

    public void copyMessagesTo(List<Message> m) {
        m = messages;
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

    // Who sent the message
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) messages.get(position);
        if (message.isSent())
            return 1;
        else
            return 2;
    }

    public List<Message> getMessages() {
        return messages;
    }

}
