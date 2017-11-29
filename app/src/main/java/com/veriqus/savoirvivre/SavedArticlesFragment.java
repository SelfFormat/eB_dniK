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
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedArticlesFragment extends Fragment {


    public SavedArticlesFragment() {
        // Required empty public constructor
    }

    ListArticlesFragment.OnArticleSelectedListener mCallback;
    private ListView listView;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_articles, container, false);

        HashMap<String, String> savedList = ((MainActivity)getActivity()).loadMap();

        final List<String> quotesTitles = new ArrayList<>();
        final List<String> quotesContents = new ArrayList<>();
        final List<String> quotesCategories = new ArrayList<>();

        for (String key : savedList.keySet()) {
            int num =+ 0;
            quotesContents.add(num, ((MainActivity)getActivity()).getArticleContentbyID(key));
            quotesTitles.add(num, ((MainActivity)getActivity()).getArticleTitlebyID(key));
            quotesCategories.add(num, ((MainActivity)getActivity()).getCategory(key));
            Log.i("Cat:", quotesCategories.get(num));
        }
//
//        for (Map.Entry<String, String> entry : savedList.entrySet()) {
//            String key = entry.getKey();
//            Log.i("Key", key);
//            String value = entry.getValue();
//            Log.i("Value", value);
//            int num =+ 0;
//            quotesContents.add(num, ((MainActivity)getActivity()).getArticleContentbyID("1"));
//            quotesTitles.add(num, ((MainActivity)getActivity()).getArticleTitlebyID("1"));
//        }


//
//
//        final List<String> quotesTitles = ((MainActivity)getActivity()).getSaved("title");
//        final List<String> quotesContents = ((MainActivity)getActivity()).getSaved("content");

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
                    mCallback.onArticleSelected("SAVED", position);
                }
            });

        }
        else {
            Snackbar noArticleSnackbar = Snackbar.make(rootView.findViewById(R.id.listVi), R.string.no_saved_article, Snackbar.LENGTH_LONG);
            noArticleSnackbar.show();
            rootView = inflater.inflate(R.layout.no_saved_articles, container, false);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ListArticlesFragment.OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
