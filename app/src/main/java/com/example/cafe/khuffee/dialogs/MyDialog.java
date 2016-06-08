package com.example.cafe.khuffee.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafe.khuffee.R;
import com.example.cafe.khuffee.database.Dao;
import com.example.cafe.khuffee.network.NetworkHandler;

import java.util.regex.Pattern;

import classes.User;


/**
 * Created by lee on 2016-05-29.
 */
public class MyDialog extends Dialog implements View.OnClickListener {
    private String title;
    private EditText editId;
    private EditText editPass;
    private Button btnSignIn;
    private TextView tvTitle;
    private TextView tvSignUp;
    private TextView tvSearch;

    private User user;
    private MyDialogListener dialogListener;

    private NetworkHandler networkHandler;

    public MyDialog(Context context, String title) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = title;
        networkHandler = new NetworkHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Rect displayRec = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRec);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        layoutParams.width = (int) (displayRec.width() * 0.9f);
        layoutParams.height = (int) (displayRec.height() * 0.6f);
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.login_dialog);
        editId = (EditText) findViewById(R.id.edit_id);
        editPass = (EditText) findViewById(R.id.edit_pass);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        tvSearch = (TextView) findViewById(R.id.tv_search);

        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        tvTitle.setText(title);
        editId.setFilters(new InputFilter[]{new MyInputFilter(getContext())});
    }

    public MyDialogListener getDialogListener() {
        return dialogListener;
    }

    public void setDialogListener(MyDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_in) {
            Log.e("click", "sign in");
            if (signIn()) {
                Toast.makeText(this.getContext(), "sign in", Toast.LENGTH_SHORT).show();
                dialogListener.signinDialog(user);
                this.dismiss();
            } else {
                Toast.makeText(this.getContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.tv_search) {
            this.dismiss();
            dialogListener.cancelDialog();
        } else if (v.getId() == R.id.tv_sign_up) {
            this.dismiss();
            dialogListener.signupDialog();
        }
        return;
    }

    private boolean signIn() {
        String id = editId.getText().toString();
        String pass = editPass.getText().toString();

        if (id.length() <= 0 || pass.length() <= 0) {
            return false;
        } else if (id.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return false;
        }
        //dao.insertUser(new User("aa", "1234", new ArrayList<Coupon>()));

        networkHandler.signin(id, pass);

        while (networkHandler.isWorking()) {}

        if(networkHandler.getSignIn()) {
            user = networkHandler.getUser();
            return true;
        }
        else {
            Toast.makeText(this.getContext(), networkHandler.getMessge(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private class MyInputFilter implements InputFilter {
        private Context context;

        MyInputFilter(Context context) {
            this.context = context;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");

            if (source.equals("") || pattern.matcher(source).matches()) {
                return source;
            }
            Toast.makeText(this.context, "Alphabet or Number", Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
