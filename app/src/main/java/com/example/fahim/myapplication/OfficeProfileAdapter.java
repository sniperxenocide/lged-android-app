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


public class OfficeProfileAdapter extends ArrayAdapter<JSONObject> implements View.OnClickListener
{

    private ArrayList<JSONObject> dataSet;
    Context mContext;
    MainActivity mainActivity;

    // View lookup cache
    private static class ViewHolder
    {
        TextView officeName;
        TextView officeAddress;
        Button detailBtn;
    }

    public OfficeProfileAdapter(ArrayList<JSONObject> data, Context context,MainActivity ma)
    {
        super(context, R.layout.list_office_element, data);
        this.dataSet = data;
        this.mContext=context;
        mainActivity = ma;

    }

    @Override
    public void onClick(View view) {

    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final JSONObject office = getItem(position);
        final OfficeProfileAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new OfficeProfileAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_office_element, parent, false);
            viewHolder.officeName = (TextView) convertView.findViewById(R.id.officeName);
            viewHolder.officeAddress = (TextView) convertView.findViewById(R.id.officeAddress);
            viewHolder.detailBtn = (Button) convertView.findViewById(R.id.detailBtn);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (OfficeProfileAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        lastPosition = position;

        try
        {
            viewHolder.officeName.setText(office.getString("name"));
            if(office.getString("address")!="null")
                viewHolder.officeAddress.setText(office.getString("address"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        viewHolder.detailBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                mainActivity.setContentView(R.layout.profile_detail_view);
                mainActivity.loadNavigationMenu();

                mainActivity.LIST_ITEM_CLICKED = position;


                TextView head = mainActivity.findViewById(R.id.headerText);
                head.setText("OFFICE PROFILE");

                try {
                    Uri uri = Uri.parse(office.getString("profile_pic"));
                    SimpleDraweeView proPic =  mainActivity.findViewById(R.id.userpic);
                    proPic.setImageURI(uri);
                }
                catch (Exception e) {e.printStackTrace();}



                TextView a,a1,a2,b1,b2,c1,c2,d1,d2,e1,e2,f1,f2,g1,g2,h1,h2,i1,i2;

                a  = mainActivity.findViewById(R.id.a);

                a1 = mainActivity.findViewById(R.id.a1);
                a2 = mainActivity.findViewById(R.id.a2);

                b1 = mainActivity.findViewById(R.id.b1);
                b2 = mainActivity.findViewById(R.id.b2);

                c1 = mainActivity.findViewById(R.id.c1);
                c2 = mainActivity.findViewById(R.id.c2);

                d1 = mainActivity.findViewById(R.id.d1);
                d2 = mainActivity.findViewById(R.id.d2);

                e1 = mainActivity.findViewById(R.id.e1);
                e2 = mainActivity.findViewById(R.id.e2);

                f1 = mainActivity.findViewById(R.id.f1);
                f2 = mainActivity.findViewById(R.id.f2);

                g1 = mainActivity.findViewById(R.id.g1);
                g2 = mainActivity.findViewById(R.id.g2);

                h1 = mainActivity.findViewById(R.id.h1);
                h2 = mainActivity.findViewById(R.id.h2);

                i1 = mainActivity.findViewById(R.id.i1);
                i2 = mainActivity.findViewById(R.id.i2);

                try
                {
                    a.setText(office.getString("name"));

                    a1.setText("Division");
                    if(office.getString("division_name")!="null")
                        a2.setText(office.getString("division_name"));

                    b1.setText("Region");
                    if(office.getString("region_name")!="null")
                        b2.setText(office.getString("region_name"));

                    c1.setText("District");
                    if(office.getString("district_name")!="null")
                        c2.setText(office.getString("district_name"));

                    d1.setText("Upazila");
                    if(office.getString("upazila_name")!="null")
                        d2.setText(office.getString("upazila_name"));

                    e1.setText("Class");
                    if(office.getString("pourashava_class")!="null")
                        e2.setText(office.getString("pourashava_class"));

                    f1.setText("Post Code");
                    if(office.getString("post_code")!="null")
                        f2.setText(office.getString("post_code"));

                    g1.setText("Phone No.");
                    if(office.getString("phone_no")!="null")
                        g2.setText(office.getString("phone_no"));

                    h1.setText("Fax No.");
                    if(office.getString("fax_no")!="null")
                        h2.setText(office.getString("fax_no"));

                    i1.setText("Details");
                    i2.setText("------");
                }
                catch (Exception e) {e.printStackTrace();}


            }
        });


        return convertView;
    }
}
