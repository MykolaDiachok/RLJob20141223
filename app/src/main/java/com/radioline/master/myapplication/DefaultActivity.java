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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.fragments.ExchangeFragment;
import com.radioline.master.fragments.GroupsFragment;
import com.radioline.master.fragments.NewsFragment;
import com.radioline.master.myapplication.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import hugo.weaving.DebugLog;

public class DefaultActivity extends AppCompatActivity {

    //save our header or result
    private Drawer result = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private GroupsFragment groupsFragment;
    private ExchangeFragment exchangeFragment;
    private NewsFragment newsFragment;

    public static final int
            ID_NEWS = 0,
            ID_Exchange = 1,
            ID_Order = 2,
            ID_Basket = 3,
            ID_Search = 4,
            ID_BarcodeSearch = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("RadioLine");

        fragmentManager = getSupportFragmentManager();

        groupsFragment = new GroupsFragment();
        exchangeFragment = new ExchangeFragment();
        newsFragment = new NewsFragment();


        //Create the drawer
        result = new DrawerBuilder(this)
                //this layout have to contain child layouts
                .withRootView(R.id.drawer_container)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Exchange $").withIdentifier(ID_Exchange).withTag(ExchangeFragment.class),
                        new PrimaryDrawerItem().withName("News incoming").withIdentifier(ID_NEWS).withTag(NewsFragment.class),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Order").withIdentifier(ID_Order).withTag(GroupsFragment.class)

//                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye),

//                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(false),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_bullhorn)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                   @DebugLog
                                                   @Override
                                                   public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                       if (drawerItem != null && drawerItem instanceof Nameable) {
                                                           fragmentTransaction = fragmentManager.beginTransaction();
                                                           switch (drawerItem.getIdentifier()) {
                                                               case ID_Order:
                                                                   fragmentTransaction.replace(R.id.fragment_container, groupsFragment, "order");
                                                                   break;
                                                               case ID_NEWS:
                                                                   fragmentTransaction.replace(R.id.fragment_container, newsFragment, "news");
                                                                   break;
                                                               case ID_Exchange:
                                                                   fragmentTransaction.replace(R.id.fragment_container, exchangeFragment, "exchange");
                                                                   break;
                                                           }
                                                           fragmentTransaction.commit();
                                                       }

                                                       return false;
                                                   }
                                               }

                )
                            .

                    withOnDrawerListener(new Drawer.OnDrawerListener() {
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
                                         }

                    )
                            .

                    withFireOnInitialOnClick(true)

                    .

                    withSavedInstance(savedInstanceState)

                    .

                    build();

                    ParseQuery<ParseObject> queryExchange = new ParseQuery<ParseObject>("ExchangeRates");
                    queryExchange.orderByDescending("dateSet");
                    queryExchange.getFirstInBackground(new GetCallback<ParseObject>()

                    {
                        @Override
                        public void done (ParseObject parseObject, ParseException e){
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        result.updateItem(
                                new PrimaryDrawerItem().withName("Exchange $").withIdentifier(ID_Exchange).withBadge(formatter.format(parseObject.getDouble("rate")))
                        );


                    }
                    }

                    );


                    //react on the keyboard
                    result.keyboardSupportEnabled(this,true);
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
