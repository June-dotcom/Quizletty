package edu.ucucite.quizlettyn.fragment_nav.logout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.login.StartActivity;


public class logoutFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        final GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (googleSignInAccount != null){
            GoogleSignInClient googleClient = GoogleSignIn.getClient(getContext(),
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build());
            googleClient.signOut();
        }else{
            // use facebook logout
            LoginManager.getInstance().logOut();

        }
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getContext(), StartActivity.class));


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_logout, container, false);
        return null;
    }
}