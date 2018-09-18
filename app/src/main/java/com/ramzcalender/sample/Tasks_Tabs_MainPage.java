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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.ramzcalender.sample.Activity_Fragment.class_name;
import static com.ramzcalender.sample.MainActivity.flag;
import static com.ramzcalender.sample.SignIn_page.contacts_str;

public class Tasks_Tabs_MainPage extends AppCompatActivity {
    FloatingActionButton fb;
    LinearLayout ll;
    TabLayout tabLayout;
    Button back_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_tabs_main_page);
        back_task= (Button) findViewById(R.id.back_task);
        fb= (FloatingActionButton) findViewById(R.id.floating_deal);
        ll= (LinearLayout) findViewById(R.id.linearlyout);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag=0;
                class_name="Task_class";
                Intent i11 = new Intent(Tasks_Tabs_MainPage.this, Task_class.class);
                startActivity(i11);
            }
        });

        back_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tasks_Tabs_MainPage.this, MainActivity.class);
                startActivity(i);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("My Task"));
        tabLayout.addTab(tabLayout.newTab().setText("Created Tasks"));


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

                    Fragment_My_Tasks mytasks = new Fragment_My_Tasks();
                    return mytasks;
                case 1:

                    Fragment_My_Created_Tasks my_created_tasks = new Fragment_My_Created_Tasks();
                    return my_created_tasks;
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
