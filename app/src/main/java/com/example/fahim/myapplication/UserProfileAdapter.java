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

public class UserProfileAdapter extends ArrayAdapter<JSONObject> implements View.OnClickListener

{
    private ArrayList<JSONObject> dataSet;
    private Context mContext;
    private MainActivity mainActivity;

    private static class ViewHolder {
        TextView usrName;
        TextView usrAddress;
        TextView usrPhone;
        Button userDetailButton;
    }

    public UserProfileAdapter(ArrayList<JSONObject> data, Context context, MainActivity ma) {
        super(context, R.layout.list_user_element, data);
        this.dataSet = data;
        this.mContext = context;
        mainActivity = ma;
    }

    @Override
    public void onClick(View view) {

    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        final JSONObject User = getItem(position);
        final UserProfileAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new UserProfileAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_user_element, parent, false);
            viewHolder.usrName = (TextView) convertView.findViewById(R.id.usrName);
            viewHolder.usrAddress = (TextView) convertView.findViewById(R.id.usrAddress);
            viewHolder.userDetailButton = (Button) convertView.findViewById(R.id.usrDetailButton);
            viewHolder.usrPhone = convertView.findViewById(R.id.usrPhone);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserProfileAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        lastPosition = position;

        try {
            String x = "";
            if (User.getString("first_name") != "null") x = User.getString("first_name");
            if (User.getString("last_name") != "null") x = x + " " + User.getString("last_name");
            viewHolder.usrName.setText(x);

            x="";
            if (User.getString("designation_name") != "null") x = User.getString("designation_name");
            if (User.getString("email") != "null") x = x+ " | "+ User.getString("email");
            viewHolder.usrAddress.setText(x);

            x="";
            if (User.getString("official_mobile_no") != "null") x =  User.getString("official_mobile_no");
            viewHolder.usrPhone.setText(x);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        viewHolder.userDetailButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                mainActivity.setContentView(R.layout.profile_detail_view);
                mainActivity.loadNavigationMenu();


                mainActivity.LIST_ITEM_CLICKED = position;

                TextView head = mainActivity.findViewById(R.id.headerText);
                head.setText("USER PROFILE");

                try {
                    //Uri uri = Uri.parse(USER.getString("profile_pic"));
                    //SimpleDraweeView proPic =  mainActivity.findViewById(R.id.userpic);
                    //proPic.setImageURI(uri);
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
                    String x = "";
                    if (User.getString("first_name") != "null")
                        x = User.getString("first_name");
                    if (User.getString("last_name") != "null")
                        x = x + " " + User.getString("last_name");
                    if (User.getString("designation_name") != "null")
                        x = x + "\n" + User.getString("designation_name");
                    a.setText(x);


                    a1.setText("Office Name");
                    if(User.getString("office_name")!="null")
                        a2.setText(User.getString("office_name"));

                    b1.setText("Division");
                    if(User.getString("division")!="null")
                        b2.setText(User.getString("division"));

                    c1.setText("Region");
                    if(User.getString("region")!="null")
                        c2.setText(User.getString("region"));

                    d1.setText("District");
                    if(User.getString("district")!="null")
                        d2.setText(User.getString("district"));

                    e1.setText("Upazilla");
                    if(User.getString("upazila")!="null")
                        e2.setText(User.getString("upazila"));

                    f1.setText("Phone No.");
                    if(User.getString("mobile_no")!="null")
                        f2.setText(User.getString("mobile_no"));

                    g1.setText("Phone No.");
                    if(User.getString("official_mobile_no")!="null")
                        g2.setText(User.getString("official_mobile_no"));

                    h1.setText("Email");
                    if(User.getString("email")!="null")
                        h2.setText(User.getString("email"));

                    i1.setText("NID");
                    if(User.getString("nid")!="null")
                        i2.setText(User.getString("nid"));
                }
                catch (Exception e) {e.printStackTrace();}

            }
        });


        return convertView;

    }
}