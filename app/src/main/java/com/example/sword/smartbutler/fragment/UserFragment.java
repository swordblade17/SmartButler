package com.example.sword.smartbutler.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.MyUser;
import com.example.sword.smartbutler.ui.CourierActivity;
import com.example.sword.smartbutler.ui.LoginActivity;
import com.example.sword.smartbutler.ui.PhoneActivity;
import com.example.sword.smartbutler.utils.L;
import com.example.sword.smartbutler.utils.ShareUtil;
import com.example.sword.smartbutler.utils.UtilTools;
import com.example.sword.smartbutler.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sword on 2017/2/26.
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    private Button mBtnExitUser;
    private TextView mEditUser;
    private EditText mETUserName;
    private EditText mETDesc;
    private EditText mETAge;
    private EditText mETSex;
    private Button mBtnUpdate;
    private TextView mCourierTV;
    private TextView mPhoneTV;

    //头像
    private CircleImageView mUserPhoto;

    private CustomDialog mDialog;

    private Button mBtnPhoto;
    private Button mBtnGallery;
    private Button mBtnCancel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);

        return view;
    }

    private void findView(View view) {
        mBtnExitUser = (Button) view.findViewById(R.id.btn_exit_user);
        mBtnExitUser.setOnClickListener(this);
        mEditUser = (TextView) view.findViewById(R.id.edit_user);
        mEditUser.setOnClickListener(this);
        mETUserName = (EditText) view.findViewById(R.id.et_username);
        mETSex = (EditText) view.findViewById(R.id.et_sex);
        mETAge = (EditText) view.findViewById(R.id.et_age);
        mETDesc = (EditText) view.findViewById(R.id.et_desc);
        mCourierTV = (TextView) view.findViewById(R.id.tv_courier);
        mCourierTV.setOnClickListener(this);
        mPhoneTV = (TextView) view .findViewById(R.id.tv_phone);
        mPhoneTV.setOnClickListener(this);
        //默认不可点击
        setEnabled(false);

        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        mETUserName.setText(userInfo.getUsername());
        mETAge.setText(String.format("%d", userInfo.getAge()));
        mETDesc.setText(userInfo.getDesc());
        mETSex.setText(userInfo.isSex() ? "男" : "女");


        mBtnUpdate = (Button) view.findViewById(R.id.btn_update_ok);
        mBtnUpdate.setOnClickListener(this);

        mUserPhoto = (CircleImageView) view.findViewById(R.id.profile_image);
        mUserPhoto.setOnClickListener(this);

        UtilTools.getImageFromShare(getActivity(), mUserPhoto);

        mDialog = new CustomDialog(getActivity(), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_photo, R.style.theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);
        //屏幕外点击有效
        mDialog.setCancelable(true);
        //dialog上的按键
        mBtnPhoto = (Button) mDialog.findViewById(R.id.btn_camera);
        mBtnGallery = (Button) mDialog.findViewById(R.id.btn_picture);
        mBtnCancel = (Button) mDialog.findViewById(R.id.btn_cancel);
        mBtnPhoto.setOnClickListener(this);
        mBtnGallery.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    private void setEnabled(boolean is) {
        mETUserName.setEnabled(is);
        mETSex.setEnabled(is);
        mETAge.setEnabled(is);
        mETDesc.setEnabled(is);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit_user:
                //退出登录
                //清除缓存用户对象
                MyUser.logOut();
                //现在的currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑资料
            case R.id.edit_user:
                mBtnUpdate.setVisibility(View.VISIBLE);
                setEnabled(true);
                break;
            case R.id.btn_update_ok:
                String userName = mETUserName.getText().toString();
                String sex = mETSex.getText().toString();
                String age = mETAge.getText().toString();
                String decs = mETDesc.getText().toString();

                if (!TextUtils.isEmpty(userName) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
                    //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(userName);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    if (!TextUtils.isEmpty(decs)) {
                        user.setDesc(decs);
                    } else {
                        user.setDesc("这个人很懒，什么都没有留下");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //修改成功
                                setEnabled(false);
                                mBtnUpdate.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                mDialog.show();
                break;
            case R.id.btn_cancel:
                mDialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.tv_courier:
                //TODO 跳转至快递查询
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转到相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        mDialog.dismiss();
    }


    //跳转到相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT
                , Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                        , PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        mDialog.dismiss();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }

    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);

    }

    private void setImageView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            mUserPhoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToShare(getActivity(), mUserPhoto);
    }
}
