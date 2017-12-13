package com.veriqus.savoirvivre;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;


public class ArticleSubcategoryFragment extends Fragment {

    View rootView;
    String articleName;
    String articleContent;
    HashMap<String, String> savedList;
    boolean isSaved;
    int position;
    List<String> quotesTitles;
    List<String> quotesContents;
    ImageView saveIcon;
    TextView articleContentTextView;
    TextView articleTitleText;
    TextView startQuiz;
    ViewGroup swipeCard;
    View swipeLay;
    int dip16toPx;
    ProgressBar progressBar;
    TextView progressTextView;
    int articleListLenght;
    SharedPreferences.Editor edit;
    public static String ca1;
    OnQuizSelectedListener mCallback;
    String subCategoryID;
    int categoryID;

    public interface OnQuizSelectedListener {
        public void onQuizSelected(String quizCategory, int category);
    }


    public ArticleSubcategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article_learning, container, false);
        setHasOptionsMenu(true);

        final SharedPreferences cat1pref = getContext().getSharedPreferences(ca1, 0);

        //pixel to dp conversion
        //Resources r = getResources();
        //dip16toPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());

        Bundle bundle = getArguments();
        subCategoryID = bundle.getString("SUBCATEGORY_ID");
        categoryID = bundle.getInt("CATEGORY");
        Log.i("SubCatID", subCategoryID);
        Log.i("category", categoryID+"");

        ca1 = subCategoryID;
        position = 0;
        //position = bundle.getInt("ARTICLE_POSITION");

        swipeLay = rootView.findViewById(R.id.swipeLay);
        swipeLay.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            public void onSwipeRight() {
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                if (position > 0) {
                    position--;
                } else {
                }
                loadArticle();
                updateProgressViews();

            }
            public void onSwipeLeft() {
                Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                if (position < quotesTitles.size() - 1) {
                    position++;
                } else {
                    edit = cat1pref.edit();
                    edit.putBoolean(ca1, true);
                    edit.apply();
                    edit.commit();
                }
                loadArticle();
                updateProgressViews();
            }
        });
        swipeCard = (ViewGroup) rootView.findViewById(R.id.cardViewSwipe);
        swipeCard.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            public void onSwipeRight() {
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                if (position > 0) {
                    position--;
                } else {
                }
                loadArticle();
                updateProgressViews();
            }
            public void onSwipeLeft() {
                //Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                if (position < quotesTitles.size() - 1) {
                    position++;
                } else {
                    edit = cat1pref.edit();
                    edit.putBoolean(ca1, true);
                    edit.apply();
                    edit.commit();
                }
                loadArticle();
                updateProgressViews();
            }
        });


        quotesTitles = ((MainActivity) getActivity()).getQuizArticleList(subCategoryID, "title");
        quotesContents = ((MainActivity) getActivity()).getQuizArticleList(subCategoryID, "content");

        articleListLenght = quotesTitles.size();

        articleContentTextView = (TextView) rootView.findViewById(R.id.articleContent);
        articleTitleText = (TextView) rootView.findViewById(R.id.articleTitle);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressTextView = (TextView) rootView.findViewById(R.id.progress_textView);

        loadArticle();
        updateProgressViews();

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
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
                if (position > 0) {
                    position--;
                } else {
                }
                loadArticle();
                updateProgressViews();
            }
        });

        final ImageView next = (ImageView) rootView.findViewById(R.id.nextArticleButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < quotesTitles.size() - 1) {
                    position++;
                } else {
                }
                loadArticle();
                updateProgressViews();
            }
        });


        return rootView;
    }

    private void shareIntent(String title, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + content);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void save(){
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
        isSaved = savedList.containsKey(((MainActivity) getActivity()).getArticleID(articleName));
    }

    private void loadArticle() {
        articleName = quotesTitles.get(position);
        articleContent = quotesContents.get(position);
        articleTitleText.setText(articleName);

//        FOR HTML CONVERSION

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            articleContent = Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName),Html.FROM_HTML_MODE_LEGACY).toString();
//            Log.i(">","25");
//        } else {
//            articleContent = Html.fromHtml(((MainActivity)getActivity()).getArticleContent(articleName)).toString();
//            Log.i("<","21");
//        }

        articleContentTextView.setText(articleContent);

        ImageView imgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        byte[] imgByteData = ((MainActivity) getActivity()).getImageByte(quotesTitles.get(position));
        int lenData = 0;
        if (!imgByteData.equals(null)) {
            lenData = imgByteData.length;
        }

        if (!imgByteData.equals(null) && lenData > 1) {
//            imgPlace.setPadding(dip16toPx, dip16toPx, dip16toPx, dip16toPx);
            Log.i("Image:", "shown");
            Glide.with(getActivity())
                    .load(imgByteData).asBitmap()
                    .into(imgPlace);
            imgPlace.setVisibility(View.VISIBLE);
        } else {
            imgPlace.setVisibility(View.GONE);
        }

        saveIcon = (ImageView) rootView.findViewById(R.id.saveArticleButton);
        savedList = ((MainActivity) getActivity()).loadMap();
        isSaved = savedList.containsKey(((MainActivity) getActivity()).getArticleID(articleName));

        if (!isSaved) {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
        } else {
            saveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_love_black));
        }
    }

    private void updateProgressViews() {
        int progressposition = position + 1;
        progressBar.setMax(articleListLenght);
        progressBar.setProgress(progressposition);
        progressTextView.setText(progressposition + " / " + articleListLenght);
        startQuiz = (TextView) rootView.findViewById(R.id.start_quiz_button);
        startQuiz.setVisibility(View.GONE);
        if(progressBar.getProgress() == progressBar.getMax()) {
            startQuiz.setVisibility(View.VISIBLE);
            startQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onQuizSelected(subCategoryID, categoryID);
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnQuizSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
