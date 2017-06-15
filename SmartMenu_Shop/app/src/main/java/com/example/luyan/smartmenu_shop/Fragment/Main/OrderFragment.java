package com.example.luyan.smartmenu_shop.Fragment.Main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.luyan.smartmenu_shop.Adapter.OrderAdapter;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    private Context context;
    private ArrayList<ORDERITEM> orderitems;
    private ListView listView;

    public OrderFragment() {
        // Required empty public constructor
    }

    public OrderFragment(Context contexts, ArrayList<ORDERITEM> orderitems) {
        // Required empty public constructor
        this.context = contexts;
        this.orderitems = orderitems;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        listView = (ListView) view.findViewById(R.id.order_list);
        initList();
        return view;
    }

    private void initList(){
        final OrderAdapter adapter = new OrderAdapter(getActivity(),orderitems);
        listView.setAdapter(adapter);
    }


}
