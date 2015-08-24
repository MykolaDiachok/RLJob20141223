package com.radioline.master.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.KeyboardUtil;
import com.radioline.master.fragments.GroupsFragment;
import com.radioline.master.myapplication.R;

public class DefaultActivity extends AppCompatActivity {

    private Drawer result = null;

    public static enum MenuDrawer {
        NEWS,
        EXCHANGE,
        GROUPS,
        ITEMS,
        BASKET,
        SEARCH,
        SCAN
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("RadioLine");

        //Create the drawer
        DrawerBuilder drawerBuilder = new DrawerBuilder(this);
        drawerBuilder.withActivity(this);
        drawerBuilder.withRootView(R.id.drawer_container);
        drawerBuilder.withToolbar(toolbar);
        drawerBuilder.withActionBarDrawerToggleAnimated(true);
        drawerBuilder.addDrawerItems(
                new PrimaryDrawerItem().withName("News").withIdentifier(MenuDrawer.NEWS.ordinal()),
                new PrimaryDrawerItem().withName("Exchange").withIdentifier(MenuDrawer.EXCHANGE.ordinal()),
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withName("Groups").withIdentifier(MenuDrawer.GROUPS.ordinal()),
                new PrimaryDrawerItem().withName("Items").withIdentifier(MenuDrawer.ITEMS.ordinal()),
                new PrimaryDrawerItem().withName("Basket").withIdentifier(MenuDrawer.BASKET.ordinal()),
                new DividerDrawerItem(),

                new PrimaryDrawerItem().withName("Search").withIdentifier(MenuDrawer.SEARCH.ordinal()),
                new PrimaryDrawerItem().withName("Scan").withIdentifier(MenuDrawer.SCAN.ordinal())

        ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                if (drawerItem != null) {
                    if (drawerItem instanceof Nameable) {
                        String name = ((Nameable) drawerItem).getName().getText(DefaultActivity.this);
                        getSupportActionBar().setTitle(name);
                    }
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = new Fragment();
                MenuDrawer curIndef = MenuDrawer.values()[drawerItem.getIdentifier()];
                switch (curIndef)
                {
                    case NEWS:
                        //fragment = new LibraryFragment();
                        break;
                    case EXCHANGE:
                        //fragment = new AddBookFragment();
                        break;
                    case GROUPS:
                        fragment = new GroupsFragment();
                        break;
                }

                transaction.replace(R.id.fragment_container, fragment,"groups");
                transaction.commit();

                return false;

            }
        })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        KeyboardUtil.hideKeyboard(DefaultActivity.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                });

        drawerBuilder.withSavedInstance(savedInstanceState);
        drawerBuilder.build();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
