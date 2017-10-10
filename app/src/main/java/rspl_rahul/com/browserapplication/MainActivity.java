package rspl_rahul.com.browserapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String url1 = "https://www.google.co.in/";
    String url2="https://www.facebook.com/";
    String url3="https://developer.android.com/";
    String url4="https://www.youtube.com/";
    Button Btn1,Btn2,Btn3,Btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Btn1=(Button)findViewById(R.id.button);
        Btn2=(Button)findViewById(R.id.button2);
        Btn3=(Button)findViewById(R.id.button3);
        Btn4=(Button)findViewById(R.id.button4);

        Btn1.setOnClickListener(new View.OnClickListener() {
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
        });

        Btn2.setOnClickListener(new View.OnClickListener() {
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
        });
    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            Btn1.setVisibility(View.VISIBLE);
            Btn2.setVisibility(View.VISIBLE);
            Btn3.setVisibility(View.VISIBLE);
            Btn4.setVisibility(View.VISIBLE);
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
       Btn1.setVisibility(View.GONE);
       Btn2.setVisibility(View.GONE);
       Btn3.setVisibility(View.GONE);
       Btn4.setVisibility(View.GONE);
    }
}
