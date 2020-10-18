package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.view.HelpView;

public class HelpPresenter {

    private HelpView view;

    public HelpPresenter(){
        this.view = new HelpView();
    }

    public HelpView getView(){
        return view;
    }

}
