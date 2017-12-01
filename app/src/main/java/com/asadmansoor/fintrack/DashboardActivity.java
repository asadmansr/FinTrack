package com.asadmansoor.fintrack;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
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
    Button mBaseBtn, mCounterBtn;
    EditText mBaseET, mCounterET;
    LinearLayout mDashboardContainer;
    RecyclerView mFavourites;
    ArrayList<String> mFavouriteList;
    ImageView mStar, mNotif;
    FavouritesRecyclerAdapter mFavouritesRecyclerAdapter;
    FloatingActionButton fab;
    String buttonValue;
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

        mBaseBtn = (Button) findViewById(R.id.base_btn);
        mCounterBtn = (Button) findViewById(R.id.counter_btn);

        mComingSoonText = (TextView) findViewById(R.id.coming_soon_tv);
        mDashboardContainer = (LinearLayout) findViewById(R.id.dashboard_container_ll);

        mBaseET = (EditText) findViewById(R.id.base_et);
        mCounterET = (EditText) findViewById(R.id.counter_et);

        mStar = (ImageView) findViewById(R.id.star_iv);
        mNotif = (ImageView) findViewById(R.id.notif_iv);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                boolean validChange = extras.getBoolean("result");
                if (validChange) {
                    int positionBtn = extras.getInt("position");
                    if (positionBtn == 0) {
                        String temp = extras.getString("result_text");
                        String ref = extras.getString("reference");
                        temp = temp.split("-")[1];
                        temp = temp.split(" ")[1];
                        Log.d("data",temp);
                        mBaseBtn.setText(temp.toUpperCase());
                        mCounterBtn.setText(ref.toUpperCase());
                    } else if (positionBtn == 1) {
                        String temp = extras.getString("result_text");
                        String ref = extras.getString("reference");
                        temp = temp.split("-")[1];
                        temp = temp.split(" ")[1];
                        Log.d("data",temp);
                        mCounterBtn.setText(temp.toUpperCase());
                        mBaseBtn.setText(ref.toUpperCase());
                    }
                }
            }
        }

        mBaseET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((charSequence.toString()).length() > 0) {
                    convertCurrency(charSequence.toString(),mBaseBtn.getText().toString(),mCounterBtn.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mState) {
                    Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                    intent.putExtra("reference",mCounterBtn.getText());
                    startActivity(intent);
                    finish();
                }
            }
        });

        mBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                intent.putExtra("position",0);
                intent.putExtra("reference",mCounterBtn.getText());
                startActivity(intent);
                finish();
            }
        });

        mCounterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                intent.putExtra("position",1);
                intent.putExtra("reference",mBaseBtn.getText());
                startActivity(intent);
                finish();
            }
        });

        mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = mStar.getContentDescription().toString();
                if (state.equals("unselected")){
                    mStar.setBackgroundResource(R.drawable.ic_star_selected);
                    mStar.setContentDescription("selected");

                } else if (state.equals("selected")){
                    mStar.setBackgroundResource(R.drawable.ic_star_unselected);
                    mStar.setContentDescription("unselected");

                }
            }
        });

        mNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = mNotif.getContentDescription().toString();
                if (state.equals("unselected")){
                    mNotif.setBackgroundResource(R.drawable.ic_bell_selected);
                    mNotif.setContentDescription("selected");
                    triggerNotification();

                } else if (state.equals("selected")){
                    mNotif.setBackgroundResource(R.drawable.ic_bell_unselected);
                    mNotif.setContentDescription("unselected");

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
        Description description = new Description();
        description.setText("Currency Value");
        chart.setDescription(description);

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(1, (float) 0.78668));
        entries.add(new Entry(2, (float) 0.78521));
        entries.add(new Entry(3, (float) 0.78691));
        entries.add(new Entry(4, (float) 0.78710));
        entries.add(new Entry(5, (float) 0.78828));
        entries.add(new Entry(6, (float) 0.78161));
        entries.add(new Entry(7, (float) 0.77873));

        List<Entry> entriesB = new ArrayList<Entry>();
        entriesB.add(new Entry(1, (float) 1.27137));
        entriesB.add(new Entry(2, (float) 1.27084));
        entriesB.add(new Entry(3, (float) 1.27068));
        entriesB.add(new Entry(4, (float) 1.27128));
        entriesB.add(new Entry(5, (float) 1.26836));
        entriesB.add(new Entry(6, (float) 1.27866));
        entriesB.add(new Entry(7, (float) 1.28108));


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
        mFavouriteList.add("CAD,1.00");
        mFavouriteList.add("USD,0.77");
        mFavouriteList.add("INR,50.05");
        mFavouriteList.add("BRL,2.54");
        mFavouriteList.add("EUR,0.65");

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
                intent.putExtra("reference",mCounterBtn.getText());
                startActivity(intent);
                finish();
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

    private void convertCurrency(String baseCurrency, String baseStr, String counterStr){
        double[] baseARS = new double[]{1.0,0.189139,0.0745341,1.53141,0.0485375,3.73225,0.216717,5.78961,0.483731,0.0577858};
        double[] baseBRL = new double[]{5.28755,1.0,0.394054,8.09815,0.256601,19.7406,1.14608,30.6015,2.55757,0.305624};
        double[] baseCAD = new double[]{13.4205,2.53753,1.0,20.5515,0.651054,50.0925,2.90833,77.6230,6.49098,0.775590};
        double[] baseCUP = new double[]{0.652982,0.123503,0.0486527,1.0,0.0316779,2.43759,0.141525,3.77514,0.315825,0.0377358};
        double[] baseEUR = new double[]{20.6166,3.89786,1.53567,31.5698,1.0,76.9368,4.46781,119.257,9.97107,1.19139};
        double[] baseINR = new double[]{0.267941,0.0506595,0.0199591,0.410279,0.0129957,1.0,0.0580615,1.54913,0.129564,0.0154816};
        double[] baseSAR = new double[]{4.61416,0.872661,0.343728,7.06613,0.223820,17.2235,1.0,26.6914,2.23147,0.266645};
        double[] baseRSD = new double[]{0.173031,0.0327236,0.0128884,0.264988,0.00839339,0.645849,0.0374984,1.0,0.0836841,0.00999937};
        double[] baseSEK = new double[]{2.06770,0.391054,0.154020,3.16661,0.100303,7.71659,0.448138,11.9646,1.0,0.119495};
        double[] baseUSD = new double[]{17.3036,3.27273,1.28900,26.5000,0.839415,64.5760,3.74966,100.074,8.36939,1.0};

        int baseValue = Integer.valueOf(baseCurrency);

        double convertedValue = 0;
        int base = 0;
        int counter = 0;
        String[] listCurr = new String[]{"ARS","BRL","CAD","CUP","EUR","INR","SAR","RSD","SEK","USD"};

        for (int i=0;i<listCurr.length;i++){
            if (baseStr.equals(listCurr[i])){
                base = i;
            }

            if (counterStr.equals(listCurr[i])){
                counter = i;
            }
        }



        if (base == 0){
            convertedValue = baseValue * baseARS[counter];
        } else if (base == 1){
            convertedValue = baseValue * baseBRL[counter];
        } else if (base == 2){
            convertedValue = baseValue * baseCAD[counter];
        } else if (base == 3){
            convertedValue = baseValue * baseCUP[counter];
        } else if (base == 4){
            convertedValue = baseValue * baseEUR[counter];
        } else if (base == 5){
            convertedValue = baseValue * baseINR[counter];
        } else if (base == 6){
            convertedValue = baseValue * baseSAR[counter];
        } else if (base == 7){
            convertedValue = baseValue * baseRSD[counter];
        } else if (base == 8){
            convertedValue = baseValue * baseSEK[counter];
        } else if (base == 9){
            convertedValue = baseValue * baseUSD[counter];
        }

        mCounterET.setText(String.valueOf(convertedValue));

    }


    private void triggerNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("CAD");
        mBuilder.setContentText("0.82587");

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //mNotificationManager.notify();
        mNotificationManager.notify(001, mBuilder.build());

    }
}
