package com.veriqus.savoirvivre;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListArticlesFragment extends Fragment {


    private ListView listView;
    OnArticleSelectedListener mCallback;
    View rootView;
    static String PASSED_VALUE;

    public ListArticlesFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface OnArticleSelectedListener {
        public void onArticleSelected(String name);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_list_articles, container, false);


        Bundle bundle = getArguments();

        String categoryName = bundle.getString(PASSED_VALUE);
        String categoryID = ((MainActivity)getActivity()).getCategoryIDByName(categoryName);


        listView = (ListView) rootView.findViewById(R.id.listVi);
        final List<String> quotesTitles = ((MainActivity)getActivity()).getArticleList(categoryID, "title");
        Log.i("CatID", categoryID);
        final List<String> quotesContents = ((MainActivity)getActivity()).getArticleList(categoryID, "content");



        final ArrayList<ItemList> arrayOfArticles = new ArrayList<>();
        TextView articleContentText = (TextView) rootView.findViewById(R.id.content_text_article_list);


        if (!quotesTitles.isEmpty()) {

            ArticleListAdapter articleAdapter = new ArticleListAdapter(getContext(), arrayOfArticles);
            for(int i = 0; i < quotesTitles.size(); i++){
                String plainText = Html.fromHtml(quotesContents.get(i)).toString();
                quotesContents.set(i, plainText);
                articleAdapter.add(new ItemList(quotesTitles.get(i), quotesContents.get(i)));
            }
            listView = (ListView) rootView.findViewById(R.id.listVi);
            listView.setAdapter(articleAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mCallback.onArticleSelected(quotesTitles.get(position));
                }
            });

        }
        else {
            Snackbar noArticleSnackbar = Snackbar.make(rootView.findViewById(R.id.listVi), R.string.no_article, Snackbar.LENGTH_LONG);
            noArticleSnackbar.show();
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

//    public String getCategoryIDByName(String categoryName){
//        String categoryID = "";
//
//        if (categoryName.equals(getContext().getString(R.string.subCat1_1_phone))){
//            categoryID = "subCat1_1_phone";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat1_2_mail))){
//            categoryID = "subCat1_2_mail";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat1_3_socialmedia))){
//            categoryID = "subCat1_3_socialmedia";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat2_1_restaurant))){
//            categoryID = "subCat2_1_restaurant";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat2_2_home))){
//            categoryID = "subCat2_2_home";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat2_3_servingalcohol))){
//            categoryID = "subCat2_3_servingalcohol";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_1_appearance))){
//            categoryID = "subCat3_1_appearance";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_2_gifts))){
//            categoryID = "subCat3_2_gifts";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_3_greetings))){
//            categoryID = "subCat3_3_greetings";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_4_guests))){
//            categoryID = "subCat3_4_guests";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_5_dance))){
//            categoryID = "subCat3_5_dance";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_6_date))){
//            categoryID = "subCat3_6_date";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_7_job))){
//            categoryID = "subCat3_7_job";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_8_disabled))){
//            categoryID = "subCat3_8_disabled";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_9_cigarettes))){
//            categoryID = "subCat3_9_cigarettes";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat3_10_conversation))){
//            categoryID = "subCat3_10_conversation";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat4_1_birthday))){
//            categoryID = "subCat4_1_birthday";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat4_2_wedding))){
//            categoryID = "subCat4_2_wedding";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat4_3_funeral))){
//            categoryID = "subCat4_3_funeral";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat5_1_religion))){
//            categoryID = "subCat5_1_religion";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat5_2_shop))){
//            categoryID = "subCat5_2_shop";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat5_3_gym))){
//            categoryID = "subCat5_3_gym";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat5_5_events))){
//            categoryID = "subCat5_5_events";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat5_6_publictransport))){
//            categoryID = "subCat5_6_publictransport";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat5_7_journey))){
//            categoryID = "subCat5_7_journey";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat5_8_hospital))){
//            categoryID = "subCat5_8_hospital";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat6_1_dictionary))){
//            categoryID = "subCat6_1_dictionary";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat6_2_animals))){
//            categoryID = "subCat6_2_animals";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat6_3_children))){
//            categoryID = "subCat6_3_children";
//        }
//        else if (categoryName.equals(getContext().getString(R.string.subCat6_4_others))){
//            categoryID = "subCat6_4_others";
//        }
//
//        return categoryID;
//    }

}