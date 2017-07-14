package autobike.stanley.idv.android_autobike_v7;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.login.LoginGetMemberVOByAccTask;
import autobike.stanley.idv.android_autobike_v7.login.LoginGetMemberVOByMemnoTask;
import autobike.stanley.idv.android_autobike_v7.login.Member;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.Navi_IDCheck;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.Navi_Member_Data;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.Navi_Rent_List;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.Navi_Sell_List;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.Navi_Setting;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.Tab_BoardMessage_Fragment;
import autobike.stanley.idv.android_autobike_v7.tab.location.Tab_Location_Fragment;
import autobike.stanley.idv.android_autobike_v7.tab.news.Tab_News_Fragment;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Tab_RentBike_Fragment;
import autobike.stanley.idv.android_autobike_v7.tab.sellBike.Tab_SellBike_Fragment;

public class MainActivity extends FragmentActivity {

    private static final String TAB_1_TAG = "tab_1";
    private static final String TAB_2_TAG = "tab_2";
    private static final String TAB_3_TAG = "tab_3";
    private static final String TAB_4_TAG = "tab_4";
    private static final String TAB_5_TAG = "tab_5";
    private static final int REQ_PERMISSIONS = 0;
    private DrawerLayout drawerLayout;
    private ImageView ivMenu;
    private Profile profile;
    private Member member;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivMenu = (ImageView)findViewById(R.id.iv_menu);
        askPermissions();
        //add listrner for navigationview
        ivMenu.setOnClickListener(new View.OnClickListener() {  //set onclick listrner to show navigationview
            @Override
            public void onClick(View v) {
                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
                if(!drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.openDrawer(navigationView);
                }else{
                    drawerLayout.closeDrawer(navigationView);
                }

            }
        });

        initDrawer();
        makeTabs();
    }

    private void makeTabs() {
        //獲取TabHost控制元件
        final FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //設定Tab頁面的顯示區域，帶入Context、FragmentManager、Container ID
        mTabHost.setup(this, getSupportFragmentManager(), R.id.container);
        /**
         新增Tab結構說明 :
         首先帶入Tab分頁標籤的Tag資訊並可設定Tab標籤上顯示的文字與圖片，
         再來帶入Tab頁面要顯示連結的Fragment Class，最後可帶入Bundle資訊。
         **/
        //小黑人建立一個Tab，這個Tab的Tag設定為one，
        //並設定Tab上顯示的文字為第一堂課與icon圖片，Tab連結切換至
        //LessonOneFragment class，無夾帶Bundle資訊。
        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG)
                        .setIndicator("",getResources().getDrawable(R.drawable.bike))
                ,Tab_RentBike_Fragment.class,null);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG)
                        .setIndicator("",getResources().getDrawable(R.drawable.shoppingcart))
                ,Tab_SellBike_Fragment.class,null);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec(TAB_3_TAG)
                        .setIndicator("",getResources().getDrawable(R.drawable.newspaper))
                ,Tab_News_Fragment.class, null);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec(TAB_4_TAG)
                        .setIndicator("",getResources().getDrawable(R.drawable.write))
                ,Tab_BoardMessage_Fragment.class,null);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec(TAB_5_TAG)
                        .setIndicator("",getResources().getDrawable(R.drawable.location))
                ,Tab_Location_Fragment.class,null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(mTabHost.getCurrentTabTag().equals(TAB_5_TAG)){
                    Toast.makeText(MainActivity.this,"logTab",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void initDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerview = navigationView.getHeaderView(0);      //get navigationview's header
        profile =  new Profile(this);
        String tempMemno = profile.getData("Memno");
        try {
            //get membervo
            member = new LoginGetMemberVOByMemnoTask().execute(Common.URL_MemServlet,tempMemno).get();
            //findview by id
            TextView userID = (TextView) headerview.findViewById(R.id.tvUserName);
            TextView userMail = (TextView) headerview.findViewById(R.id.tvUserEmail);
            //set id&mail
            userID.setText(member.getMemname());
            userMail.setText(member.getMail());
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    drawerLayout.closeDrawers();
                    Fragment fragment;
                    switch (menuItem.getItemId()) {
                        case R.id.memberData:
                            fragment = new Navi_Member_Data();
                            switchFragment(fragment);
                            break;
                        case R.id.rentList:
                            fragment = new Navi_Rent_List();
                            switchFragment(fragment);
                            break;
                        case R.id.secondList:
                            fragment = new Navi_Sell_List();
                            switchFragment(fragment);
                            break;
                        case R.id.idCheckStatus:
                            fragment = new Navi_IDCheck();
                            switchFragment(fragment);
                            break;
                        case R.id.settingPage:
                            fragment = new Navi_Setting();
                            switchFragment(fragment);
                            break;
                        default:
//                        initBody();
                            break;
                    }
                    return true;
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void askPermissions(){
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    REQ_PERMISSIONS);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (keyCode == KeyEvent.KEYCODE_BACK && count == 0) {
            new AlertDialogFragment().show(getSupportFragmentManager(), "exit");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static class AlertDialogFragment
            extends DialogFragment implements DialogInterface.OnClickListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.text_Exit)
                    .setIcon(R.drawable.ic_alert)
                    .setMessage(R.string.msg_WantToExit)
                    .setPositiveButton(R.string.text_Yes, this)
                    .setNegativeButton(R.string.text_No, this)
                    .create();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
//                  getActivity().finish();
//                    int pid = android.os.Process.myPid();
//                    android.os.Process.killProcess(pid);   //杀死当前进程
                    System.exit(0);
                    break;
                default:
                    dialog.cancel();
                    break;
            }
        }
    }
}
