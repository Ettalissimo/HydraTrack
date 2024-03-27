package com.example.hydratrack;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

public class Popup extends Dialog {

    private String title;
    private String subTitle;
    private Button button;
    private TextView titleView,subTitleView;

    public Popup(Activity activity){
        super(activity, com.google.android.material.R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.pop_up_suggestion);

        this.title="Mon titre";
        this.subTitle="Mon sous titre";
        this.button= findViewById(R.id.ok);
        this.titleView = findViewById(R.id.title);
        this.subTitleView = findViewById(R.id.subTitle);
    }

    public void setTitle(String title){
        this.title= title;
    }

    public void setSubTitle(String subTitle){
        this.subTitle = subTitle;
    }

    public Button getButton(){return button;}

    public void build(){
        show();
        titleView.setText(title);
        subTitleView.setText(subTitle);
    }
}
