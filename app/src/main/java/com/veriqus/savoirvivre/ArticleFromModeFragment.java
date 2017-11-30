package com.veriqus.savoirvivre;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import static com.veriqus.savoirvivre.ModeFragment.PASSED_VALUE;


public class ArticleFromModeFragment extends Fragment {


    public ArticleFromModeFragment() {
        // Required empty public constructor
    }

    View rootView;
    static String ARTICLE_NAME;
    String articleName;
    int counter = 0;
    int listSize = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        //articleName = bundle.getString(ARTICLE_NAME);

        //pixel to dp conversion
        Resources r = getResources();
        final int dip20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        String categoryName = bundle.getString(PASSED_VALUE);
        String categoryID = ((MainActivity)getActivity()).getCategoryIDByName(categoryName);

//        TODO: Add dependencies if type is NULL (show all)

        final List<String> quotesTitles;
        final List<String> quotesContents;

        Log.i("CatID", categoryID);
        quotesContents = ((MainActivity) getActivity()).getArticleList(categoryID, "content");
        quotesTitles = ((MainActivity) getActivity()).getArticleList(categoryID, "title");

        listSize = quotesContents.size();
        articleName = quotesTitles.get(counter);

        //title textview just for header coloring
        final TextView articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);
        final TextView articleContentText = (TextView) rootView.findViewById(R.id.articleContent);
        final ImageView  imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);


//        String articleType = ((MainActivity) this.getContext()).getArticleType(articleName);
//
//        if (articleType.equals("good")) {
//            articleTitleText.setBackgroundColor(Color.parseColor("#009688"));
//            articleTitleText.setText(getContext().getText(R.string.good));
//        } else if (articleType.equals("bad")) {
//            articleTitleText.setBackgroundColor(Color.parseColor("#A54E4E"));
//            articleTitleText.setText(getContext().getText(R.string.bad));
//        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            articleContentText.setText(Html.fromHtml(quotesContents.get(counter),Html.FROM_HTML_MODE_LEGACY));
        } else {
            articleContentText.setText(Html.fromHtml(quotesContents.get(counter)));
        }

        // Retrieve the selected image as byte[]
        if (((MainActivity) getActivity()).getImageByte(articleName) != null) {
            imgPlace.setPadding(0,0,0,dip20);
            byte[] data = ((MainActivity) getActivity()).getImageByte(articleName);
            // Convert to Bitmap
            Bitmap image = toBitmap(data);
            // Set to the imgPlace
            imgPlace.setImageBitmap(image);
        }
//
//        Button goodButton = (Button) rootView.findViewById(R.id.goodButton);
//        goodButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (counter < listSize-1) {
//                    ++counter;
//                    articleName = quotesTitles.get(counter);
//
//                    String articleType = ((MainActivity) getContext()).getArticleType(articleName);
//
//                    if (articleType.equals("good")) {
//                        articleTitleText.setBackgroundColor(Color.parseColor("#009688"));
//                        articleTitleText.setText(getContext().getText(R.string.good));
//                    } else if (articleType.equals("bad")) {
//                        articleTitleText.setBackgroundColor(Color.parseColor("#A54E4E"));
//                        articleTitleText.setText(getContext().getText(R.string.bad));
//                    }
//
//                    if (counter < listSize) {
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                            articleContentText.setText(Html.fromHtml(quotesContents.get(counter), Html.FROM_HTML_MODE_LEGACY));
//                        } else {
//                            articleContentText.setText(Html.fromHtml(quotesContents.get(counter)));
//                        }
//                    }
//
//                    if (((MainActivity) getActivity()).getImageByte(articleName) != null) {
//                        imgPlace.setPadding(0, 0, 0, dip16toPx);
//                        byte[] data = ((MainActivity) getActivity()).getImageByte(articleName);
//                        // Convert to Bitmap
//                        Bitmap image = toBitmap(data);
//                        // Set to the imgPlace
//                        imgPlace.setImageBitmap(image);
//                    }
//
//
//                    //Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
//                }
//
//                else{
//                    Toast.makeText(getContext(), "NO MORE TO SHOW", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//


        return rootView;
    }

    public static Bitmap toBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }



    //Heart button option
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.article_actions, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_favorite:
                HashMap<String, String> savedList = ((MainActivity)getActivity()).loadMap();

                boolean isSaved = savedList.containsKey(((MainActivity)getActivity()).getArticleID(articleName));
                if(!isSaved) {
                    String ID =  ((MainActivity)getActivity()).getArticleID(articleName);
                    savedList.put(ID, ID);
                    ((MainActivity)getActivity()).saveMap(savedList);
//
//                    SharedPreferences.Editor editor = getContext().getSharedPreferences(SAVED, 0).edit();
//                    for (Map.Entry<String, String> entry : savedList.entrySet()) {
//                        editor.putString(entry.getKey(), entry.getValue());
//                    }
//
//                    ((MainActivity)getActivity()).save(articleName, 1);
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    String ID =  ((MainActivity)getActivity()).getArticleID(articleName);
                    savedList.remove(ID);
                    ((MainActivity)getActivity()).saveMap(savedList);


//                    ((MainActivity)getActivity()).save(articleName, 0);
                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
                }

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
