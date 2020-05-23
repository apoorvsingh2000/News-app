package com.example.news2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context mContext;
    private List<News> mNewsList;

    public NewsAdapter(Context context, List<News> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, null, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        final News currentNews = mNewsList.get(position);
        Log.v("Called:", "Populating views");
        //Populating the respective views
        holder.titleTextView.setText(currentNews.getTitle());
        holder.descriptionTextView.setText(String.format("%s...", currentNews.getDescription()));
        holder.dateTextView.setText(currentNews.getDate());

        if (currentNews.getAuthor() != "null") {
            holder.sourceTextView.setText(currentNews.getAuthor());
        }
        else holder.sourceTextView.setText(R.string.default_author);

        if (!currentNews.getUrlToImage().isEmpty() && currentNews.getUrlToImage() != "null") {
            Log.v("URL is: ", currentNews.getUrlToImage());
            Picasso.with(mContext).load(currentNews.getUrlToImage()).into(holder.imageView);
        }
        else holder.imageView.setImageResource(R.drawable.image_view);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast message for opening the web browser
                Toast.makeText(v.getContext(),"Redirecting to the news website",Toast.LENGTH_LONG).show();

                //intent to open the web browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getUrl()));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTextView, descriptionTextView, sourceTextView, dateTextView;
        private ImageView imageView;
        private CardView cardView;

        public NewsViewHolder(@NonNull View itemView) {

            super(itemView);

            //Finding the views
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.description);
            sourceTextView = itemView.findViewById(R.id.author);
            imageView = itemView.findViewById(R.id.image_view);
            dateTextView = itemView.findViewById(R.id.date);

            //card view
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
