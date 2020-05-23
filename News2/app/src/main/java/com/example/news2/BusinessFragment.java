package com.example.news2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends Fragment {


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
    private List<News> mNewsList;

    //news url
    private static String NEWS_URL = "http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=1f81626fb6f6456bbc0fed7fd9f74805";

    public BusinessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        //loading spinner
        mLoadingSpinner = rootView.findViewById(R.id.loading_spinner);

        //swipe refresh layout
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);

        //create a custom array adapter whose source is list_item
        mNewsAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());

        //No internet
        ImageView noInternet = rootView.findViewById(R.id.no);

        //Check Internet connectivity
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            //no internet connection case
            noInternet.setVisibility(View.GONE);

        }
        else {
            mLoadingSpinner.setVisibility(View.GONE);
        }

        loadRecyclerViewData(NEWS_URL);

        //finding the recycler view list on which adapter is to be set up
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        return rootView;
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
                                String date = publishedAt.substring(0, 10);
                                String author = newsObject.getString("author");

                                News news =new News(title, description, author, url, date, urlToImage);
                                mNewsList.add(news);
                            }
                            Log.v("What's happening?????", "Spinner");
                            mLoadingSpinner.setVisibility(View.GONE);
                            mNewsAdapter = new NewsAdapter(getActivity(), mNewsList);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}

