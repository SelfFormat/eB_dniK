package com.veriqus.savoirvivre;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
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
    HashMap<String, String> savedList;
    boolean isSaved;
    ImageView imgPlace;
    TextView articleTitleText;
    TextView articleContentText;
    ImageView saveIcon;
    int dip20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        setHasOptionsMenu(true);

        //pixel to dp conversion
        Resources r = getResources();
        dip20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        saveIcon = (ImageView) rootView.findViewById(R.id.saveArticleButton);
        savedList = ((MainActivity)getActivity()).loadMap();

        loadRandomArticle();

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

        final ImageView previous = (ImageView) rootView.findViewById(R.id.previousArticleButton);
        previous.setVisibility(View.GONE);

        final ImageView next = (ImageView) rootView.findViewById(R.id.nextArticleButton);
        next.setVisibility(View.GONE);

        final ImageView refresh = (ImageView) rootView.findViewById(R.id.randomArticleButton);
        refresh.setVisibility(View.VISIBLE);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRandomArticle();
            }
        });


        return rootView;
    }


    public void loadRandomArticle() {
        isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));
        if (!isSaved) {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
        } else {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_36dp));
        }

        articleName = ((MainActivity)getActivity()).getRandromTitle();

        articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);
        articleTitleText.setText(articleName);


        articleContentText = (TextView) rootView.findViewById(R.id.articleContent);

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName),Html.FROM_HTML_MODE_LEGACY));
//                } else {
//                    articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName)));
//                }

        articleContentText.setText(((MainActivity)getActivity()).getArticleContent(articleName));

        imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        byte[] data = ((MainActivity) getActivity()).getImageByte(articleName);
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

    public static Bitmap toBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

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
//    }
//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.action_favorite:
//                if(!isSaved) {
//                    String ID =  ((MainActivity)getActivity()).getArticleID(articleName);
//                    savedList.put(ID, ID);
//                    ((MainActivity)getActivity()).saveMap(savedList);
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
