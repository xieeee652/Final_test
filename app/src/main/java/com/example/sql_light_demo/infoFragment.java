package com.example.sql_light_demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class infoFragment extends Fragment {
    TextView num,name,time,address,priority,record;
    ImageButton back;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info, container, false);

        num = view.findViewById(R.id.num);
        name = view.findViewById(R.id.name);
        time = view.findViewById(R.id.time);
        address = view.findViewById(R.id.address);
        priority = view.findViewById(R.id.priority);
        record = view.findViewById(R.id.record);
        back = view.findViewById(R.id.button_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick: ", "BackPressed");
                NavController navController = Navigation.findNavController(view);
                // Navigating to Previous Fragment
                navController.popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        infoFragmentArgs args = infoFragmentArgs.fromBundle(getArguments());
        num.setText("活動編號: " + args.getNum());
        name.setText("活動名稱: " + args.getName());
        time.setText("活動時間: " + args.getTime());
        address.setText("活動地點: " + args.getAddress());
        priority.setText("優先級: " + args.getPriority());
        record.setText("打卡情況: " + args.getRecord());

    }
}