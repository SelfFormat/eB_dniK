package com.veriqus.savoirvivre;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    public CategoryFragment(){}

    OnHeadlineSelectedListener mCallback;
    View rootView;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onSubCategorySelected(String name);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_category, container, false);

        LinearLayout mainLinear = (LinearLayout) rootView.findViewById(R.id.mainCategory_LinView);

        Resources r = getResources();

        int dip1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        int px20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, r.getDisplayMetrics());
        int dip20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
        int dip60 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics());
        int dip100 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        int dip150 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());


        List<Category> subCat_1 = new ArrayList<>();
        subCat_1.add(0, new Category("1", getContext().getString(R.string.subCat1_1_phone), R.drawable.icon_mobile));
        subCat_1.add(1, new Category("2", getContext().getString(R.string.subCat1_2_mail), R.drawable.icon_email));
        subCat_1.add(2, new Category("3", getContext().getString(R.string.subCat1_3_socialmedia), R.drawable.icon_social_media));


        List<Category> subCat_2 = new ArrayList<>();
        subCat_2.add(0, new Category("1", getContext().getString(R.string.subCat2_1_restaurant), R.drawable.icon_food_at_restaurant));
        subCat_2.add(1, new Category("2", getContext().getString(R.string.subCat2_2_home), R.drawable.icon_food_at_home));
        subCat_2.add(2, new Category("3", getContext().getString(R.string.subCat2_3_servingalcohol), R.drawable.icon_alcohol));

        List<Category> subCat_3 = new ArrayList<>();
        subCat_3.add(0, new Category("1", getContext().getString(R.string.subCat3_1_appearance), R.drawable.oval));
        subCat_3.add(1, new Category("2", getContext().getString(R.string.subCat3_2_gifts), R.drawable.oval));
        subCat_3.add(2, new Category("3", getContext().getString(R.string.subCat3_3_greetings), R.drawable.oval));
        subCat_3.add(3, new Category("4", getContext().getString(R.string.subCat3_4_guests), R.drawable.oval));
        subCat_3.add(4, new Category("5", getContext().getString(R.string.subCat3_5_dance), R.drawable.oval));
        subCat_3.add(5, new Category("6", getContext().getString(R.string.subCat3_6_date), R.drawable.oval));
        subCat_3.add(6, new Category("7", getContext().getString(R.string.subCat3_7_job), R.drawable.oval));
        subCat_3.add(7, new Category("8", getContext().getString(R.string.subCat3_8_disabled), R.drawable.oval));
        subCat_3.add(8, new Category("9", getContext().getString(R.string.subCat3_9_cigarettes), R.drawable.oval));
        subCat_3.add(9, new Category("10", getContext().getString(R.string.subCat3_10_conversation), R.drawable.oval));


        List<Category> subCat_4 = new ArrayList<>();
        subCat_4.add(0, new Category("1", getContext().getString(R.string.subCat4_1_birthday), R.drawable.birthday));
        subCat_4.add(1, new Category("2", getContext().getString(R.string.subCat4_2_wedding), R.drawable.icon_wedding));
        subCat_4.add(2, new Category("3", getContext().getString(R.string.subCat4_3_funeral), R.drawable.icon_grave));

        List<Category> subCat_5 = new ArrayList<>();
        subCat_5.add(0, new Category("1", getContext().getString(R.string.subCat5_1_religion), R.drawable.oval));
        subCat_5.add(1, new Category("2", getContext().getString(R.string.subCat5_2_shop), R.drawable.oval));
        subCat_5.add(2, new Category("3", getContext().getString(R.string.subCat5_3_gym), R.drawable.oval));
        subCat_5.add(3, new Category("4", getContext().getString(R.string.subCat5_4_sauna), R.drawable.oval));
        subCat_5.add(4, new Category("5", getContext().getString(R.string.subCat5_5_events), R.drawable.oval));
        subCat_5.add(5, new Category("6", getContext().getString(R.string.subCat5_6_publictransport), R.drawable.oval));
        subCat_5.add(6, new Category("7", getContext().getString(R.string.subCat5_7_journey), R.drawable.oval));
        subCat_5.add(7, new Category("8", getContext().getString(R.string.subCat5_8_hospital), R.drawable.oval));


        List<Category> subCat_6 = new ArrayList<>();
        subCat_6.add(0, new Category("1", getContext().getString(R.string.subCat6_1_dictionary), R.drawable.oval));
        subCat_6.add(1, new Category("2", getContext().getString(R.string.subCat6_2_animals), R.drawable.oval));
        subCat_6.add(2, new Category("3", getContext().getString(R.string.subCat6_3_children), R.drawable.oval));
        subCat_6.add(3, new Category("4", getContext().getString(R.string.subCat6_4_others), R.drawable.oval));


        ArrayList<Category> categories = new ArrayList<>();
        categories.add(0, new Category("1", getContext().getString(R.string.category_1), subCat_1));
        categories.add(1, new Category("2", getContext().getString(R.string.category_2), subCat_2));
        categories.add(2, new Category("3", getContext().getString(R.string.category_3), subCat_3));
        categories.add(3, new Category("4", getContext().getString(R.string.category_4), subCat_4));
        categories.add(4, new Category("5", getContext().getString(R.string.category_5), subCat_5));
        categories.add(5, new Category("6", getContext().getString(R.string.category_6), subCat_6));

        for (int i = 0; i < categories.size(); i++) {

            TextView categoryText = new TextView(getContext());
            categoryText.setText(categories.get(i).getName());
            categoryText.setTextSize(px20);
            categoryText.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));
            categoryText.setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(dip20, dip20, dip20, dip20);

            mainLinear.addView(categoryText, params);

            HorizontalScrollView horizonScrl = new HorizontalScrollView(getContext());
            horizonScrl.setHorizontalScrollBarEnabled(false);
            horizonScrl.setPadding(0,0, 0, dip20);
            horizonScrl.setOverScrollMode(View.OVER_SCROLL_NEVER);
            mainLinear.addView(horizonScrl);

            LinearLayout categoryItems = new LinearLayout(getContext());
            categoryItems.setOrientation(LinearLayout.HORIZONTAL);
            categoryItems.setPadding(dip20, 0, dip20, 0);
            horizonScrl.addView(categoryItems);



            for (int j = 0; j < categories.get(i).getSubCategories().size() ; j++) {

                ImageView subCategoryImage = new ImageView(getContext());
                subCategoryImage.setImageResource(categories.get(i).getSubCategories().get(j).getDrawable());
                LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(dip100, dip60);
                paramsImg.setMargins(dip20, dip20, dip20, dip20);

                TextView subCategoryText = new TextView(getContext());
                LinearLayout.LayoutParams subCategoryTextParams = new LinearLayout.LayoutParams(dip100, LinearLayout.LayoutParams.WRAP_CONTENT);
                subCategoryTextParams.setMargins(dip20, 0, dip20, dip20);
                final String subCategoryName = categories.get(i).getSubCategories().get(j).getName();
                //Log.i("String", subCategoryName);
                subCategoryText.setText(subCategoryName);
                subCategoryText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                subCategoryText.setLines(2);
                subCategoryText.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimaryDark));
                subCategoryText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                //subCategoryText.setEllipsize(TextUtils.TruncateAt.END);

                CardView cardView = new CardView(getContext());
                //cardView.setLayoutParams(new LinearLayout.LayoutParams(dip150, dip150));
                cardView.setUseCompatPadding(true);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onSubCategorySelected(subCategoryName);
                    }
                });

                LinearLayout pair = new LinearLayout(getContext());
                pair.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                pair.setOrientation(LinearLayout.VERTICAL);
                pair.addView(subCategoryImage, paramsImg);
                pair.addView(subCategoryText, subCategoryTextParams);
                cardView.addView(pair);
                categoryItems.addView(cardView);
            }

            View line = new View(getContext());
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dip1);
//            lineParams.setMargins(dip20, dip20, dip20, dip20);
            line.setBackgroundColor(Color.parseColor("#CCCCCC"));
            mainLinear.addView(line, lineParams);

        }

        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }



}