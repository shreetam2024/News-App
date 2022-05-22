package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickinterface {

    private RecyclerView NewsRV,CategoryRV;
    private ArrayList<Articles> ArticlesArrayList;
    private ArrayList<CategoryRVModal> CategoryRVModalArrayList;
    private CategoryRVAdapter CategoryRVAdapter;
    private NewsRVAdapter NewsRVAdapter;
    private ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsRV = findViewById(R.id.idRVNews);
        CategoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBloading);
        ArticlesArrayList = new ArrayList<>();
        CategoryRVModalArrayList = new ArrayList<>();
        NewsRVAdapter = new NewsRVAdapter(ArticlesArrayList,this);
        CategoryRVAdapter = new CategoryRVAdapter(CategoryRVModalArrayList,this,this::onCategoryClick);
        NewsRV.setLayoutManager(new LinearLayoutManager(this));
        NewsRV.setAdapter(NewsRVAdapter);
        CategoryRV.setAdapter(CategoryRVAdapter);
        getCategories();
        getNews("All");
        NewsRVAdapter.notifyDataSetChanged();


    }

    private void getCategories(){
        CategoryRVModalArrayList.add(new CategoryRVModal("All","https://images.unsplash.com/reserve/Af0sF2OS5S5gatqrKzVP_Silhoutte.jpg?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGFsbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\n"));
        CategoryRVModalArrayList.add(new CategoryRVModal("Technology","https://media.istockphoto.com/photos/data-scientists-male-programmer-using-laptop-analyzing-and-developing-picture-id1295900106?b=1&k=20&m=1295900106&s=170667a&w=0&h=kQ2UWilU4Bild5aP03CaF65gMbSI-chG--7dd2oS8GM=\n"));
        CategoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c2NpZW5jZSUyMGltYWdlc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\n"));
        CategoryRVModalArrayList.add(new CategoryRVModal("Sports","https://media.istockphoto.com/photos/sports-equipment-on-floor-picture-id1136317339?b=1&k=20&m=1136317339&s=170667a&w=0&h=BVVxuGb9kk50gEAHsgK4LYpuqwQ_InkEy1GGbjRBGWo=\n"));
        CategoryRVModalArrayList.add(new CategoryRVModal("General","https://media.istockphoto.com/photos/colorful-store-building-exteriors-picture-id173607914?b=1&k=20&m=173607914&s=170667a&w=0&h=UJ6-AXCFqbjo3u_7GCFGWlGxI3TGn4WUAesmkJJUIpE=\n"));
        CategoryRVModalArrayList.add(new CategoryRVModal("Business","https://images.unsplash.com/photo-1444653614773-995cb1ef9efa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzd8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60\n"));
        CategoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://images.unsplash.com/photo-1514525253161-7a46d19cd819?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\n"));
        CategoryRVModalArrayList.add(new CategoryRVModal("Health","https://images.unsplash.com/photo-1453847668862-487637052f8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTc2fHxoZWFsdGh8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60\n"));
        CategoryRVAdapter.notifyDataSetChanged();


    }
    private void getNews(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        ArticlesArrayList.clear();

        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category= " + category + " &apikey=832bfcb46c5b4dc699a0b450b5f105c7";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language-en&apikey=832bfcb46c5b4dc699a0b450b5f105c7";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if (category.equals("All")) {
            call = retrofitAPI.getAllNews(url);
        } else {
            call = retrofitAPI.getNewsByCategory(categoryURL);

        }
        call.enqueue(new Callback<NewsModal>() {

            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal NewsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = NewsModal.getArticles();
                for (int i = 0; i<articles.size(); i++){
                    ArticlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                NewsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        String category;
        category = CategoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}