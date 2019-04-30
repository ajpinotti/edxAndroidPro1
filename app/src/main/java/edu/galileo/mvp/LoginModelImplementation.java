package edu.galileo.mvp;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import edu.galileo.mvp.event.CanceledEvent;
import edu.galileo.mvp.event.PasswordErrorEvent;
import edu.galileo.mvp.event.SuccessEvent;
import edu.galileo.mvp.event.UserNameErrorEvent;

public class LoginModelImplementation implements LoginModel {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[] {
            "test@galileo.edu:testtest", "pinotaj@galileo.edu:android"
    };

    @Override
    public void login(String username, String password) {
        new UserLoginTask(username, password).execute((Void) null);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 1;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    if(pieces[1].equals(mPassword)) {
                        return 0;
                    }
                    else {
                        return 1;
                    }
                }
            }

            // TODO: register the new account here.
            return 2;   //username not found
        }

        @Override
        protected void onPostExecute(final Integer success) {
            if (success == 0) {
                EventBus.getDefault().post(new SuccessEvent());
            } else if (success == 1){
                EventBus.getDefault().post(new PasswordErrorEvent());
            }
            else if (success == 2) {
                EventBus.getDefault().post(new UserNameErrorEvent());
            }
        }

        @Override
        protected void onCancelled() {
            EventBus.getDefault().post(new CanceledEvent());
        }
    }
}

