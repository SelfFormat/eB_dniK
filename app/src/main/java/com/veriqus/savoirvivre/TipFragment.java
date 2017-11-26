package com.veriqus.savoirvivre;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        setHasOptionsMenu(true);
//        Bundle bundle = getArguments();
//        articleName = bundle.getString(ARTICLE_NAME_TIP);
        articleName = ((MainActivity)getActivity()).getRandromTitle();

        //pixel to dp conversion
        Resources r = getResources();
        final int dip20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());


        TextView articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);
        articleTitleText.setText(articleName);

//        String articleType = ((MainActivity) this.getContext()).getArticleType(articleName);
//
//        if (articleType.equals("good")) {
//            articleTitleText.setBackgroundColor(Color.parseColor("#009688"));
//            articleTitleText.setText(getContext().getText(R.string.good));
//        } else if (articleType.equals("bad")) {
//            articleTitleText.setBackgroundColor(Color.parseColor("#A54E4E"));
//            articleTitleText.setText(getContext().getText(R.string.bad));
//        }

        TextView articleContentText = (TextView) rootView.findViewById(R.id.articleContent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName),Html.FROM_HTML_MODE_LEGACY));
        } else {
            articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName)));
        }

        ImageView  imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);

        // Retrieve the selected image as byte[]
        if (((MainActivity) getActivity()).getImageByte(articleName) != null) {
            imgPlace.setPadding(0,0,0,dip20);
            byte[] data = ((MainActivity) getActivity()).getImageByte(articleName);
            // Convert to Bitmap
            Bitmap image = toBitmap(data);
            // Set to the imgPlace
            imgPlace.setImageBitmap(image);
        }


        final ImageView saveIcon = (ImageView) rootView.findViewById(R.id.saveArticleButton);
        savedList = ((MainActivity)getActivity()).loadMap();
        isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));


        Button goodButton = (Button) rootView.findViewById(R.id.goodButton);
        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));
                if (!isSaved) {
                    saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
                } else {
                    saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_36dp));
                }


                // TODO: Na razie skopiowane. poprawic ponizszy kod zeby nie wyswietlal 2 razy tego samego
                articleName = ((MainActivity)getActivity()).getRandromTitle();

                TextView articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);
                articleTitleText.setText(articleName);

//                String articleType = ((MainActivity) getContext()).getArticleType(articleName);
//
//                if (articleType.equals("good")) {
//                    articleTitleText.setBackgroundColor(Color.parseColor("#009688"));
//                    articleTitleText.setText(getContext().getText(R.string.good));
//                } else if (articleType.equals("bad")) {
//                    articleTitleText.setBackgroundColor(Color.parseColor("#A54E4E"));
//                    articleTitleText.setText(getContext().getText(R.string.bad));
//                }

                TextView articleContentText = (TextView) rootView.findViewById(R.id.articleContent);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    articleContentText.setText(Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName)));
                }

                ImageView  imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);

                // Retrieve the selected image as byte[]
                if (((MainActivity) getActivity()).getImageByte(articleName) != null) {
                    imgPlace.setPadding(0,0,0,dip20);
                    byte[] data = ((MainActivity) getActivity()).getImageByte(articleName);
                    // Convert to Bitmap
                    Bitmap image = toBitmap(data);
                    // Set to the imgPlace
                    imgPlace.setImageBitmap(image);
                }
            }
        });


        if (!isSaved) {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
        } else {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
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

        return rootView;
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
