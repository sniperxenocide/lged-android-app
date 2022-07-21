package com.example.fahim.myapplication;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.util.ArrayList;

public class LotAdapter extends ArrayAdapter<JSONObject> implements View.OnClickListener

{
    private ArrayList<JSONObject> dataSet;
    private Context mContext;
    private MainActivity mainActivity;

    private static class ViewHolder
    {
        TextView t_name;
        TextView status;
        Button detailButton;
    }

    public LotAdapter(ArrayList<JSONObject> data, Context context, MainActivity ma)
    {
        super(context, R.layout.list_office_element, data);
        this.dataSet = data;
        this.mContext = context;
        mainActivity = ma;
    }


    public void detailButtonFunction(JSONObject o, int p)
    {
        mainActivity.setContentView(R.layout.large_profile_detail_view);
        mainActivity.loadNavigationMenu();
        mainActivity.LIST_ITEM_CLICKED = p;
        TextView head = mainActivity.findViewById(R.id.headerText);
        head.setText("LOT DETAIL");
        TextView m1,m2,n1,n2,o1,o2,p1,p2,q1,q2,r1,r2,s1,s2,t1,t2,u1,u2,v1,v2,w1,w2,x1,x2,y1,y2,z1,z2;
        TextView a,a1,a2,b1,b2,c1,c2,d1,d2,e1,e2,f1,f2,g1,g2,h1,h2,i1,i2,j1,j2,k1,k2,l1,l2;
        a  = mainActivity.findViewById(R.id.a);a1 = mainActivity.findViewById(R.id.a1); a2 = mainActivity.findViewById(R.id.a2);b1 = mainActivity.findViewById(R.id.b1); b2 = mainActivity.findViewById(R.id.b2);c1 = mainActivity.findViewById(R.id.c1); c2 = mainActivity.findViewById(R.id.c2);d1 = mainActivity.findViewById(R.id.d1); d2 = mainActivity.findViewById(R.id.d2);
        e1 = mainActivity.findViewById(R.id.e1); e2 = mainActivity.findViewById(R.id.e2);f1 = mainActivity.findViewById(R.id.f1); f2 = mainActivity.findViewById(R.id.f2);g1 = mainActivity.findViewById(R.id.g1); g2 = mainActivity.findViewById(R.id.g2);h1 = mainActivity.findViewById(R.id.h1); h2 = mainActivity.findViewById(R.id.h2);i1 = mainActivity.findViewById(R.id.i1);
        i2 = mainActivity.findViewById(R.id.i2);j1 = mainActivity.findViewById(R.id.j1); j2 = mainActivity.findViewById(R.id.j2);k1 = mainActivity.findViewById(R.id.k1); k2 = mainActivity.findViewById(R.id.k2);l1 = mainActivity.findViewById(R.id.l1); l2 = mainActivity.findViewById(R.id.l2);m1 = mainActivity.findViewById(R.id.m1); m2 = mainActivity.findViewById(R.id.m2);
        n1 = mainActivity.findViewById(R.id.n1); n2 = mainActivity.findViewById(R.id.n2);o1 = mainActivity.findViewById(R.id.o1); o2 = mainActivity.findViewById(R.id.o2);p1 = mainActivity.findViewById(R.id.p1); p2 = mainActivity.findViewById(R.id.p2);q1 = mainActivity.findViewById(R.id.q1); q2 = mainActivity.findViewById(R.id.q2);r1 = mainActivity.findViewById(R.id.r1);
        r2 = mainActivity.findViewById(R.id.r2);s1 = mainActivity.findViewById(R.id.s1); s2 = mainActivity.findViewById(R.id.s2);t1 = mainActivity.findViewById(R.id.t1); t2 = mainActivity.findViewById(R.id.t2);u1 = mainActivity.findViewById(R.id.u1); u2 = mainActivity.findViewById(R.id.u2);v1 = mainActivity.findViewById(R.id.v1); v2 = mainActivity.findViewById(R.id.v2);
        w1 = mainActivity.findViewById(R.id.w1); w2 = mainActivity.findViewById(R.id.w2);x1 = mainActivity.findViewById(R.id.x1); x2 = mainActivity.findViewById(R.id.x2);y1 = mainActivity.findViewById(R.id.y1); y2 = mainActivity.findViewById(R.id.y2);z1 = mainActivity.findViewById(R.id.z1); z2 = mainActivity.findViewById(R.id.z2);

        try
        {
            //String x = "";
            String x = o.getString("office_name");
            if (x != "null") a.setText(x);

            a1.setText("Office Name");
            x = o.getString("office_name");
            if(x!="null") a2.setText(x);

            b1.setText("Division");
            x = o.getString("division_name");
            if(x!="null")  b2.setText(x);

            c1.setText("Region");
            x = o.getString("region_name");
            if(x!="null") c2.setText(x);

            d1.setText("District");
            x = o.getString("district_name");
            if(x!="null") d2.setText(x);

            e1.setText("Upazila");
            x = o.getString("upazila_name");
            if(x!="null") e2.setText(x);

            f1.setText("Package No.");
            x = o.getString("package_no_value");
            if(x!="null")  f2.setText(x);

            g1.setText("Lot Description");
            x = o.getString("lot_description");
            if(x!="null") g2.setText(x);

            h1.setText("Approval Date");
            x = o.getString("approval_date");
            if(x!="null") h2.setText(x);

            i1.setText("Cost");
            x = o.getString("cost");
            if(x!="null") i2.setText(x);

            j1.setText("Proc Method");
            x = o.getString("proc_method_value");
            if(x!="null") j2.setText(x);

            k1.setText("Proc Type");
            x = o.getString("proc_type_value");
            if(x!="null") k2.setText(x);

            l1.setText("Source of Fund");
            x = o.getString("source_of_fund_value");
            if(x!="null") l2.setText(x);

            m1.setText("Tender List");
            //x = o.getString("Lot_amount");
            //if(x!="null") m2.setText(x);

            /*n1.setText("Type");
            x = o.getString("type");
            if(x!="null") n2.setText(x);

            /*o1.setText("Approving Authority");
            x = o.getString("approving_authority_value");
            if(x!="null") o2.setText(x);

            p1.setText("NOA Issue Date");
            x = o.getString("noa_issuing_date");
            if(x!="null") p2.setText(x);

            q1.setText("Contract Signing Date");
            x = o.getString("contract_signing_date");
            if(x!="null") q2.setText(x);

            r1.setText("Contract Amount");
            x = o.getString("contract_amount");
            if(x!="null") r2.setText(x);

            s1.setText("Contractor Name");
            x = o.getString("contractor_name");
            if(x!="null") s2.setText(x);

            t1.setText("Status");
            x = o.getString("status");
            if(x!="null") t2.setText(x);

            u1.setText("Approval Status");
            x = o.getString("Lot_status");
            if(x!="null") u2.setText(x);

            v1.setText("Lot Type");
            x = o.getString("Lot_type");
            if(x!="null") v2.setText(x);*/

            //l1.setVisibility(View.GONE);
            //m1.setVisibility(View.GONE);
            //n1.setVisibility(View.GONE);
            o1.setVisibility(View.GONE);
            p1.setVisibility(View.GONE);
            q1.setVisibility(View.GONE);
            r1.setVisibility(View.GONE);
            s1.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
            u1.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            w1.setVisibility(View.GONE);
            x1.setVisibility(View.GONE);
            y1.setVisibility(View.GONE);
            z1.setVisibility(View.GONE);


            //l2.setVisibility(View.GONE);
            //m2.setVisibility(View.GONE);
            //n2.setVisibility(View.GONE);
            o2.setVisibility(View.GONE);
            p2.setVisibility(View.GONE);
            q2.setVisibility(View.GONE);
            r2.setVisibility(View.GONE);
            s2.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            u2.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            w2.setVisibility(View.GONE);
            x2.setVisibility(View.GONE);
            y2.setVisibility(View.GONE);
            z2.setVisibility(View.GONE);


        }
        catch (Exception e) {e.printStackTrace(); }

    }

    @Override
    public void onClick(View view) { }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        final JSONObject Lot = getItem(position);
        final LotAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new LotAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_office_element, parent, false);
            viewHolder.t_name = (TextView) convertView.findViewById(R.id.officeName);
            viewHolder.status = (TextView) convertView.findViewById(R.id.officeAddress);
            viewHolder.detailButton = (Button) convertView.findViewById(R.id.detailBtn);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LotAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        lastPosition = position;

        try {
            String x = "";
            if (Lot.getString("office_name") != "null") x = Lot.getString("office_name");
            viewHolder.t_name.setText(x);

            x="";
            if (Lot.getString("package_no_value") != "null") x = Lot.getString("package_no_value");
            viewHolder.status.setText(x);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewHolder.detailButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                detailButtonFunction(Lot,position);
            }
        });


        return convertView;

    }
}