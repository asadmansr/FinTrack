package com.asadmansoor.fintrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ListView mSearchList;
    RecyclerView mTrendingList;
    RelativeLayout mBackButton;
    EditText mSearchEditText;
    ArrayList<String> mHorizontalList;
    ArrayList<String> mSearchQuery;
    HorizontalRecyclerAdapter mHorizontalRecyclerAdapter;
    ArrayAdapter<String> adapter;
    int position;
    String reference;

    String[] values = new String[] {
            "Argentina Peso - ARS",
            "Brazilian Real - BRL",
            "Canadian Dollar - CAD",
            "Cuban Peso - CUP",
            "European Euro - EUR",
            "Indian Rupee - INR",
            "Saudi Arabian Riyal - SAR",
            "Serbian Dinar - RSD",
            "Swedish Krona - SEK",
            "United States Dollar - USD"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchList = (ListView) findViewById(R.id.search_lv);
        mSearchEditText = (EditText) findViewById(R.id.search_et);
        mTrendingList = (RecyclerView) findViewById(R.id.trending_rv);
        mBackButton = (RelativeLayout) findViewById(R.id.back_button_rl);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                position = extras.getInt("position",0);
                reference = extras.getString("reference","");
            }
        }



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, DashboardActivity.class);
                intent.putExtra("result",false);
                startActivity(intent);
                finish();
            }
        });

        mSearchEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        mSearchQuery = new ArrayList<>();
        mHorizontalList = new ArrayList<>();


        loadInitialValues();

        mHorizontalList.add("CAD");
        mHorizontalList.add("USD");
        mHorizontalList.add("BRL");
        mHorizontalList.add("SAR");
        mHorizontalList.add("INR");

        mHorizontalRecyclerAdapter = new HorizontalRecyclerAdapter(mHorizontalList, SearchActivity.this, position, reference);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mSearchQuery);

        mSearchList.setAdapter(adapter);

        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mTrendingList.setLayoutManager(horizontalLayoutManager);
        mTrendingList.setAdapter(mHorizontalRecyclerAdapter);



        mSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, DashboardActivity.class);
                intent.putExtra("result",true);
                intent.putExtra("position",position);
                intent.putExtra("reference",reference);
                intent.putExtra("result_text", (adapterView.getItemAtPosition(i)).toString());
                startActivity(intent);
                finish();
            }
        });

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("test",charSequence.toString());
                updateSearchList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateSearchList(String searchQuery){
        mSearchQuery.clear();

        for (int i=0; i<values.length; i++){
            String currencyData = (values[i]).toLowerCase();
            if (currencyData.startsWith(searchQuery)){
                mSearchQuery.add(currencyData);
            }
        }

        adapter.notifyDataSetChanged();
        mSearchList.invalidate();
    }

    private void loadInitialValues(){
        mSearchQuery.add("Argentina Peso - ARS");
        mSearchQuery.add("Brazilian Real - BRL");
        mSearchQuery.add("Canadian Dollar - CAD");
        mSearchQuery.add("Cuban Peso - CUP");
        mSearchQuery.add("European Euro - EUR");

        mSearchQuery.add("Indian Rupee - INR");
        mSearchQuery.add("Saudi Arabian Riyal - SAR");
        mSearchQuery.add("Serbian Dinar - RSD");
        mSearchQuery.add("Swedish Krona - SEK");
        mSearchQuery.add("United States Dollar - USD");

    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SearchActivity.this, DashboardActivity.class);
        intent.putExtra("result",false);
        startActivity(intent);
        finish();

    }
}
