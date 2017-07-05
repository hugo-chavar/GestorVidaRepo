package ar.com.fiuba.tddp1.gestorvida;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.PingListener;
import ar.com.fiuba.tddp1.gestorvida.web.RequestSender;
import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, ResponseListener {


    // UI references.
    private EditText mNameView;
    private EditText mEmailView;
    private EditText mNacimientoView;
    private EditText mPasswordView;
    private TextView mGeneroTextView;
    private TextView mUsuarioTextView;
    private RadioGroup mGeneroView;
    private RadioGroup mUsuarioView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mNameView = (EditText) findViewById(R.id.name_register);
        mEmailView = (EditText) findViewById(R.id.email_register);
        mNacimientoView = (EditText) findViewById(R.id.nacimiento_register);

        mNacimientoView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        int s=monthOfYear+1;
                        String a = dayOfMonth+"/"+s+"/"+year;
                        mNacimientoView.setText(""+a);
                    }
                };

                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(RegisterActivity.this, dpd, date.year ,date.month, date.monthDay);
                d.updateDate(2017,6,1);
                d.show();

            }
        });

        mGeneroTextView = (TextView) findViewById(R.id.genero_register);
        mUsuarioTextView = (TextView) findViewById(R.id.usuario_register);
        mGeneroView = (RadioGroup) findViewById(R.id.genero_radioGroup);
        mUsuarioView = (RadioGroup) findViewById(R.id.usuario_radioGroup);

        mPasswordView = (EditText) findViewById(R.id.password_register);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.button_register);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.form_register);
        mProgressView = findViewById(R.id.progress_register);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mNameView.setError(null);
        mEmailView.setError(null);
        mNacimientoView.setError(null);
        mPasswordView.setError(null);
        mGeneroTextView.setError(null);
        mUsuarioTextView.setError(null);


        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String nacimiento = mNacimientoView.getText().toString();
        String password = mPasswordView.getText().toString();
        String genero = null;
        String usuario = null;

        boolean cancel = false;
        View focusView = null;

        //Check Radio buttons
        if (mGeneroView.getCheckedRadioButtonId() == -1) {
            mGeneroTextView.setError(getString(R.string.error_field_required));
            focusView = mGeneroTextView;
            cancel = true;
        } else {
            genero = ((RadioButton) mGeneroView.findViewById(mGeneroView.getCheckedRadioButtonId())).getText().toString();
        }

        if (mUsuarioView.getCheckedRadioButtonId() == -1) {
            mUsuarioTextView.setError(getString(R.string.error_field_required));
            focusView = mUsuarioTextView;
            cancel = true;
        } else {
            usuario = ((RadioButton) mUsuarioView.findViewById(mUsuarioView.getCheckedRadioButtonId())).getText().toString();
        }

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        } else if (!TextUtils.isEmpty(name) && !isNameValid(name)) {
            mNameView.setError(getString(R.string.error_invalid_name));
            focusView = mNameView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid birth date.
        if (TextUtils.isEmpty(nacimiento)) {
            mNacimientoView.setError(getString(R.string.error_field_required));
            focusView = mNacimientoView;
            cancel = true;
        } else if (!isNacimientoValid(nacimiento)) {
            mNacimientoView.setError(getString(R.string.error_invalid_nacimiento));
            focusView = mNacimientoView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            register(name, email, genero, usuario, nacimiento, password);
        }
    }

    private void register(String name, String email, String genero, String usuario, String nacimiento, String password) {

        Map<String,String> _params;
        _params = new HashMap<String,String>();
        RequestSender requestSender = new RequestSender(this);
        _params.put("name", name);
        _params.put("username", name.trim().toLowerCase());
        _params.put("email", email);
        //_params.put("sexo", mGenero); // TODO: no lo acepta el server
        _params.put("password", password);
        _params.put("nacimiento", nacimiento);

        JSONObject obj = new JSONObject(_params);

        String url = getString(R.string.url) + "users/register";


        requestSender.doPost(this, url, new JSONObject(_params));

        //PingListener listener = new PingListener(context);
        //String url2 = getString(R.string.url) + "ping";
        //requestSender.doGet(listener, url2);
    }

    private void doPing() {
        RequestSender requestSender = new RequestSender(this);
        PingListener listener = new PingListener(this);
        String url = getString(R.string.url) + "ping";
        requestSender.doGet(listener, url);
    }

    private boolean isNacimientoValid(String nacimiento) {
        return nacimiento.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
    }

    private boolean isNameValid(String name) {
        return name.length() > 2;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onRequestCompleted(Object response) {

        showProgress(false);
        try {
            JSONObject jsonObject = (JSONObject)response;
            Perfil.token = jsonObject.getString("token");
            Perfil.id = jsonObject.getString("id");
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
            goToMain();
        } catch (JSONException e) {
            showError("No se pudo obtener el Token");
        }

    }

    @Override
    public void onRequestError(int cod, String errorMessage) {

        showProgress(false);
        if (cod == 409) {
            mNameView.setError(getString(R.string.error_username_duplicated));
            mNameView.requestFocus();
        }
        showError(errorMessage);
    }

    public void showError(String error) {
        Log.d("RegisterActivity", error);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

    }

    public void goToMain() {
        //Mostrar pantalla principal
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

