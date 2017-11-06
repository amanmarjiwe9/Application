package rspl_rahul.com.browserapplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // String url1 = "https://www.google.co.in/";
    String url2="https://www.facebook.com/";
    String url3="https://developer.android.com/";
    String url4="https://www.youtube.com/";
    Button Btn1,Btn2,Btn3,Btn4;
    Button[] myButton;
    DbHelper dbHelper;
    ArrayList<Info> getAll;
    String mac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        mac = wInfo.getMacAddress();
        myButton = new Button[5];
        dbHelper = new DbHelper(this);
        getAll = dbHelper.getAll();
        for (final Info in : getAll) {
            myButton[Integer.parseInt(String.valueOf(in.getId()))] = new Button(getApplicationContext());
            myButton[Integer.parseInt(String.valueOf(in.getId()))].setText(in.getName());
            myButton[Integer.parseInt(String.valueOf(in.getId()))].setBackground(getResources().getDrawable(R.drawable.shapebutton));
            myButton[Integer.parseInt(String.valueOf(in.getId()))].setTextColor(getResources().getColor(R.color.backgroudButton));

            LinearLayout ll = (LinearLayout) findViewById(R.id.buttonlayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            ll.addView(myButton[Integer.parseInt(String.valueOf(in.getId()))], lp);
            myButton[Integer.parseInt(String.valueOf(in.getId()))].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getVisibility();
                    //  myButton[Integer.parseInt(String.valueOf(id))].setVisibility(View.GONE);
                    Fragment fragment = new WebFragment();
                    /*Log.d("Inside On Click", String.valueOf(in.getId()));*/
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    Log.d("^^^^^^^^^", in.getWebsite());
                    bundle.putString("url", in.getWebsite());
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().add(R.id.web_browser, fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();

                }
            });


        }

        /*Btn1=(Button)findViewById(R.id.button);
        Btn2=(Button)findViewById(R.id.button2);
        Btn3=(Button)findViewById(R.id.button3);
        Btn4=(Button)findViewById(R.id.button4);*/
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("USV_TAB_WEBSITE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // String mac = dataSnapshot.child("Mac").getValue(String.class).toString();
                //  getVisibility();
                final String url = dataSnapshot.child("Website").getValue(String.class);

                final String id = dataSnapshot.child("Id").getValue(String.class);
                final String method = dataSnapshot.child("Method").getValue(String.class);
                final String name = dataSnapshot.child("Name").getValue(String.class);
                final String macAddress = dataSnapshot.child("Mac").getValue(String.class);
                List<String> items = Arrays.asList(macAddress.split("\\s*,\\s*"));
                ArrayList<Info> info = dbHelper.getId(id);


                for (String aman : items) {
                    Log.d("}}}}}}}", aman);
                    if (aman.equals(mac) && info.size() == 0 && method.equals("Create")) {
                        dbHelper.insertNew(id, url, name);
                        myButton[Integer.parseInt(String.valueOf(id))] = new Button(getApplicationContext());
                        myButton[Integer.parseInt(String.valueOf(id))].setText(name);
                        myButton[Integer.parseInt(String.valueOf(id))].setBackground(getResources().getDrawable(R.drawable.shapebutton));
                        myButton[Integer.parseInt(String.valueOf(id))].setTextColor(getResources().getColor(R.color.backgroudButton));

                        LinearLayout ll = (LinearLayout) findViewById(R.id.buttonlayout);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(5, 5, 5, 5);
                        ll.addView(myButton[Integer.parseInt(String.valueOf(id))], lp);
                        myRef.child("USV_TAB_WEBSITE").child("Status").setValue("Tab Created");
                        myButton[Integer.parseInt(String.valueOf(id))].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getVisibility();
                                //  myButton[Integer.parseInt(String.valueOf(id))].setVisibility(View.GONE);
                                Fragment fragment = new WebFragment();
                                Log.d("Inside On Click", String.valueOf(id));
                                FragmentManager fragmentManager = getSupportFragmentManager();

                                Bundle bundle = new Bundle();
                                Log.d("^^^^^^^^^", url);
                                bundle.putString("url", url);
                                fragment.setArguments(bundle);
                                fragmentManager.beginTransaction().add(R.id.web_browser, fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();

                            }
                        });

                    } else if (aman.equals(mac) && method.equals("Rename")) {
                        dbHelper.renameTab(id, name, url);

                        myButton[Integer.parseInt(String.valueOf(id))].setText(name);
                        myButton[Integer.parseInt(String.valueOf(id))].setOnClickListener(null);
                        myRef.child("USV_TAB_WEBSITE").child("Status").setValue("Tab Renamed");
                        myButton[Integer.parseInt(String.valueOf(id))].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getVisibility();
                                //  myButton[Integer.parseInt(String.valueOf(id))].setVisibility(View.GONE);
                                Fragment fragment = new WebFragment();
                                Log.d("Inside On Click", String.valueOf(id));
                                FragmentManager fragmentManager = getSupportFragmentManager();

                                Bundle bundle = new Bundle();
                                Log.d("^^^^^^^^^", url);
                                bundle.putString("url", url);
                                fragment.setArguments(bundle);
                                fragmentManager.beginTransaction().add(R.id.web_browser, fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();

                            }
                        });
                    }


                }

//                Log.d( "Value is: ",  url);
                int i = 5;

                //   ArrayList<Info> info =  dbHelper.getId(id);
                if (method.equals("Fetch")) {


                    if (info.size() == 0) {

                        myRef.child("USV_TAB_WEBSITE").child("Website").setValue("Null");
                        myRef.child("USV_TAB_WEBSITE").child("Name").setValue("Null");

                    } else {
                        String nameDb = info.get(0).getName();
                        String websiteDb = info.get(0).getWebsite();

                        myRef.child("USV_TAB_WEBSITE").child("Name").setValue(nameDb);
                        myRef.child("USV_TAB_WEBSITE").child("Website").setValue(websiteDb);

                    }

                }


             /* else if(info.size()==0 && method.equals("Create")) {
                    dbHelper.insertNew(id,url,name);
                    myButton[Integer.parseInt(String.valueOf(id))] = new Button(getApplicationContext());
                    myButton[Integer.parseInt(String.valueOf(id))].setText(name);
                    myButton[Integer.parseInt(String.valueOf(id))].setBackground(getResources().getDrawable(R.drawable.shapebutton));
                    myButton[Integer.parseInt(String.valueOf(id))].setTextColor(getResources().getColor(R.color.backgroudButton));

                    LinearLayout ll = (LinearLayout) findViewById(R.id.buttonlayout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,5,5,5);
                    ll.addView(myButton[Integer.parseInt(String.valueOf(id))], lp);
                    myRef.child("Tab_Website").child("Status").setValue("Tab Created");
                    myButton[Integer.parseInt(String.valueOf(id))].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getVisibility();
                          //  myButton[Integer.parseInt(String.valueOf(id))].setVisibility(View.GONE);
                            Fragment fragment= new WebFragment();
                            Log.d("Inside On Click", String.valueOf(id));
                            FragmentManager fragmentManager=getSupportFragmentManager();

                            Bundle bundle = new Bundle();
                            Log.d("^^^^^^^^^",url);
                            bundle.putString("url", url);
                            fragment.setArguments(bundle);
                            fragmentManager.beginTransaction().add(R.id.web_browser,fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();

                        }
                    });
                }*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });

    /*    Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVisibility();
                Fragment fragment= new WebFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("url", url1);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.web_browser,fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();
            }
        });*/

       /* Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVisibility();
                Fragment fragment= new WebFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("url", url2);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.web_browser,fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();
            }
        });
        Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVisibility();
                Fragment fragment= new WebFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("url", url3);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.web_browser,fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();
            }
        });
        Btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVisibility();
                Fragment fragment= new WebFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("url", url4);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.web_browser,fragment).addToBackStack(MainActivity.class.getSimpleName()).commit();
            }
        });*/
    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();


            for (int i = 1; i < myButton.length; i++) {
                if (myButton[i] != null) {
                    myButton[i].setVisibility(View.VISIBLE);
                }
            }

            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browse_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Add_browser:

                return true;
            case R.id.help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
   public void getVisibility(){

       for (int i = 1; i < myButton.length; i++) {
           if (myButton[i] != null) {
               Log.d("*******", String.valueOf(i));
               myButton[i].setVisibility(View.GONE);
           }
       }
    }
}
