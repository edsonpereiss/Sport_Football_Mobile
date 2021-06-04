package com.aroniez.futaa.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aroniez.futaa.R
import com.aroniez.futaa.api.dirtyWords
import com.aroniez.futaa.models.User
import com.aroniez.futaa.utils.SharedPreferencesUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val RC_SIGN_IN = 9001
    private lateinit var auth: FirebaseAuth
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        googleLoginButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun showUsernameDialog(firebaseUser: FirebaseUser) {
        Toast.makeText(this, "Choose username to continue", Toast.LENGTH_LONG).show()
        val dialogBuilder = AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_username_set, null)
        dialogBuilder.setView(dialogView)

        val editText = dialogView.findViewById(R.id.editText) as EditText
        val checkBox = dialogView.findViewById(R.id.languageCheckbox) as CheckBox
        val loginButton = dialogView.findViewById(R.id.button) as Button

        loginButton.setOnClickListener {
            if (checkBox.isChecked) {
                val username = editText.text.toString()
                if (username.isEmpty()) {
                    Toast.makeText(this, "Username is required to proceed", Toast.LENGTH_LONG).show()
                } else {
                    if (dirtyWords.contains(username)) {
                        Toast.makeText(this, "Please choose another username", Toast.LENGTH_LONG).show()
                    } else {
                        saveUserToFirebase(username, firebaseUser)
                    }
                }
            } else {
                Toast.makeText(this, "You need to confirm no use of abusive language", Toast.LENGTH_LONG).show()
            }
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        showUsernameDialog(user!!)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        //Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                    // ...
                }
    }

    private fun saveUserToFirebase(username: String, firebaseUser: FirebaseUser) {
        val usersReference = FirebaseDatabase.getInstance()
                .getReference("users")
        val user = User()
        user.uid = firebaseUser.uid
        user.email = firebaseUser.email
        user.name = firebaseUser.displayName

        val updateRef = usersReference.child(username)
        val postValues = user.toMap()
        updateRef.updateChildren(postValues)

        SharedPreferencesUtil.saveUsername(this, username)
        SharedPreferencesUtil.saveUserLoggedInToPrefs(this, true)
        Toast.makeText(this, "Logged in successfully", Toast.LENGTH_LONG).show()
        finish()

    }


}