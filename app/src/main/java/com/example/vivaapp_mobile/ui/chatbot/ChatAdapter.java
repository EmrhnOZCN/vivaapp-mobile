package com.example.vivaapp_mobile.ui.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> messages;
    static final int TYPE_USER = 1;
    static final int TYPE_BOT = 2;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case TYPE_USER:
                view = inflater.inflate(R.layout.item_user_message_chatbot, parent, false);
                return new UserMessageViewHolder(view);
            case TYPE_BOT:
                view = inflater.inflate(R.layout.item_bot_message_chatbot, parent, false);
                return new BotMessageViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        switch (holder.getItemViewType()) {
            case TYPE_USER:
                ((UserMessageViewHolder) holder).bind(message);
                break;
            case TYPE_BOT:
                ((BotMessageViewHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType();
    }

    static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;

        UserMessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.textViewMessage);
        }

        void bind(ChatMessage message) {
            messageTextView.setText(message.getText());
        }
    }

    static class BotMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;
        private Button buttonYes;
        private Button buttonNo;

        BotMessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.textViewMessage);
            buttonYes = itemView.findViewById(R.id.buttonYes);
            buttonNo = itemView.findViewById(R.id.buttonNo);
        }

        void bind(ChatMessage message) {
            messageTextView.setText(message.getText());

            if (message.getText().contains("Malzemeler Sepete Eklensin mi?")) {
                buttonYes.setVisibility(View.VISIBLE);
                buttonNo.setVisibility(View.VISIBLE);

                buttonYes.setOnClickListener(v -> {
                    // Handle "Evet" button click
                });

                buttonNo.setOnClickListener(v -> {
                    // Handle "Hayır" button click
                });
            } else {
                buttonYes.setVisibility(View.GONE);
                buttonNo.setVisibility(View.GONE);
            }
        }
    }
}

