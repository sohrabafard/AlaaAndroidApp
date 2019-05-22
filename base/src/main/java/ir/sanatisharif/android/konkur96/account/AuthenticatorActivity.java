package ir.sanatisharif.android.konkur96.account;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.adapter.FilterAdapterBySpinner;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.user.Data;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.model.user.UserInfo;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import ir.sanatisharif.android.konkur96.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.accounts.AccountManager.KEY_ERROR_MESSAGE;
import static ir.sanatisharif.android.konkur96.activity.ActivityBase.toastShow;
import static ir.sanatisharif.android.konkur96.app.AppConfig.showNoInternetDialog;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ARG_AUTH_TYPE;

/**
 * Created by Mohamad on 2/9/2019.
 */

public class AuthenticatorActivity extends AccountAuthenticatorActivity implements
        View.OnClickListener,
        AdapterView.OnItemSelectedListener, ICheckNetwork {

    private final String TAG = this.getClass().getSimpleName();
    FirebaseAnalytics mFirebaseAnalytics;
    private boolean login = true;//flag for check status login or register
    private AccountManager mAccountManager;
    private Utils.ValidNationalCode nationalCode = new Utils.ValidNationalCode();
    //ui
    private AlertDialog dialog;
    private View loginView, registerView;
    private LinearLayout linBody;
    private Button btnLogin, btnRegister;
    private MaterialEditText edtPhone, edtNathonalCode, edtPhoneReg, edtNationalCodeReg, edtLastName, edtFirstName, edtEmail;
    private TextView txtDoNotAccount, txtAccountExist;
    private Spinner spinnerField, spinnerGender;
    private int gender_id = 0, majer_id = 0;
    private MainRepository repository;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        mAccountManager = AccountManager.get(getBaseContext());
        AppConfig.currentActivity = this;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        repository = new MainRepository(this);
        initUI();
        setDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.mInstance.setICheckNetwork(this);
        if (repository == null)
            repository = new MainRepository(this);
    }

    private void initUI() {

        loginView = findViewById(R.id.login);
        registerView = findViewById(R.id.register);

        //login
        btnLogin = loginView.findViewById(R.id.btnLogin);
        edtNathonalCode = loginView.findViewById(R.id.edtNathinalCode);
        edtPhone = loginView.findViewById(R.id.edtPhone);
        txtDoNotAccount = loginView.findViewById(R.id.txtDoNotAccount);

        //register
        btnRegister = registerView.findViewById(R.id.btnRegister);
        edtNationalCodeReg = registerView.findViewById(R.id.edtNathinalCode);
        edtPhoneReg = registerView.findViewById(R.id.edtPhone);
        edtFirstName = registerView.findViewById(R.id.edtFirstName);
        edtLastName = registerView.findViewById(R.id.edtLastName);
        edtEmail = registerView.findViewById(R.id.edtEmail);
        txtAccountExist = registerView.findViewById(R.id.txtAccountExist);
        spinnerField = registerView.findViewById(R.id.spinnerField);
        spinnerGender = registerView.findViewById(R.id.spinnerGender);

        spinnerField.setAdapter(
                new FilterAdapterBySpinner(getApplicationContext(),
                        R.layout.spinner_item,
                        getResources().getStringArray(R.array.field)));

        spinnerGender.setAdapter(
                new FilterAdapterBySpinner(getApplicationContext(),
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.gender)));

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        txtDoNotAccount.setOnClickListener(this);
        txtAccountExist.setOnClickListener(this);

        spinnerGender.setOnItemSelectedListener(this);
        spinnerField.setOnItemSelectedListener(this);

    }

    private void getLoginInfo(User user) {

        dialog.show();
        repository.userInfo(user, new IServerCallbackObject() {

            @Override
            public void onSuccess(Object obj) {

                UserInfo u = (UserInfo) obj;
                Data data = u.getData();
                User user1 = data.getUser();
                addAccount(user1, data.getAccessToken());
                mFirebaseAnalytics.setUserId("" + user1.getId());
                dialog.dismiss();
                startActivity(new Intent(AuthenticatorActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();
                Log.i(TAG, "onSuccess: onFailure " + message);
            }
        });


    }

    private void addAccount(User user, String authToken) {

        Bundle data = new Bundle();
        Bundle userData = new Bundle();
        Gson gson = new Gson();

        try {
            data.putString(AccountManager.KEY_ACCOUNT_NAME, user.getMobile());
            data.putString(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
            data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            data.putString(AccountManager.KEY_PASSWORD, user.getNationalCode());

            userData.putString(AccountManager.KEY_USERDATA, gson.toJson(user));

        } catch (Exception e) {
            data.putString(KEY_ERROR_MESSAGE, e.getMessage());
        }


        Account account = new Account(user.getMobile(), ACCOUNT_TYPE);
        // Creating the account on the device and setting the auth token we got
        // (Not setting the auth token will cause another call to the server to authenticate the user)
        mAccountManager.addAccountExplicitly(account, user.getNationalCode(), userData);
        mAccountManager.setAuthToken(account, ARG_AUTH_TYPE, authToken);
        MyPreferenceManager.getInatanse().setApiToken(authToken);

        Intent res = new Intent();
        res.putExtras(data);
        setAccountAuthenticatorResult(res.getExtras());
        setResult(RESULT_OK, res);
        //finish();
    }

    private void setDialog() {
        dialog = new AlertDialog.Builder(this)
                .setView(R.layout.progress_dialog)
                .create();
        dialog.setCancelable(true);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.txtDoNotAccount) {

            if (loginView.getVisibility() == View.VISIBLE) {
                loginView.setVisibility(View.GONE);
                registerView.setVisibility(View.VISIBLE);
            }

        } else if (view.getId() == R.id.txtAccountExist) {

            if (registerView.getVisibility() == View.VISIBLE) {
                loginView.setVisibility(View.VISIBLE);
                registerView.setVisibility(View.GONE);
            }

        } else if (view.getId() == R.id.btnLogin) {
            login = true;
            login();

        } else if (view.getId() == R.id.btnRegister) {
            login = false;
            register();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        int i = parent.getId();
        if (i == R.id.spinnerGender) {//1= man,2=woman
            gender_id = pos;
        } else if (i == R.id.spinnerField) {//1=riuazi,2=tagrobi,3=ensani
            majer_id = pos;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckNetwork(boolean flag) {
        if (!flag && !showNoInternetDialog) {
            showDialog();
        }
    }

    private void login() {
        if (edtPhone.getText().length() == 0) {
            edtPhone.setError(getResources().getString(R.string.empty_phone));
            return;
        }
        if (!Utils.validPhone(edtPhone.getText().toString())) {
            edtPhone.setError(getResources().getString(R.string.not_valid_phone));
            return;
        }
        if (edtNathonalCode.getText().length() == 0) {
            edtNathonalCode.setError(getResources().getString(R.string.empty_personal_code));
            return;
        }
        nationalCode.check(edtNathonalCode.getText().toString());
        if (!nationalCode.isValid()) {
            edtNathonalCode.setError(nationalCode.getMessage());
            return;
        }

        //checked
        if (!Utils.isConnected()) {
            showDialog();
            return;
        }
        User user = new User();
        user.setMobile(edtPhone.getText().toString().trim().toLowerCase());
        user.setPassword(edtNathonalCode.getText().toString().trim().toLowerCase());
        getLoginInfo(user);
    }

    private void register() {
        if (Objects.requireNonNull(edtPhoneReg.getText()).length() == 0) {
            edtPhoneReg.setError(getResources().getString(R.string.empty_phone));
            return;
        }
        if (!Utils.validPhone(edtPhoneReg.getText().toString())) {
            edtPhoneReg.setError(getResources().getString(R.string.not_valid_phone));
            return;
        }
        if (Objects.requireNonNull(edtNationalCodeReg.getText()).length() == 0) {
            edtNationalCodeReg.setError(getResources().getString(R.string.empty_personal_code));
            return;
        }

        nationalCode.check(edtNationalCodeReg.getText().toString());
        if (!nationalCode.isValid()) {
            edtNationalCodeReg.setError(nationalCode.getMessage());
            return;
        }
        if (Objects.requireNonNull(edtFirstName.getText()).length() == 0) {
            edtFirstName.setError(getResources().getString(R.string.empty_first_name));
            return;
        }
        if (Objects.requireNonNull(edtLastName.getText()).length() == 0) {
            edtLastName.setError(getResources().getString(R.string.empty_last_name));
            return;
        }
        if (Objects.requireNonNull(edtEmail.getText()).length() == 0) {
            edtEmail.setError(getResources().getString(R.string.empty_email));
            return;
        }
        if (!Utils.validEmail(edtEmail.getText().toString())) {
            edtEmail.setError(getResources().getString(R.string.not_valid_email));
            return;
        }
        // Log.i(TAG, "onClick: " + gender_id + " " + majer_id);
        if (gender_id <= 0) {
            toastShow("جنسیت انتخاب شود" + gender_id, MDToast.TYPE_ERROR);
            return;
        }
        if (majer_id <= 0) {
            toastShow("رشته انتخاب شود " + majer_id, MDToast.TYPE_ERROR);
            return;
        }

        //checked
        if (!Utils.isConnected()) {
            showDialog();
            return;
        }
        User user = new User();
        user.setMobile(edtPhoneReg.getText().toString().trim().toLowerCase());
        user.setPassword(edtNationalCodeReg.getText().toString().trim().toLowerCase());
        user.setFirstName(edtFirstName.getText().toString().trim().toLowerCase());
        user.setLastName(edtLastName.getText().toString().trim().toLowerCase());
        user.setEmail(edtEmail.getText().toString().trim().toLowerCase());
        user.setGender_id(gender_id);
        user.setMajor_id(majer_id);
        getLoginInfo(user);
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_no_internet);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        showNoInternetDialog = true;
        Button btnOK = dialog.findViewById(R.id.btnOK);
        ImageView imgCLose = dialog.findViewById(R.id.imgCLose);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (login)
                    login();
                else
                    register();
                dialog.dismiss();
                showNoInternetDialog = false;
            }
        });
        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoInternetDialog = false;
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
