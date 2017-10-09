package com.veriqus.savoirvivre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity
        extends AppCompatActivity
        implements CategoryFragment.OnHeadlineSelectedListener,
                    ListArticlesFragment.OnArticleSelectedListener,
                    ModeFragment.onModeSelectedListener{

    DatabaseAccess databaseAccess;
    CategoryFragment firstFragment = new CategoryFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    SavedArticlesFragment savedArticlesFragment = new SavedArticlesFragment();
    TipFragment tipFragment = new TipFragment();
    ArticleFragment articleFragment = new ArticleFragment();
    ListArticlesFragment listArticlesFragment = new ListArticlesFragment();
    ModeFragment modeFragment = new ModeFragment();

    boolean noMoreIntro = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    getSupportFragmentManager().popBackStackImmediate("subCategory", POP_BACK_STACK_INCLUSIVE);
                    replaceFragment(firstFragment,getResources().getString(R.string.app_name));
                    return true;
                case R.id.action_tip:
                    //this if avoid error during selecting visible fragment another time
                    if(!tipFragment.isVisible()) {
                        getSupportFragmentManager().popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
                        replaceFragment(tipFragment, getResources().getString(R.string.random));
                        return true;
                    }
                    return true;
                case R.id.action_saved_articles:
                    getSupportFragmentManager().popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
                    replaceFragment(savedArticlesFragment,getResources().getString(R.string.saved_articles));
                    return true;
                case R.id.action_settings:
                    getSupportFragmentManager().popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
                    replaceFragment(settingsFragment, getResources().getString(R.string.settings));
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking if it is first run of program or not. If yes, it show Welcome Activity
        SharedPreferences settingsIntro = getSharedPreferences(WelcomeActivity.NO_MORE_INTRO, 0);
        noMoreIntro = settingsIntro.getBoolean("noMoreIntro", false);

        if(!noMoreIntro)
        {
            Intent toTheWelcome = new Intent(this, WelcomeActivity.class);
            startActivity(toTheWelcome);
            finish();
        }

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        TabAdapter adapter = new TabAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        //tabLayout.setupWithViewPager(viewPager);


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // OPTIONAL:
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

        }

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
    }

    @Override
    public void onSubCategorySelected(String name) {
        Bundle args = new Bundle();
        args.putString(ListArticlesFragment.CATEGORYNAME_VALUE, name);
        modeFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, modeFragment);
        transaction.addToBackStack("subCategory");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        setAppBarName(name);
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onModeSelected(String name, String mode) {
        Bundle args = new Bundle();
        args.putString(ModeFragment.PASSED_VALUE, name);
        args.putString("TYPE_VALUE", mode);
        listArticlesFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, listArticlesFragment);
        transaction.addToBackStack("subCategory");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        setAppBarName(name);
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onModeSelected(String name) {
        Bundle args = new Bundle();
        args.putString(ModeFragment.PASSED_VALUE, name);
        listArticlesFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, listArticlesFragment);
        transaction.addToBackStack("subCategory");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        setAppBarName(name);
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onArticleSelected(String name) {
        //new fragment stop error: fragment already active
        ArticleFragment newFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(newFragment.ARTICLE_NAME, name);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack("article");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true; //Notice you must returning true here
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseAccess.close();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //databaseAccess.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        //Log.i("count:", count+"");

        if (count == 0 ) {
            super.onBackPressed();
        }

        if (count == 1) {
            super.onBackPressed();
            //additional code
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            if(firstFragment.isVisible()) {
                setAppBarName(getResources().getString(R.string.app_name));
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    public void setAppBarName(String name) {
        getSupportActionBar().setTitle(name);
    }

    public List<String> getArticleList(String row, String title_or_content) {
        List<String> list = databaseAccess.getQuotes(row, title_or_content );
        return list;
    }

    public List<String> getArticleList(String row, String title_or_content, String type) {
        List<String> list = databaseAccess.getQuotes(row, title_or_content, type);
        return list;
    }


    public List<String> getSaved(String title_or_content) {
        List<String> list = databaseAccess.getSavedArticles(title_or_content);
        return list;
    }

    public String getArticleContent(String name) {
        String text = databaseAccess.getArticleContent(name);
        return text;
    }

    public String getArticleType(String name) {
        String text = databaseAccess.getArticleType(name);
        return text;
    }

    public String getArticleContentbyID(String id) {
        return databaseAccess.getArticleContentbyID(id);
    }

    public String getArticleTitlebyID(String id) {
        return databaseAccess.getArticleTitlebyID(id);
    }


    public void save(String name, int save) {
        databaseAccess.save(name, save);
    }

    public byte[] getImageByte(String name){
        byte[] image = databaseAccess.getImage(name);
        return image;
    }

    public String getRandromTitle(){
        return databaseAccess.getRandomArticleName();
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //ft.addToBackStack(tag);
        setAppBarName(tag);
//        if(tag=="Category"){
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
//        else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fragment_container, newFragment, tag)
                .commit();

    }

    public boolean isSaved(String title){
        boolean isSaved = databaseAccess.isSaved(title);
        return isSaved;
    }

    private String randomArticle(){
        String articleName = "";
        return articleName;
    }

    public String getArticleID(String name) {
        return databaseAccess.getArticleID(name);
    }

    public void saveMap(HashMap<String,String> inputMap){
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").commit();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
    }

    public HashMap<String,String> loadMap(){
        HashMap<String,String> outputMap = new HashMap<String,String>();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString("My_map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }

    public String getCategoryIDByName(String categoryName){
        String categoryID = "";

        if (categoryName.equals(getString(R.string.subCat1_1_phone))){
            categoryID = "subCat1_1_phone";
        }
        else if (categoryName.equals(getString(R.string.subCat1_2_mail))){
            categoryID = "subCat1_2_mail";
        }
        else if (categoryName.equals(getString(R.string.subCat1_3_socialmedia))){
            categoryID = "subCat1_3_socialmedia";
        }
        else if (categoryName.equals(getString(R.string.subCat2_1_restaurant))){
            categoryID = "subCat2_1_restaurant";
        }
        else if (categoryName.equals(getString(R.string.subCat2_2_home))){
            categoryID = "subCat2_2_home";
        }
        else if (categoryName.equals(getString(R.string.subCat2_3_servingalcohol))){
            categoryID = "subCat2_3_servingalcohol";
        }
        else if (categoryName.equals(getString(R.string.subCat3_1_appearance))){
            categoryID = "subCat3_1_appearance";
        }
        else if (categoryName.equals(getString(R.string.subCat3_2_gifts))){
            categoryID = "subCat3_2_gifts";
        }
        else if (categoryName.equals(getString(R.string.subCat3_3_greetings))){
            categoryID = "subCat3_3_greetings";
        }
        else if (categoryName.equals(getString(R.string.subCat3_4_guests))){
            categoryID = "subCat3_4_guests";
        }
        else if (categoryName.equals(getString(R.string.subCat3_5_dance))){
            categoryID = "subCat3_5_dance";
        }
        else if (categoryName.equals(getString(R.string.subCat3_6_date))){
            categoryID = "subCat3_6_date";
        }
        else if (categoryName.equals(getString(R.string.subCat3_7_job))){
            categoryID = "subCat3_7_job";
        }
        else if (categoryName.equals(getString(R.string.subCat3_8_disabled))){
            categoryID = "subCat3_8_disabled";
        }
        else if (categoryName.equals(getString(R.string.subCat3_9_cigarettes))){
            categoryID = "subCat3_9_cigarettes";
        }
        else if (categoryName.equals(getString(R.string.subCat3_10_conversation))){
            categoryID = "subCat3_10_conversation";
        }
        else if (categoryName.equals(getString(R.string.subCat4_1_birthday))){
            categoryID = "subCat4_1_birthday";
        }
        else if (categoryName.equals(getString(R.string.subCat4_2_wedding))){
            categoryID = "subCat4_2_wedding";
        }
        else if (categoryName.equals(getString(R.string.subCat4_3_funeral))){
            categoryID = "subCat4_3_funeral";
        }
        else if (categoryName.equals(getString(R.string.subCat5_1_religion))){
            categoryID = "subCat5_1_religion";
        }
        else if (categoryName.equals(getString(R.string.subCat5_2_shop))){
            categoryID = "subCat5_2_shop";
        }
        else if (categoryName.equals(getString(R.string.subCat5_3_gym))){
            categoryID = "subCat5_3_gym";
        }
        else if (categoryName.equals(getString(R.string.subCat5_5_events))){
            categoryID = "subCat5_5_events";
        }
        else if (categoryName.equals(getString(R.string.subCat5_6_publictransport))){
            categoryID = "subCat5_6_publictransport";
        }
        else if (categoryName.equals(getString(R.string.subCat5_7_journey))){
            categoryID = "subCat5_7_journey";
        }
        else if (categoryName.equals(getString(R.string.subCat5_8_hospital))){
            categoryID = "subCat5_8_hospital";
        }
        else if (categoryName.equals(getString(R.string.subCat6_1_dictionary))){
            categoryID = "subCat6_1_dictionary";
        }
        else if (categoryName.equals(getString(R.string.subCat6_2_animals))){
            categoryID = "subCat6_2_animals";
        }
        else if (categoryName.equals(getString(R.string.subCat6_3_children))){
            categoryID = "subCat6_3_children";
        }
        else if (categoryName.equals(getString(R.string.subCat6_4_others))){
            categoryID = "subCat6_4_others";
        }

        return categoryID;
    }


}
