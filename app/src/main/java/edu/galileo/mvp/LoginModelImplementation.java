package edu.galileo.mvp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import edu.galileo.mvp.domain.FirebaseHelper;
import edu.galileo.mvp.event.CanceledEvent;
import edu.galileo.mvp.event.PasswordErrorEvent;
import edu.galileo.mvp.event.SuccessEvent;
import edu.galileo.mvp.event.UserNameErrorEvent;


public class LoginModelImplementation implements LoginModel {

    private FirebaseHelper helper;
    private DatabaseReference myUserReference;

    public LoginModelImplementation(){
        helper = FirebaseHelper.getInstance();
        myUserReference = helper.getMyUserReference();
    }

    @Override
    public void login(String username, String password) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(username, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            myUserReference = helper.getMyUserReference();
                            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    EventBus.getDefault().post(new SuccessEvent());
                                }
                                @Override
                                public void onCancelled(DatabaseError firebaseError) {
                                    EventBus.getDefault().post(new CanceledEvent());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Log.d("LoginModel", "onFailure: "+e);
                            if( (e.toString()).contains("password") ) {
                                EventBus.getDefault().post(new PasswordErrorEvent());
                            } else {
                                EventBus.getDefault().post(new UserNameErrorEvent());
                            }
                        }
                    });
        } catch (Exception e) {
            EventBus.getDefault().post(new UserNameErrorEvent());
        }
    }

}

