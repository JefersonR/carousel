package com.example.jeferson.carouseltest.widgets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeferson.carouseltest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarouselItemFragment extends Fragment {
    private View view;

    @BindView(R.id.txt_title)
    TextView txt_title;

    public CarouselItemFragment() {
        // Required empty public constructor
    }

    public final String TITLE = "TITLE";
    public final String POS   = "POS";
    public final String COLOR = "COLOR";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_item_carousel, container, false);
        } catch (InflateException e) {
            e.getMessage();
        }
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            txt_title.setText(getArguments().getString(TITLE));
            if (getArguments().getInt(POS) == 0) {
                switch (getArguments().getInt(COLOR)) {
                    case 0:
                        updateTitleNew(PineCarouselPanel.colorTitle.gray);
                        break;
                    case 1:
                        updateTitleNew(PineCarouselPanel.colorTitle.brown);
                        break;
                    case 2:
                        updateTitleNew(PineCarouselPanel.colorTitle.purple);
                        break;
                }

            }
        }
        return view;
    }

    public void updateTitleOld() {
        txt_title.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGrayFavored));
        txt_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textTitleUnselectNotification));

    }

    public void updateTitleNew(PineCarouselPanel.colorTitle colorTitle) {
        switch (colorTitle) {
            case gray:
                txt_title.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGray));
                break;
            case brown:
                txt_title.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBrown));
                break;
            case purple:
                txt_title.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                break;
        }
        txt_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textTitleNotification));
    }
}
