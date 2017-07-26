package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;

import static android.app.Activity.RESULT_OK;
import static autobike.stanley.idv.android_autobike_v7.R.id.imageView;
import static autobike.stanley.idv.android_autobike_v7.R.id.ivId1;

public class Navi_IDCheck extends Fragment {

    private ImageView ivid1,ivid2,ivid3;
    private Button btnTakeImage1,btnTakeImage2,btnTakeImage3,btnSendIDCheck;
    private View view;
    private File file;//,file1,file2,file3;
    private int flag;
    private Profile profile;
    private static final int REQUEST_TAKE_PICTURE_LARGE = 1;
    private static final int REQUEST_PICK_PICTURE = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.navi_idcheck, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        findViews();
        profile = new Profile(getActivity());
        if(profile.getData("Memstatus").equals("confirmed")){
            Toast.makeText(getActivity(),"認證已完成",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        /////check status
//        String memStatus = profile.getData("Memstatus");
//        if(memStatus.equals("confirmed")){
//            ivid1.setVisibility(View.GONE);
//            ivid2.setVisibility(View.GONE);
//            ivid3.setVisibility(View.GONE);
//        }
        /////check status
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        Navi_IDCheck_CameraTool.askPermissions(getActivity(), permissions, Navi_IDCheck_CameraTool.PERMISSION_READ_EXTERNAL_STORAGE);


        ///////////////////upload image from storage/////////////////////////////////////
        btnTakeImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_PICTURE);
            }
        });
        btnTakeImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_PICTURE);
            }
        });
        btnTakeImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 3;
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_PICTURE);
            }
        });
        ///////////////////upload image from storage/////////////////////////////////////

        ///////////////////upload image from Camera/////////////////////////////////////
        ivid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                onTakePictureLargeClick(view);
            }
        });
        ivid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                onTakePictureLargeClick(view);
            }
        });
        ivid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 3;
                onTakePictureLargeClick(view);
            }
        });
        ///////////////////upload image from Camera/////////////////////////////////////

        btnSendIDCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change imageview to byte[]

                String memno = profile.getData("Memno");
                String image1 = Base64.encodeToString(changeImageViewToByte(ivid1), Base64.DEFAULT);
                String image2 = Base64.encodeToString(changeImageViewToByte(ivid2), Base64.DEFAULT);
                String image3 = Base64.encodeToString(changeImageViewToByte(ivid3), Base64.DEFAULT);
                new UpdateIDCheckImageTask().execute(Common.URL_MemServlet,memno,image1,image2,image3);
                Toast.makeText(getActivity(),"Update OK!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        ivid1 = (ImageView) view.findViewById(R.id.ivId1);
        ivid2 = (ImageView) view.findViewById(R.id.ivId2);
        ivid3 = (ImageView) view.findViewById(R.id.ivId3);
        btnTakeImage1 = (Button) view.findViewById(R.id.btnTakeImage1);
        btnTakeImage2 = (Button) view.findViewById(R.id.btnTakeImage2);
        btnTakeImage3 = (Button) view.findViewById(R.id.btnTakeImage3);
        btnSendIDCheck = (Button)view.findViewById(R.id.btnSendIDCheck);
    }

    //check availible camera device
    public boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            int newSize = 512;
            switch (requestCode) {
                case REQUEST_TAKE_PICTURE_LARGE:
                    Bitmap srcPicture = BitmapFactory.decodeFile(file.getPath());
                    Bitmap downsizedPicture = Navi_IDCheck_CameraTool.downSize(srcPicture, newSize);
                    if(flag == 1){
                        ivid1.setImageBitmap(downsizedPicture);
                    }else if(flag == 2){
                        ivid2.setImageBitmap(downsizedPicture);
                    }else if(flag == 3){
                        ivid3.setImageBitmap(downsizedPicture);
                    }

                    break;

                case REQUEST_PICK_PICTURE:
                    Uri uri = intent.getData();
                    String[] columns = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(uri, columns,
                            null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        String imagePath = cursor.getString(0);
                        cursor.close();
                        Bitmap srcImage = BitmapFactory.decodeFile(imagePath);
                        Bitmap downsizedImage = Navi_IDCheck_CameraTool.downSize(srcImage, newSize);
                        if(flag == 1){
                            ivid1.setImageBitmap(downsizedImage);
                        }else if(flag == 2){
                            ivid2.setImageBitmap(downsizedImage);
                        }else if(flag == 3){
                            ivid3.setImageBitmap(downsizedImage);
                        }
                    }
                    break;
            }
        }
    }

    public void onTakePictureLargeClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(flag == 1){
            file = new File(file, "picture1.jpg");
        }else if(flag == 2){
            file = new File(file, "picture2.jpg");
        }else if(flag == 3){
            file = new File(file, "picture3.jpg");
        }
        Uri contentUri = FileProvider.getUriForFile(
                getActivity(), getActivity().getPackageName() + ".provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        if (isIntentAvailable(getActivity(), intent)) {
            startActivityForResult(intent, REQUEST_TAKE_PICTURE_LARGE);
        } else {
            Toast.makeText(getActivity(), R.string.msg_NoCameraAppsFound,
                    Toast.LENGTH_SHORT).show();
        }
    }
    // change imageview to byte[]
    private byte[] changeImageViewToByte(ImageView ivimage){
        Bitmap bitmap =  ((BitmapDrawable) ivimage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
