package com.veriqus.savoirvivre;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ArticleFragment extends Fragment {


    public ArticleFragment() {
        // Required empty public constructor
    }

    View rootView;
    static String ARTICLE_NAME;
    String articleName;
    String articleContent;
    HashMap<String, String> savedList;
    boolean isSaved;
    int position;
    List<String> quotesTitles;
    List<String> quotesContents;
    TextView articleContentTextView;
    TextView articleTitleText;
    int dip20;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        setHasOptionsMenu(true);


        //pixel to dp conversion
        Resources r = getResources();
        dip20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        Bundle bundle = getArguments();
        String categoryID = bundle.getString("CATEGORY_ID");
        Log.i("CatID", categoryID);
        position = bundle.getInt("ARTICLE_POSITION");


        if(categoryID.equals("SAVED"))
        {
            HashMap<String, String> savedList = ((MainActivity)getActivity()).loadMap();

            quotesTitles = new ArrayList<>();
            quotesContents = new ArrayList<>();

            for (String key : savedList.keySet()) {
                int num =+ 0;
                quotesContents.add(num, ((MainActivity)getActivity()).getArticleContentbyID(key));
                quotesTitles.add(num, ((MainActivity)getActivity()).getArticleTitlebyID(key));

            }
        }
        else {
            quotesTitles = ((MainActivity) getActivity()).getArticleList(categoryID, "title");
            quotesContents = ((MainActivity) getActivity()).getArticleList(categoryID, "content");
        }

        articleContentTextView = (TextView) rootView.findViewById(R.id.articleContent);
        articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);



        loadArticle();



        final ImageView saveIcon = (ImageView) rootView.findViewById(R.id.saveArticleButton);
        savedList = ((MainActivity)getActivity()).loadMap();
        isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));

        if (!isSaved) {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
        } else {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_36dp));
        }


        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isSaved) {
                    String ID = ((MainActivity) getActivity()).getArticleID(articleName);
                    savedList.put(ID, ID);
                    ((MainActivity) getActivity()).saveMap(savedList);
                    saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_36dp));


//
//                    SharedPreferences.Editor editor = getContext().getSharedPreferences(SAVED, 0).edit();
//                    for (Map.Entry<String, String> entry : savedList.entrySet()) {
//                        editor.putString(entry.getKey(), entry.getValue());
//                    }
//
//                    ((MainActivity)getActivity()).save(articleName, 1);
                    //Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
                    String ID = ((MainActivity) getActivity()).getArticleID(articleName);
                    savedList.remove(ID);
                    ((MainActivity) getActivity()).saveMap(savedList);


//                    ((MainActivity)getActivity()).save(articleName, 0);
                    //Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
                }
                isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));
            }
        });



        final ImageView share = (ImageView) rootView.findViewById(R.id.shareArticleButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent(articleName, articleContent);

            }
        });


        final ImageView previous = (ImageView) rootView.findViewById(R.id.previousArticleButton);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > 0) {
                    position--;
                } else {
                    position = quotesTitles.size()-1;
                }
                loadArticle();
            }
        });

        final ImageView next = (ImageView) rootView.findViewById(R.id.nextArticleButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < quotesTitles.size()-1) {
                    position++;
                } else {
                    position=0;
                }
                loadArticle();
            }
        });



        return rootView;
    }

    public static Bitmap toBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    private void shareIntent(String title, String content){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + content);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void loadArticle() {
        articleName = quotesTitles.get(position);
        articleTitleText.setText(articleName);

//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            articleContent = Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName),Html.FROM_HTML_MODE_LEGACY).toString();
//            Log.i(">","25");
//        } else {
//            articleContent = Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName)).toString();
//            Log.i("<","21");
//        }

        articleContentTextView.setText(quotesContents.get(position));

        ImageView  imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        byte[] data = ((MainActivity) getActivity()).getImageByte(quotesTitles.get(position));
        int lenData = 0;
        if(!data.equals(null)){
            lenData = data.length;
        }


        // Retrieve the selected image as byte[]
        if (!data.equals(null) && lenData > 1) {

            imgPlace.setPadding(dip20, dip20, dip20 ,dip20);
//            byte[] data = ((MainActivity) getActivity()).getImageByte(quotesTitles.get(position));
            Log.i("Image:", "shown");
            // Convert to Bitmap

            //Drawable image = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(data, 0, data.length));
            //Bitmap image = toBitmap(data);
            // Set to the imgPlace

            Glide.with(getActivity())
                    .load(data).asBitmap()
                    .into(imgPlace);
            //imgPlace.setImageBitmap(image);
            imgPlace.setVisibility(View.VISIBLE);
        } else {
            imgPlace.setVisibility(View.GONE);
        }
    }


//    //Heart button option
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        savedList = ((MainActivity)getActivity()).loadMap();
//        isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));
//        if(isSaved){
//            inflater.inflate(R.menu.article_actions_alreadysaved, menu);
//        }
//        else{
//            inflater.inflate(R.menu.article_actions, menu);
//        }
//        super.onCreateOptionsMenu(menu,inflater);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.action_favorite:
//
//                if(!isSaved) {
//                    String ID =  ((MainActivity)getActivity()).getArticleID(articleName);
//                    savedList.put(ID, ID);
//                    ((MainActivity)getActivity()).saveMap(savedList);
//
////
////                    SharedPreferences.Editor editor = getContext().getSharedPreferences(SAVED, 0).edit();
////                    for (Map.Entry<String, String> entry : savedList.entrySet()) {
////                        editor.putString(entry.getKey(), entry.getValue());
////                    }
////
////                    ((MainActivity)getActivity()).save(articleName, 1);
//                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    String ID =  ((MainActivity)getActivity()).getArticleID(articleName);
//                    savedList.remove(ID);
//                    ((MainActivity)getActivity()).saveMap(savedList);
//
//
////                    ((MainActivity)getActivity()).save(articleName, 0);
//                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
//                }
//
//                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
}
