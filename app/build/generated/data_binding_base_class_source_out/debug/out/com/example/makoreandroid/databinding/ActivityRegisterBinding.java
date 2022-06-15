// Generated by view binder compiler. Do not edit!
package com.example.makoreandroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.makoreandroid.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView addImg;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final Button registerBtnRegister;

  @NonNull
  public final TextView registerError;

  @NonNull
  public final TextView registerLogin;

  @NonNull
  public final EditText registerNickName;

  @NonNull
  public final EditText registerPassword;

  @NonNull
  public final EditText registerUserName;

  @NonNull
  public final EditText registerVPassword;

  @NonNull
  public final TableRow tableRow;

  @NonNull
  public final TextView tvTitle;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView addImg,
      @NonNull ImageView imageView4, @NonNull LinearLayout linearLayout,
      @NonNull Button registerBtnRegister, @NonNull TextView registerError,
      @NonNull TextView registerLogin, @NonNull EditText registerNickName,
      @NonNull EditText registerPassword, @NonNull EditText registerUserName,
      @NonNull EditText registerVPassword, @NonNull TableRow tableRow, @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.addImg = addImg;
    this.imageView4 = imageView4;
    this.linearLayout = linearLayout;
    this.registerBtnRegister = registerBtnRegister;
    this.registerError = registerError;
    this.registerLogin = registerLogin;
    this.registerNickName = registerNickName;
    this.registerPassword = registerPassword;
    this.registerUserName = registerUserName;
    this.registerVPassword = registerVPassword;
    this.tableRow = tableRow;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_img;
      ImageView addImg = ViewBindings.findChildViewById(rootView, id);
      if (addImg == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.register_btnRegister;
      Button registerBtnRegister = ViewBindings.findChildViewById(rootView, id);
      if (registerBtnRegister == null) {
        break missingId;
      }

      id = R.id.register_error;
      TextView registerError = ViewBindings.findChildViewById(rootView, id);
      if (registerError == null) {
        break missingId;
      }

      id = R.id.registerLogin;
      TextView registerLogin = ViewBindings.findChildViewById(rootView, id);
      if (registerLogin == null) {
        break missingId;
      }

      id = R.id.register_nickName;
      EditText registerNickName = ViewBindings.findChildViewById(rootView, id);
      if (registerNickName == null) {
        break missingId;
      }

      id = R.id.register_password;
      EditText registerPassword = ViewBindings.findChildViewById(rootView, id);
      if (registerPassword == null) {
        break missingId;
      }

      id = R.id.register_userName;
      EditText registerUserName = ViewBindings.findChildViewById(rootView, id);
      if (registerUserName == null) {
        break missingId;
      }

      id = R.id.register_vPassword;
      EditText registerVPassword = ViewBindings.findChildViewById(rootView, id);
      if (registerVPassword == null) {
        break missingId;
      }

      id = R.id.tableRow;
      TableRow tableRow = ViewBindings.findChildViewById(rootView, id);
      if (tableRow == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, addImg, imageView4,
          linearLayout, registerBtnRegister, registerError, registerLogin, registerNickName,
          registerPassword, registerUserName, registerVPassword, tableRow, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
