package friday.nicknamer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static android.util.Log.i;

public class Nicknamer extends AppCompatActivity implements ListOfItems.OnFragmentInteractionListener {

    FloatingActionButton fab;
    ListOfItems fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicknamer);
        setupFragments();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentFabOnClick();
            }
        });
    }

    private void fragmentFabOnClick() {
        fragment.onFabClick();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        fragment.backButtonPressed();
    }

    private void setupFragments() {
        fragment = ListOfItems.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_page, fragment).show(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(NicknamerAction action) {

    }

    @Override
    public void setPageTitle(String title) {
        ((TextView) findViewById(R.id.header_page)).setText(title);
    }

    @Override
    public void exitApp() {
        Log.i("FridayTag","I tried");
        ActivityManager manager;
        if ((manager = ((ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE))) != null) {
            manager.clearApplicationUserData();
        }
        try {
            killProcessesAround((Activity) getApplicationContext());
        } catch (PackageManager.NameNotFoundException e) {
            if (BuildConfig.DEBUG) i("TAG", "NameNotFoundExcotFoundException : " + e.getMessage());
        }
    }

    private static void killProcessesAround(Activity activity) throws PackageManager.NameNotFoundException {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        String myProcessPrefix = activity.getApplicationInfo().processName;
        String myProcessName = activity.getPackageManager().getActivityInfo(activity.getComponentName(), 0).processName;
        if (am != null) {
            for (ActivityManager.RunningAppProcessInfo proc : am.getRunningAppProcesses()) {
                if (proc.processName.startsWith(myProcessPrefix) && !proc.processName.equals(myProcessName)) {
                    android.os.Process.killProcess(proc.pid);
                }
            }
        }
    }
}
