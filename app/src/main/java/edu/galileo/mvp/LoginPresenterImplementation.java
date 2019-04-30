package edu.galileo.mvp;

import android.text.TextUtils;

public class LoginPresenterImplementation implements LoginPresenter {

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImplementation(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImplementation();
    }

    @Override
    public void validateCredentials(String username, String password) {
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            loginView.setPasswordError(R.string.error_invalid_password);
            return;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            loginView.setUsernameError(R.string.error_field_required);
            return;
        } else if (!isEmailValid(username)) {
            loginView.setUsernameError(R.string.error_invalid_email);
            return;
        }

        loginView.showProgress(true);
        loginModel.login(username,password);
    }

    /**
     * Moving to EventBus
    @Override
    public void onCanceled() {
        loginView.showProgress(false);
    }

    @Override
    public void onPasswordError() {
        loginView.showProgress(false);
        loginView.setPasswordError(R.string.error_incorrect_password);
    }

    @Override
    public void onUserNameError() {
        loginView.showProgress(false);
        loginView.setUsernameError(R.string.error_user_not_found);
    }

    @Override
    public void onSuccess() {
        loginView.showProgress(false);
        loginView.successAction();
    }
    */

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
