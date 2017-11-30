package com.asadmansoor.fintrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText mSearchEditText;
    TextView mComingSoonText;
    LinearLayout mDashboardContainer;
    RecyclerView mFavourites;
    ArrayList<String> mFavouriteList;
    FavouritesRecyclerAdapter mFavouritesRecyclerAdapter;
    FloatingActionButton fab;
    boolean mState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingToolbar fabToolbar = (FloatingToolbar) findViewById(R.id.floatingToolbar);
        mSearchEditText = (EditText) findViewById(R.id.search_et);
        mFavourites = (RecyclerView) findViewById(R.id.favorites_rv);

        mComingSoonText = (TextView) findViewById(R.id.coming_soon_tv);
        mDashboardContainer = (LinearLayout) findViewById(R.id.dashboard_container_ll);

        mSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mState) {
                    Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

        fabToolbar.attachFab(fab);
        fabToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.toolbar_bitcoin){
                    fab.setImageResource(R.drawable.ic_account_balance_wallet_white_24dp);
                    mComingSoonText.setVisibility(View.VISIBLE);
                    mDashboardContainer.setVisibility(View.GONE);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorBitcoinBar));
                    mSearchEditText.setHint("Search for cryptocurrencies...");
                    mSearchEditText.setEnabled(false);

                    mState = false;

                } else if (id == R.id.toolbar_stock){
                    fab.setImageResource(R.drawable.ic_account_balance_white_24dp);
                    mComingSoonText.setVisibility(View.VISIBLE);
                    mDashboardContainer.setVisibility(View.GONE);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorStockBar));
                    mSearchEditText.setHint("Search for stocks...");
                    mSearchEditText.setEnabled(false);

                    mState = false;


                } else if (id == R.id.toolbar_gold){
                    fab.setImageResource(R.drawable.ic_redeem_white_24dp);
                    mComingSoonText.setVisibility(View.VISIBLE);
                    mDashboardContainer.setVisibility(View.GONE);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorGoldBar));
                    mSearchEditText.setHint("Search for metal prices...");
                    mSearchEditText.setEnabled(false);
                    mState = false;


                } else if (id == R.id.toolbar_currency){
                    fab.setImageResource(R.drawable.ic_attach_money_white_24dp);
                    mComingSoonText.setVisibility(View.GONE);
                    mDashboardContainer.setVisibility(View.VISIBLE);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorCurrencyBar));
                    mSearchEditText.setHint("Search for currencies...");
                    mSearchEditText.setEnabled(true);

                    mState = true;


                }
            }

            @Override
            public void onItemLongClick(MenuItem item) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabToolbar.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(1,2));
        entries.add(new Entry(2,4));
        entries.add(new Entry(3,5));
        entries.add(new Entry(4,6));
        entries.add(new Entry(5,8));

        List<Entry> entriesB = new ArrayList<Entry>();
        entriesB.add(new Entry(1,3));
        entriesB.add(new Entry(2,3));
        entriesB.add(new Entry(3,5));
        entriesB.add(new Entry(4,7));
        entriesB.add(new Entry(5,9));


        LineDataSet dataSet = new LineDataSet(entries, "Base Currency"); // add entries to dataset
        dataSet.setColor(R.color.colorPrimary);

        LineDataSet dataSetB = new LineDataSet(entriesB, "Counter Currency");
        //dataSetB.setColor(R.color.colorAccent);

        //dataSet.setColors(new int[]{R.color.colorPrimary,R.color.colorAccent});

        LineData lineData = new LineData(dataSet);

        //dataSetB.setColor(R.color.colorAccent);
        lineData.addDataSet(dataSetB);

        chart.setData(lineData);
        chart.invalidate(); // refresh


        mFavouriteList = new ArrayList<>();
        mFavouriteList.add("CAD,$1.00");
        mFavouriteList.add("USD,$1.00");
        mFavouriteList.add("INR,$1.00");
        mFavouriteList.add("BRL,$1.00");
        mFavouriteList.add("EUR,$1.00");

        mFavouritesRecyclerAdapter = new FavouritesRecyclerAdapter(mFavouriteList);

        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mFavourites.setLayoutManager(horizontalLayoutManager);
        mFavourites.setAdapter(mFavouritesRecyclerAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            if (mState) {
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //Intent intent = new Intent(DashboardActivity.this, HelpActivity.class);
            //startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(DashboardActivity.this, HelpActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
