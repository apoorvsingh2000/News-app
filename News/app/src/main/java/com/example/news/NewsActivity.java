package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    //loading spinner
    private ProgressBar mLoadingSpinner;

    //adapter
    private NewsAdapter mNewsAdapter;

    //Log tag
    private static final String LOG_TAG = NewsActivity.class.getName();

    //recycler view
    private RecyclerView mRecyclerView;

    //swipe refresh layout
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //news list
    List<News> mNewsList;

    //news url
    private static String NEWS_URL = "http://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        //loading spinner
        mLoadingSpinner = findViewById(R.id.loading_spinner);

        //No internet gif
        final ImageView noInternet = findViewById(R.id.gif);

        //sports
        ImageView sports, business, global, entertainment, politics, headlines, health;
        sports = findViewById(R.id.sports);
        business = findViewById(R.id.business);
        global = findViewById(R.id.global);
        entertainment = findViewById(R.id.entertainment);
        politics = findViewById(R.id.politics);
        health = findViewById(R.id.health);
        headlines = findViewById(R.id.headlines);

        //category title
        final TextView categoryTitle = findViewById(R.id.category_title);
        //swipe refresh layout
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        //create a custom array adapter whose source is list_item
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());


        //Check Internet connectivity
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            //no internet connection case
            noInternet.setVisibility(View.GONE);

        }
        else {
            mLoadingSpinner.setVisibility(View.GONE);
        }

        //finding the recycler view list on which adapter is to be set up
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadRecyclerViewData(NEWS_URL);

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        /**
                         if (activeNetwork == null && !activeNetwork.isConnectedOrConnecting()) {
                         //toast message for no internet connection
                         Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
                         }
                         **/
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        loadRecyclerViewData(NEWS_URL);
                        mNewsAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        //headlines category
        headlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingSpinner.setVisibility(View.VISIBLE);

                //headlines url
                NEWS_URL = "http://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";
                categoryTitle.setText(R.string.top_headlines);
                categoryTitle.setAllCaps(true);
                loadRecyclerViewData(NEWS_URL);

            }
        });

        //sports category
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingSpinner.setVisibility(View.VISIBLE);

                //sports url
                NEWS_URL = "http://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";
                categoryTitle.setText(R.string.sports);
                categoryTitle.setAllCaps(true);
                loadRecyclerViewData(NEWS_URL);

            }
        });

        //business category
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingSpinner.setVisibility(View.VISIBLE);

                //business url
                NEWS_URL = "http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";
                categoryTitle.setText(R.string.business);
                categoryTitle.setAllCaps(true);
                loadRecyclerViewData(NEWS_URL);

            }
        });

        //entertainment category
        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingSpinner.setVisibility(View.VISIBLE);

                //entertainment url
                NEWS_URL = "http://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";
                categoryTitle.setText(R.string.entertainment);
                categoryTitle.setAllCaps(true);
                loadRecyclerViewData(NEWS_URL);

            }
        });

        //health category
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingSpinner.setVisibility(View.VISIBLE);

                //health url
                NEWS_URL  = "http://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";
                categoryTitle.setText(R.string.health);
                categoryTitle.setAllCaps(true);
                loadRecyclerViewData(NEWS_URL);

            }
        });

        //global category
        global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingSpinner.setVisibility(View.VISIBLE);

                //global url
                NEWS_URL = "http://newsapi.org/v2/everything?q=global&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";
                categoryTitle.setText(R.string.global);
                categoryTitle.setAllCaps(true);
                loadRecyclerViewData(NEWS_URL);

            }
        });

        //politics category
        politics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingSpinner.setVisibility(View.VISIBLE);

                //politics url
                NEWS_URL = "http://newsapi.org/v2/top-headlines?country=in&category=politics&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";
                categoryTitle.setText(R.string.politics);
                categoryTitle.setAllCaps(true);
                loadRecyclerViewData(NEWS_URL);

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void loadRecyclerViewData(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         mNewsList = new ArrayList<>();
                        try {
                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray jsonArray = jsonRootObject.optJSONArray("articles");

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject newsObject = jsonArray.getJSONObject(i);
                                String title = newsObject.getString("title");
                                String description = newsObject.getString("description");
                                String url = newsObject.getString("url");
                                String urlToImage = newsObject.getString("urlToImage");
                                String publishedAt = newsObject.getString("publishedAt");
                                String author = newsObject.getString("author");

                                News news =new News(title, description, author, url, publishedAt, urlToImage);
                                mNewsList.add(news);
                            }
                            mLoadingSpinner.setVisibility(View.GONE);
                            mNewsAdapter = new NewsAdapter(getApplicationContext(), mNewsList);
                            mRecyclerView.setAdapter(mNewsAdapter);
                        } catch (JSONException e) {
                            // If an error is thrown when executing any of the above statements in the "try" block,
                            // catch the exception here, so the app doesn't crash. Print a log message
                            // with the message from the exception.
                            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mNewsList.remove(viewHolder.getAdapterPosition());
            mNewsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        }
    };
}