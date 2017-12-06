package com.veriqus.savoirvivre;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity
        extends AppCompatActivity
        implements CategoryFragment.OnHeadlineSelectedListener,
                    CategoryFragment.OnCategorySelectedListern,
                    ListArticlesFragment.OnArticleSelectedListener,
                    ModeFragment.onModeSelectedListener,
                    SubCatListAdapter.OnLearningSubSelected,
                    ArticleSubcategoryFragment.OnQuizSelectedListener,
                    LearningFragment.OnHeadlineSelectedListener{

    DatabaseAccess databaseAccess;
    CategoryFragment firstFragment = new CategoryFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    SavedArticlesFragment savedArticlesFragment = new SavedArticlesFragment();
    TipFragment tipFragment = new TipFragment();
    QuizFragment quizFragment = new QuizFragment();
    ListArticlesFragment listArticlesFragment = new ListArticlesFragment();
    FragmentManager fm = getSupportFragmentManager();

    boolean noMoreIntro = true;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    fm.popBackStackImmediate(null, POP_BACK_STACK_INCLUSIVE);
                    getSupportActionBar().hide();
                    replaceFragment(firstFragment,getResources().getString(R.string.app_name));
                    return true;
                case R.id.action_tip:
                    //this if avoid error during selecting visible fragment another time
                    if(!tipFragment.isVisible()) {
                        fm.popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
                        replaceFragment(tipFragment, getResources().getString(R.string.random));
                        return true;
                    }
                    return true;
                case R.id.action_saved_articles:
                    fm.popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
                    replaceFragment(savedArticlesFragment,getResources().getString(R.string.saved_articles));
                    return true;
                case R.id.action_settings:
                    fm.popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
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

        getSupportActionBar().hide();


        //Checking if it is first run of program or not. If yes, it show Welcome Activity
        SharedPreferences settingsIntro = getSharedPreferences(WelcomeActivity.NO_MORE_INTRO, 0);
        noMoreIntro = settingsIntro.getBoolean("noMoreIntro", true);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.setTextVisibility(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // OPTIONAL:
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            fm.beginTransaction().add(R.id.fragment_container, firstFragment).commit();

        }

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
    }


    @Override
    public void onLearningSubSelected(String subCatName) {
        ArticleSubcategoryFragment newFragment = new ArticleSubcategoryFragment();
        Bundle args = new Bundle();
        args.putString("CATEGORY_ID", subCatName);
        args.putInt("ARTICLE_POSITION", 0);
        newFragment.setArguments(args);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack("article");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onQuizSelected(String quizName) {
//        QuizFragment newFragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString("CATEGORY_ID", quizName);
        quizFragment.setArguments(args);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, quizFragment);
        transaction.addToBackStack("quiz");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCategorySelected(int name) {
        LearningPathFragment newLearning = new LearningPathFragment();
        Bundle args = new Bundle();
        args.putInt("CATEGORY", name);
        newLearning.setArguments(args);
        fm.popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, newLearning);
        transaction.addToBackStack("learningPath");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        setAppBarName(getString(name));
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSubCategorySelected(String name, String mode) {
        Bundle args = new Bundle();
        args.putString(ListArticlesFragment.CATEGORYNAME_VALUE, name);
        args.putString("TYPE_VALUE", mode);
        listArticlesFragment.setArguments(args);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, listArticlesFragment);
        transaction.addToBackStack("listArticles");
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
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, listArticlesFragment);
        transaction.addToBackStack("subCategory");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        setAppBarName(name);
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//
//    @Override
//    public void onModeSelected(String name) {
//        Bundle args = new Bundle();
//        args.putString(ModeFragment.PASSED_VALUE, name);
//        listArticlesFragment.setArguments(args);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, listArticlesFragment);
//        transaction.addToBackStack("subCategory");
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        setAppBarName(name);
//        transaction.commit();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }

    // if article cardview selected in mode
    @Override
    public void onModeSelected(String name, boolean bool) {
        //new fragment stop error: fragment already active
        ArticleFromModeFragment newFragment = new ArticleFromModeFragment();
        Bundle args = new Bundle();
        args.putString(ModeFragment.PASSED_VALUE, name);
        newFragment.setArguments(args);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack("article");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onArticleSelected(String categoryName, int position) {
        //new fragment stop error: fragment already active
        ArticleFragment newFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString("CATEGORY_ID", categoryName);
        args.putInt("ARTICLE_POSITION", position);
        newFragment.setArguments(args);
        FragmentTransaction transaction = fm.beginTransaction();
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

        int count = fm.getBackStackEntryCount();
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
                getSupportActionBar().hide();
            }
        } else {
            fm.popBackStack();
        }

    }

    public void setAppBarName(String name) {
        getSupportActionBar().setTitle(name);
    }

    public List<String> getArticleList(String row, String title_or_content) {
        List<String> list = databaseAccess.getQuotes(row, title_or_content );
        return list;
    }

    public List<String> getArticleList2(String row, String title_or_content, String entry_or_quests) {
        List<String> list = databaseAccess.getQuotes2(row, title_or_content, entry_or_quests);
        return list;
    }

    public List<String> getArticleList(String row, String title_or_content, String type) {
        List<String> list = databaseAccess.getQuotes(row, title_or_content, type);
        return list;
    }

    public List<String> getQuizQuotes(String subCategory, String question_or_answer) {
        List<String> list = databaseAccess.getQuizQuotes(subCategory, question_or_answer);
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

    public String getCategory(String id) {
        String text = databaseAccess.getCategory(id);
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

    public byte[] getQuizImageByte(String name){
        byte[] image = databaseAccess.getQuizImage(name);
        return image;
    }

    public String getRandromTitle(){
        return databaseAccess.getRandomArticleName();
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = fm.beginTransaction();

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
