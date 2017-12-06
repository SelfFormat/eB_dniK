package com.veriqus.savoirvivre;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;


public class TipFragment extends Fragment {



    public TipFragment() {
        // Required empty public constructor
    }

    View rootView;
    static String ARTICLE_NAME_TIP;
    String articleName;
    String articleContent;
    HashMap<String, String> savedList;
    boolean isSaved;
    ImageView imgPlace;
    TextView articleTitleText;
    TextView articleContentText;
    ViewGroup swipeCard;
    View swipeLay;
    ImageView saveIcon;
    int dip20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        setHasOptionsMenu(true);

        //pixel to dp conversion
        //Resources r = getResources();
        //dip20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        saveIcon = (ImageView) rootView.findViewById(R.id.saveArticleButton);
        savedList = ((MainActivity)getActivity()).loadMap();

        loadRandomArticle();


        swipeLay = rootView.findViewById(R.id.swipeLay);
        swipeLay.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            public void onSwipeRight() {
                loadRandomArticle();
            }
            public void onSwipeLeft() {
                loadRandomArticle();
            }
        });
        swipeCard = (ViewGroup) rootView.findViewById(R.id.cardViewSwipe);
        swipeCard.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            public void onSwipeRight() {
                loadRandomArticle();
            }
            public void onSwipeLeft() {
                loadRandomArticle();
            }
        });

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isSaved) {
                    String ID = ((MainActivity) getActivity()).getArticleID(articleName);
                    savedList.put(ID, ID);
                    ((MainActivity) getActivity()).saveMap(savedList);
                    saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_love_black));
                } else {
                    saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
                    String ID = ((MainActivity) getActivity()).getArticleID(articleName);
                    savedList.remove(ID);
                    ((MainActivity) getActivity()).saveMap(savedList);
                }
                isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));
            }
        });

        final ImageView previous = (ImageView) rootView.findViewById(R.id.previousArticleButton);
        previous.setVisibility(View.GONE);

        final ImageView next = (ImageView) rootView.findViewById(R.id.nextArticleButton);
        next.setVisibility(View.GONE);

        final ImageView refresh = (ImageView) rootView.findViewById(R.id.randomArticleButton);
        refresh.setVisibility(View.VISIBLE);

        final ImageView share = (ImageView) rootView.findViewById(R.id.shareArticleButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent(articleName, articleContent);

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRandomArticle();
            }
        });


        return rootView;
    }


    public void loadRandomArticle() {
        articleName = ((MainActivity)getActivity()).getRandromTitle();
        articleContent = ((MainActivity)getActivity()).getArticleContent(articleName);
        articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);
        articleTitleText.setText(articleName);
        articleContentText = (TextView) rootView.findViewById(R.id.articleContent);

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName),Html.FROM_HTML_MODE_LEGACY));
//                } else {
//                    articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName)));
//                }

        articleContentText.setText(articleContent);

        imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        byte[] data = ((MainActivity) getActivity()).getImageByte(articleName);
        int lenData = 0;
        if(!data.equals(null)){
            lenData = data.length;
        }
        if (!data.equals(null) && lenData > 1) {

            //imgPlace.setPadding(dip20, dip20, dip20 ,dip20);
            Log.i("Image:", "shown");
            Glide.with(getActivity())
                    .load(data).asBitmap()
                    .into(imgPlace);
            imgPlace.setVisibility(View.VISIBLE);
        } else {
            imgPlace.setVisibility(View.GONE);
        }

        isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));
        if (!isSaved) {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
        } else {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_love_black));
        }
    }

    private void shareIntent(String title, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + content);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

}
