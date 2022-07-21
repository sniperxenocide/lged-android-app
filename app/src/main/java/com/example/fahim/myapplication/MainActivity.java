package com.example.fahim.myapplication;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;



public class MainActivity extends AppCompatActivity
{

    public static final String REQUEST_METHOD  = "GET";
    public static final int READ_TIMEOUT       = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static int START             = 0;
    public static int LIST_ITEM_CLICKED = 0;
    public static int FETCH             = 0;
    public static int SEARCH            = 1;
    public static int LOAD_MORE         = 2;
    public static int BACK_FROM_PROFILE = 3;
    public static boolean isLoggedIn    = false;
    public static boolean searchFlag    = false;

    public static String AccessToken     = "";
    public static String currentUser     = "";
    public static String currentPassword ="";
    public static String tokenAuthUrl    = "http://182.160.114.45:8082/lged/api/v1//api-token-auth/";
    public static String tokenVerifyUrl  = "http://182.160.114.45:8082/lged/api/v1//api-token-verify/";

    public static String officeUrl   = "http://182.160.114.45:8082/lged/api/v1/office/";
    public static String userUrl     = "http://182.160.114.45:8082/lged/api/v1/users/";
    public static String tenderUrl   = "http://182.160.114.45:8082/lged/api/v1/tender/";
    public static String projectUrl  = "http://182.160.114.45:8082/lged/api/v1/project/";
    public static String budgetUrl   = "http://182.160.114.45:8082/lged/api/v1/budget/";
    public static String appUrl      = "http://182.160.114.45:8082/lged/api/v1/app/";
    public static String lotUrl      = "http://182.160.114.45:8082/lged/api/v1/lot/";
    public static String packageUrl  = "http://182.160.114.45:8082/lged/api/v1/package/";
    public static String contractUrl = "http://182.160.114.45:8082/lged/api/v1/contract/";

    public static ArrayList<JSONObject> officeList   = new ArrayList<>();
    public static ArrayList<JSONObject> usersList    = new ArrayList<>();
    public static ArrayList<JSONObject> tenderList   = new ArrayList<>();
    public static ArrayList<JSONObject> appList      = new ArrayList<>();
    public static ArrayList<JSONObject> packageList  = new ArrayList<>();
    public static ArrayList<JSONObject> lotList      = new ArrayList<>();
    public static ArrayList<JSONObject> contractList = new ArrayList<>();
    public static ArrayList<JSONObject> projectList  = new ArrayList<>();
    public static ArrayList<JSONObject> budgetList   = new ArrayList<>();
    public static JSONObject CURRENT_USER ;

    public static ListView listView;



    //****************************************public items and buttons

    public void aboutButton(View view) { }
    public void menuAbout(MenuItem item) {}

    public void egpButton(View view) { }
    public void menuEGP(MenuItem item) {}

    public void downloadButton(View view) { }
    public void menuDownload(MenuItem item) {}

    public void galleryButton(View view) { }
    public void menuGallery(MenuItem item) {}

    public void noticeButton(View view) { }
    public void menuNotice(MenuItem item) {}

    public void contactButton(View view) { }
    public void menuContact(MenuItem item) {}

    public void linkButton(View view) { }
    public void menuLink(MenuItem item) { }

    public void loginButton(View view)
    {
        if(isLoggedIn==false) setContentView(R.layout.login);
        else showToast("You are already logged in");
    }
    public void menuLogin(MenuItem item) { loginButton(item.getActionView()); }


    //public items and buttons************************************************



    public void burgerButton(View view)
    {
        DrawerLayout d = findViewById(R.id.drawer_layout);
        d.openDrawer(Gravity.LEFT);
    }
    public void continueButton(View view)
    {
        new loginThread().execute("");
        hideKeyboard(view);
    }
    public void backButton(View view)
    {
        searchFlag =false;
        if(isLoggedIn)
        {
            setContentView(R.layout.home_private);
            loadNavigationMenu();
            START = 0;
            officeList.clear();
            usersList.clear();
            tenderList.clear();
            projectList.clear();
            budgetList.clear();
            appList.clear();
            contractList.clear();
            lotList.clear();
            packageList.clear();
        }
        else setContentView(R.layout.home);

        hideKeyboard(view);
    }
    public void backFromProfileViewButton(View view)
    {
        setContentView(R.layout.list_page);
        loadNavigationMenu();
        TextView t = findViewById(R.id.headerText);
        if(listView.getAdapter().getClass().equals(OfficeProfileAdapter.class))
        {
            t.setText("OFFICE PROFILE");
            new officeProfileThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(UserProfileAdapter.class))
        {
            t.setText("USER PROFILE");
            new userProfileThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(TenderAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            x.setText("TENDER LIST");
            Button b = findViewById(R.id.tender);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            new tenderThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(AppAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            x.setText("APP LIST");
            Button b = findViewById(R.id.app);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            new appThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(LotAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            x.setText("LOT LIST");
            Button b = findViewById(R.id.lot);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
            new Handler().postDelayed(new Runnable()
            {
                public void run() {
                    hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
            new lotThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(PackageAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            x.setText("PACKAGE LIST");
            Button b = findViewById(R.id.pkg);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            new packageThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(ContractAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            x.setText("CONTRACT LIST");
            Button b = findViewById(R.id.contract);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
            new Handler().postDelayed(new Runnable()
            {
                public void run() {
                    hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
            new contractThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(ProjectAdapter.class))
        {
            t.setText("PROJECT LIST");
            new projectThread(BACK_FROM_PROFILE).execute(" ");
        }
        else if(listView.getAdapter().getClass().equals(BudgetAdapter.class))
        {
            t.setText("BUDGET LIST");
            new budgetThread(BACK_FROM_PROFILE).execute(" ");
        }

    }
    public void searchButton(View view)
    {
        searchFlag = true;
        EditText e = findViewById(R.id.searchBox);
        String value = e.getText().toString();
        if( value.isEmpty()) return;

        START = 0;
        LIST_ITEM_CLICKED = 0;
        setContentView(R.layout.list_page);
        loadNavigationMenu();
        TextView t = findViewById(R.id.headerText);

        if(listView.getAdapter().getClass().equals(OfficeProfileAdapter.class))
        {
            officeList.clear();
            t.setText("OFFICE PROFILE");
            new officeProfileThread(SEARCH).execute(e.getText().toString());
        }
        else if(listView.getAdapter().getClass().equals(UserProfileAdapter.class))
        {
            usersList.clear();
            t.setText("USER PROFILE");
            new userProfileThread(SEARCH).execute(e.getText().toString());
        }
        else if(listView.getAdapter().getClass().equals(TenderAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            tenderList.clear();
            x.setText("TENDER LIST");
            Button b = findViewById(R.id.tender);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            new tenderThread(SEARCH).execute(value);
        }
        else if(listView.getAdapter().getClass().equals(AppAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            tenderList.clear();
            x.setText("APP LIST");
            Button b = findViewById(R.id.app);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            new appThread(SEARCH).execute(value);
        }
        else if(listView.getAdapter().getClass().equals(LotAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            lotList.clear();
            x.setText("LOT LIST");
            Button b = findViewById(R.id.lot);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
            new Handler().postDelayed(new Runnable()
            {
                public void run() {
                    hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
            new lotThread(SEARCH).execute(value);
        }
        else if(listView.getAdapter().getClass().equals(PackageAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            packageList.clear();
            x.setText("PACKAGE LIST");
            Button b = findViewById(R.id.pkg);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            new packageThread(SEARCH).execute(value);
        }
        else if(listView.getAdapter().getClass().equals(ContractAdapter.class))
        {
            setContentView(R.layout.tender_status_page);
            loadNavigationMenu();
            TextView x = findViewById(R.id.headerText);
            contractList.clear();
            x.setText("CONTRACT LIST");
            Button b = findViewById(R.id.contract);
            b.setBackgroundColor(getResources().getColor(R.color.light_purple));
            final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
            new Handler().postDelayed(new Runnable()
            {
                public void run() {
                    hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
            new contractThread(SEARCH).execute(value);
        }
        else if(listView.getAdapter().getClass().equals(ProjectAdapter.class))
        {
            projectList.clear();
            t.setText("PROJECT LIST");
            new projectThread(SEARCH).execute(e.getText().toString());
        }
        else if(listView.getAdapter().getClass().equals(BudgetAdapter.class))
        {
            budgetList.clear();
            t.setText("BUDGET LIST");
            new budgetThread(SEARCH).execute(e.getText().toString());
        }
        hideKeyboard(view);
    }



    //start***************************************************private items and buttons

    public void officeProfileButton(View view)
    {
        //officeDemo();
        if(!isLoggedIn) return;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        officeList.clear();
        setContentView(R.layout.list_page);
        loadNavigationMenu();
        TextView t = findViewById(R.id.headerText);
        t.setText("OFFICE PROFILE");
        new officeProfileThread(FETCH).execute(" ");
    }
    public void menuOfficeProfile(MenuItem item) {officeProfileButton(item.getActionView());}


    public void userProfileButton(View view)
    {
        if(!isLoggedIn) return;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        usersList.clear();
        setContentView(R.layout.list_page);
        loadNavigationMenu();
        TextView t = findViewById(R.id.headerText);
        t.setText("USER PROFILE");
        new userProfileThread(FETCH).execute(" ");
    }
    public void menuUserProfile(MenuItem item) {userProfileButton(item.getActionView());}


    public void projectProfileButton(View view)
    {
        if(!isLoggedIn) return;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        projectList.clear();
        setContentView(R.layout.list_page);
        loadNavigationMenu();
        TextView t = findViewById(R.id.headerText);
        t.setText("PROJECT LIST");
        new projectThread(FETCH).execute(" ");
    }
    public void menuProjectProfile(MenuItem item) {projectProfileButton(item.getActionView());}


    public void tenderStatusButton(View view) {appButton(view);}
    public void tenderDemo()
    {
        setContentView(R.layout.tender_status_page);
        loadNavigationMenu();
        TextView t = findViewById(R.id.headerText);
        t.setText("TENDER STATUS");
    }
    public void tenderButton(View view)
    {
        tenderDemo();
        Button x = findViewById(R.id.tender);
        x.setBackgroundColor(getResources().getColor(R.color.light_purple));
        if(!isLoggedIn) return;
        searchFlag = false;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        tenderList.clear();
        TextView t = findViewById(R.id.headerText);
        t.setText("TENDER LIST");
        new tenderThread(FETCH).execute(" ");
    }
    public void appButton(View view)
    {
        tenderDemo();
        Button x = findViewById(R.id.app);
        x.setBackgroundColor(getResources().getColor(R.color.light_purple));
        if(!isLoggedIn) return;
        searchFlag = false;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        appList.clear();
        TextView t = findViewById(R.id.headerText);
        t.setText("APP LIST");
        new appThread(FETCH).execute(" ");
    }
    public void lotButton(View view)
    {
        tenderDemo();
        Button x = findViewById(R.id.lot);
        x.setBackgroundColor(getResources().getColor(R.color.light_purple));
        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        new Handler().postDelayed(new Runnable()
        {
            public void run() {
                hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
        if(!isLoggedIn) return;
        searchFlag = false;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        lotList.clear();
        TextView t = findViewById(R.id.headerText);
        t.setText("LOT LIST");
        new lotThread(FETCH).execute(" ");
    }
    public void packageButton(View view)
    {
        tenderDemo();
        Button x = findViewById(R.id.pkg);
        x.setBackgroundColor(getResources().getColor(R.color.light_purple));
        if(!isLoggedIn) return;
        searchFlag = false;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        packageList.clear();
        TextView t = findViewById(R.id.headerText);
        t.setText("PACKAGE LIST");
        new packageThread(FETCH).execute(" ");
    }
    public void contractButton(View view)
    {
        tenderDemo();
        Button x = findViewById(R.id.contract);
        x.setBackgroundColor(getResources().getColor(R.color.light_purple));

        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        new Handler().postDelayed(new Runnable()
        {
            public void run() {
                hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);

        if(!isLoggedIn) return;
        searchFlag = false;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        contractList.clear();
        TextView t = findViewById(R.id.headerText);
        t.setText("CONTRACT LIST");
        new contractThread(FETCH).execute(" ");
    }
    public void menuTenderStatus(MenuItem item) {appButton(item.getActionView());}


    public void inventoryRecordButton(View view) {}
    public void menuInventoryRecord(MenuItem item) {}


    public void budgetButton(View view)
    {
        if(!isLoggedIn) return;
        START = 0;
        LIST_ITEM_CLICKED = 0;
        budgetList.clear();
        setContentView(R.layout.list_page);
        loadNavigationMenu();
        TextView t = findViewById(R.id.headerText);
        t.setText("BUDGET LIST");
        new budgetThread(FETCH).execute(" ");
    }
    public void menuBudget(MenuItem item) {budgetButton(item.getActionView());}


    public void externalMembersButton(View view) {}
    public void menuExternalMembers(MenuItem item) {}

    public void logoutButton(View view)
    {
        if(isLoggedIn == true)
        {
            isLoggedIn = false;
            currentUser = "";
            currentPassword = "";
            AccessToken = "";

            officeList.clear();
            usersList.clear();
            tenderList.clear();
            projectList.clear();
            budgetList.clear();
            appList.clear();
            contractList.clear();
            lotList.clear();
            packageList.clear();

            setContentView(R.layout.home);
            showToast("YOU ARE LOGGED OUT");
        }
        else showToast("YOU ARE NOT LOGGED IN");
    }
    public void menuLogOut(MenuItem item) { logoutButton(item.getActionView()); }


    //end  private items and buttons**************************************************


    public static String getToken(String un, String pass)
    {
                try{
                    URL url = new URL(tokenAuthUrl);

                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("email", un);
                    postDataParams.put("password",pass);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(JsonToString(postDataParams));

                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK)
                    {

                        BufferedReader in=new BufferedReader(
                                new InputStreamReader(
                                        conn.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";

                        while((line = in.readLine()) != null) {

                            sb.append(line);
                            break;
                        }

                        in.close();
                        JSONObject x = new JSONObject(sb.toString());
                        return x.getString("token");



                    }
                    else {
                        System.out.println("false : "+responseCode);
                        return  null;
                    }


                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                return null;
    }
    public static boolean verifyToken(String accessToken)
    {
                try{

                    System.out.println("verifying token");

                    URL url = new URL(tokenVerifyUrl);

                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("token", accessToken);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(JsonToString(postDataParams));

                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK)
                    {
                        return true;
                    }
                    else {
                        return false;
                    }

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                return false;

            }
    public void getData(String targetUrl,ArrayList<JSONObject> List)
    {
        System.out.println("getting data");

        targetUrl = targetUrl+"?start="+Integer.toString(START)+"&length=10";

        String result;
        String inputLine;
        try {
            String auth = "Bearer "+AccessToken;
            URL myUrl = new URL(targetUrl);
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            System.out.println("opened connection");
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Authorization",auth);
            connection.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();

            result = stringBuilder.toString();
            connection.disconnect();

            System.out.println(result);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            result = null;
        }


        try
        {
            JSONObject jsomObj = new JSONObject(result);
            JSONArray jsonArray = jsomObj.getJSONArray("data");

            for (int i=0;i<jsonArray.length();i++)
            {
                List.add(jsonArray.getJSONObject(i));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void getSearchData(String targetUrl,String value, ArrayList<JSONObject> List)
    {
        System.out.println("getting data");

        targetUrl = targetUrl+"?search[value]="+value;

        System.out.println("***********************************"+targetUrl);

        String result;
        String inputLine;
        try {
            String auth = "Bearer "+AccessToken;
            URL myUrl = new URL(targetUrl);
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            System.out.println("opened connection");
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Authorization",auth);
            connection.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();

            result = stringBuilder.toString();
            connection.disconnect();

            System.out.println(result);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            result = null;
        }


        try
        {
            JSONArray jsonArray = new JSONArray(result);

            for (int i=0;i<jsonArray.length();i++)
            {
                List.add(jsonArray.getJSONObject(i));
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Fresco.initialize(this);


    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.private_menu, menu);
        getMenuInflater().inflate(R.menu.public_menu, menu);
        return true;
    }




    class loginThread extends AsyncTask<String,String,String>
    {

        public EditText x ,y;
        ProgressDialog progress;

        loginThread()
        {
            x = findViewById(R.id.emailBox);
            y = findViewById(R.id.passwordBox);

        }
        @Override
        protected String doInBackground(String ...params)
        {
            currentUser = x.getText().toString();
            currentPassword = y.getText().toString();

            AccessToken = getToken(currentUser,currentPassword);
            isLoggedIn = verifyToken(AccessToken);

            if(isLoggedIn)
            {
                getSearchData(userUrl,currentUser,usersList);
                CURRENT_USER = usersList.get(0);
                usersList.clear();
            }

            return " ";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();

            if(isLoggedIn == true)
            {
                setContentView(R.layout.home_private);
                loadNavigationMenu();
                showToast("Login Successful");
            }
            else
            {
                showToast("Login Failed");
                x.setText("");
                y.setText("");
            }
        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected void onProgressUpdate(String... text)
        {

        }
    }

    class officeProfileThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        officeProfileThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);

        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE) getData(officeUrl,officeList);
            else if(MODE == FETCH) getData(officeUrl,officeList);
            else if(MODE == SEARCH) getSearchData(officeUrl,params[0],officeList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            OfficeProfileAdapter adapter = new OfficeProfileAdapter(officeList,getApplicationContext(),MainActivity.this);
            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag ==false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new officeProfileThread(FETCH).execute(" ");

                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class userProfileThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        userProfileThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);

        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE) getData(userUrl,usersList);
            else if(MODE == FETCH)  getData(userUrl,usersList);
            else if(MODE == SEARCH) getSearchData(userUrl,params[0],usersList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            UserProfileAdapter adapter
                    = new UserProfileAdapter(usersList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new userProfileThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class tenderThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        tenderThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);
        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE) getData(tenderUrl,tenderList);
            else if(MODE == FETCH)  getData(tenderUrl,tenderList);
            else if(MODE == SEARCH) getSearchData(tenderUrl,params[0],tenderList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            TenderAdapter adapter
                    = new TenderAdapter(tenderList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new tenderThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class projectThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        projectThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);
        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE) getData(projectUrl,projectList);
            else if(MODE == FETCH)  getData(projectUrl,projectList);
            else if(MODE == SEARCH) getSearchData(projectUrl,params[0],projectList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            ProjectAdapter adapter
                    = new ProjectAdapter(projectList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new projectThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class budgetThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        budgetThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);
        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE) getData(budgetUrl,budgetList);
            else if(MODE == FETCH)  getData(budgetUrl,budgetList);
            else if(MODE == SEARCH) getSearchData(budgetUrl,params[0],budgetList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            BudgetAdapter adapter
                    = new BudgetAdapter(budgetList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new budgetThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class appThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        appThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);
        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE || MODE == FETCH)
                getData(appUrl,appList);
            else if(MODE == SEARCH) getSearchData(appUrl,params[0],appList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            AppAdapter adapter
                    = new AppAdapter(appList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new appThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class packageThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        ArrayList<JSONObject> mList;
        String url;
        packageThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);
            mList = packageList;
            url = packageUrl;
        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE || MODE == FETCH)
                getData(url,mList);
            else if(MODE == SEARCH) getSearchData(url,params[0],mList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            PackageAdapter adapter
                    = new PackageAdapter(mList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new packageThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class lotThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        ArrayList<JSONObject> mList;
        String url;
        lotThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);
            mList = lotList;
            url = lotUrl;
        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE || MODE == FETCH)
                getData(url,mList);
            else if(MODE == SEARCH) getSearchData(url,params[0],mList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            LotAdapter adapter
                    = new LotAdapter(mList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new lotThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }

    class contractThread extends AsyncTask<String,String,String>
    {
        int MODE;
        ProgressDialog progress;
        ArrayList<JSONObject> mList;
        String url;
        contractThread(int m)
        {
            MODE = m;
            listView = findViewById(R.id.list);
            mList = contractList;
            url = contractUrl;
        }

        @Override
        protected String doInBackground(String ...params)
        {
            if(MODE == LOAD_MORE || MODE == FETCH)
                getData(url,mList);
            else if(MODE == SEARCH) getSearchData(url,params[0],mList);
            else if(MODE == BACK_FROM_PROFILE) {}
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progress.dismiss();
            ContractAdapter adapter
                    = new ContractAdapter(mList,getApplicationContext(),
                    MainActivity.this);

            listView.setAdapter(adapter);
            listView.setSelection(LIST_ITEM_CLICKED);

            listView.setOnScrollListener(new AbsListView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState)
                {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                            listView.getFooterViewsCount()) >= (listView.getAdapter().getCount() - 1))
                    {
                        if(searchFlag == false)
                        {
                            START +=10;
                            LIST_ITEM_CLICKED = START;
                            new contractThread(FETCH).execute(" ");
                        }

                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }

        @Override
        protected void onPreExecute()
        {
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }
    }




    public static String JsonToString(JSONObject params) throws Exception
    {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public void showToast(String s)
    {
        Toast.makeText(MainActivity.this, s,
                Toast.LENGTH_SHORT).show();
    }

    public void loadNavigationMenu()
    {
        NavigationView nv= findViewById(R.id.nav_view);
        nv.getMenu().clear();
        nv.inflateMenu(R.menu.private_menu);

        View head = nv.getHeaderView(0);

        TextView a,b,c,d;
        a=head.findViewById(R.id.navName);
        b=head.findViewById(R.id.navPost);
        c=head.findViewById(R.id.navEmail);
        d=head.findViewById(R.id.navPhone);
        ImageView i = head.findViewById(R.id.navImage);
        i.setImageResource(R.drawable.default_profile_pic);


        try
        {
            a.setText(CURRENT_USER.getString("first_name")+" "+CURRENT_USER.getString("last_name"));
            b.setText(CURRENT_USER.getString("designation_name"));
            c.setText(CURRENT_USER.getString("email"));
            d.setText(CURRENT_USER.getString("personal_mobile_no"));
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public void hideKeyboard(View v)
    {
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void loadNavigationHeader(JSONObject user)
    {
        TextView a,b,c,d;
        a=findViewById(R.id.navName);
        b=findViewById(R.id.navPost);
        c=findViewById(R.id.navEmail);
        d=findViewById(R.id.navPhone);

        try
        {
            a.setText(user.getString("first_name")+" "+user.getString("last_name"));
            b.setText(user.getString("designation_name"));
            c.setText(user.getString("email"));
            d.setText(user.getString("personal_mobile_no"));
        }
        catch (Exception e) {e.printStackTrace();}

    }

}


