package com.prashant.shoppinglist;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Prashant on 2/12/2018.
 */

public class UploadTab extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getting the first part
        final View rootView = inflater.inflate(R.layout.upload_list_fragment, container, false);
        return  rootView;
    }




}
