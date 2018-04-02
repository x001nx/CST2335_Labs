package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    Button deleteButton;
    TextView textMessage;
    TextView textID;

    boolean isTablet;
    long ID;
    String text;

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        Bundle infoToPass = getArguments();
        isTablet = (Boolean)infoToPass.get("isTablet");
        ID = (long)infoToPass.get("textID");
        text = (String)infoToPass.get("textMessage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.activity_fragment_details, null);
        textMessage = (TextView) fragmentView.findViewById(R.id.textMessage);
        textMessage.setText(text);
        textID = (TextView) fragmentView.findViewById(R.id.textID);
        textID.setText(String.valueOf(ID));

        deleteButton = (Button) fragmentView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isTablet){
                    ((ChatWindow) getActivity()).deleteMessage(ID);
                } else {
                    Intent delIntent = new Intent(getActivity(), ChatWindow.class);
                    delIntent.putExtra("ID", ID);
                    getActivity().setResult(50, delIntent);
                    getActivity().finish();
                }
            }
        });

        return fragmentView;
    }

}
