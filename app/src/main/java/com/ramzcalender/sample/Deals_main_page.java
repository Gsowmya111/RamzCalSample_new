package com.ramzcalender.sample;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.ramzcalender.sample.SignIn_page.contacts_str;


public class Deals_main_page extends AppCompatActivity {
        FloatingActionButton fb;
    LinearLayout ll;
    TabLayout tabLayout;
    public static String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_main_page);


        fb= (FloatingActionButton) findViewById(R.id.floating_deal);
        ll= (LinearLayout) findViewById(R.id.linearlyout);
        str="dmp";

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contacts_str = "Deal_main_page";
                Intent inte= new Intent(Deals_main_page.this,Deals_page.class);
                startActivity(inte);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("PROSPECTING"));
        tabLayout.addTab(tabLayout.newTab().setText("QUALIFIED"));
        tabLayout.addTab(tabLayout.newTab().setText("QUOTE"));
        tabLayout.addTab(tabLayout.newTab().setText("CLOSURE"));
        tabLayout.addTab(tabLayout.newTab().setText("WON"));
        tabLayout.addTab(tabLayout.newTab().setText("UNQUALIFIED"));
        tabLayout.addTab(tabLayout.newTab().setText("LOST"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0000FF"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter1 adapter = new PagerAdapter1
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                Toast.makeText(Deals_main_page.this, "you clicked" + tab.getPosition(), Toast.LENGTH_LONG).show();

                if(tab.getPosition()==0){
                    ll.setBackgroundColor(Color.BLACK);
                    tabLayout.setBackgroundColor(Color.BLACK);
                }else  if(tab.getPosition()==1){
                    ll.setBackgroundColor(Color.BLACK);
                    tabLayout.setBackgroundColor(Color.BLACK);
                }else  if(tab.getPosition()==2){
                    ll.setBackgroundColor(Color.BLACK);
                    tabLayout.setBackgroundColor(Color.BLACK);
                }else  if(tab.getPosition()==3){
                    ll.setBackgroundColor(Color.BLACK);
                    tabLayout.setBackgroundColor(Color.BLACK);
                }else  if(tab.getPosition()==4){
                    ll.setBackgroundColor(Color.parseColor("#66ff66"));
                    tabLayout.setBackgroundColor(Color.parseColor("#66ff66"));
                }else  if(tab.getPosition()==5){
                    ll.setBackgroundColor(Color.GRAY);
                    tabLayout.setBackgroundColor(Color.GRAY);
                }else  if(tab.getPosition()==6){
                    ll.setBackgroundColor(Color.parseColor("#FF6666"));
                    tabLayout.setBackgroundColor(Color.parseColor("#FF6666"));
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }
    class PagerAdapter1 extends FragmentPagerAdapter {
        int numberOfTabs;
        public PagerAdapter1(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.numberOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    Fragment_Prospecting prospecting = new Fragment_Prospecting();
                      return prospecting;
                case 1:

                    Fragment_Qualified qualified = new Fragment_Qualified();
                    return qualified;
                case 2:

                    Fragment_Quote quote = new Fragment_Quote();
                    return quote;
                case 3:


                    Fragment_Closure closure = new Fragment_Closure();
                    return closure;
                case 4:

                    Fragment_Won won = new Fragment_Won();
                    return won;
                case 5:

                    Fragment_Unqualified unqualified = new Fragment_Unqualified();
                    return unqualified;
                case 6:

                    Fragment_Lost lost = new Fragment_Lost();
                    return lost;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numberOfTabs;
        }
    }
}
