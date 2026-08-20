package com.example.coffieshopapp.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.coffieshopapp.Screen
import com.example.coffieshopapp.StateClass
import com.example.coffieshopapp.data.local.SharedPreferenceManager
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<StateClass>(StateClass.Idle)
    val authState: StateFlow<StateClass> = _authState.asStateFlow()


    fun signUp(context: Context, name: String, email: String, password: String) {
        _authState.value = StateClass.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email
                    )
                    if (userId != null) {
                        firestore.collection("users").document(userId).set(user)
                            .addOnSuccessListener {
                                SharedPreferenceManager.saveUserData(context, name, email)
                                _authState.value = StateClass.Success(emptyList()) // Success!
                            }
                            .addOnFailureListener { e ->
                                _authState.value = StateClass.Error(e.message ?: "Firestore error")
                            }
                    }
                } else {
                    _authState.value = StateClass.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }

    fun login(context: Context, email: String, password: String) {
        _authState.value = StateClass.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        firestore.collection("users").document(userId).get()
                            .addOnSuccessListener { document ->
                                val name = document.getString("name") ?: ""
                                val emailFromDb = document.getString("email") ?: email
                                SharedPreferenceManager.saveUserData(context, name, emailFromDb)
                                _authState.value = StateClass.Success(emptyList()) // Success!
                            }
                            .addOnFailureListener { e ->
                                _authState.value = StateClass.Error(e.message ?: "Firestore error")
                            }
                    }
                } else {
                    _authState.value = StateClass.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun logout(context: Context) {
        auth.signOut()
        SharedPreferenceManager.clearUserData(context)
        Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
    }

    fun updatePassword(context: Context, oldPass: String, newPass: String, confirmPass: String) {
        val user = auth.currentUser
        if (user == null || user.email == null) {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(context, "Please enter new password", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPass != confirmPass) {
            Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = EmailAuthProvider.getCredential(user.email!!, oldPass)

        user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
            if (reauthTask.isSuccessful) {
                user.updatePassword(newPass).addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to update password: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Old password is wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetState() {
        _authState.value = StateClass.Idle
    }


    fun getStartDirection(context: Context): String {
        val email = SharedPreferenceManager.getUserEmail(context)
        return if (email == null) Screen.Login.route else Screen.Home.route
    }

}