//package com.example.rzucpaleniem
//
//import android.util.Log
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
//import com.google.firebase.auth.FirebaseAuthInvalidUserException
//import com.google.firebase.auth.FirebaseAuthUserCollisionException
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//class AuthHelper {
//
//    private val auth: FirebaseAuth by lazy {
//        Firebase.auth
//    }
//
//    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
//        // Funkcja obsługująca logowanie do aplikacji za pośrednictwem Firebase
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    onComplete(true, null)
//                } else {
//                    val exception = task.exception
//                    val errorMessage = when (exception) {
//                        is FirebaseAuthInvalidUserException -> "Nieprawidłowe dane logowania" //email
//                        is FirebaseAuthInvalidCredentialsException -> "Nieprawidłowe dane logowania" //uname
//                        else -> "Logowanie nie powiodło się"
//                    }
//                    onComplete(false, errorMessage)
//                }
//            }
//    }
//
//    fun register(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
//        // Funkcja obługująca rejestracje do bazy użytkowników
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    onComplete(true, null)
//                } else {
//                    val exception = task.exception
//                    val errorMessage = when (exception) {
//                        is FirebaseAuthUserCollisionException -> "Email obecnie w użyciu"
//                        else -> "Rejestracja nie powiodła się"
//                    }
//                    onComplete(false, errorMessage)
//                }
//            }
//    }
//
//    fun resetPassword(email: String, onComplete: (Boolean, String?) -> Unit) {
//        // Funkcja obsługująca reset hasła
//        auth.sendPasswordResetEmail(email)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    onComplete(true, null)
//                } else {
//                    onComplete(false, "Nie udało się wysłać wiadomości email")
//                }
//            }
//    }
//
//    fun isUserLoggedIn(): Boolean {
//        // Funkcja sprawdzająca stan logowania użytkownika
//        // Pobierz obiekt FirebaseAuth
//        val auth = FirebaseAuth.getInstance()
//
//        // Sprawdź, czy użytkownik jest zalogowany (czy currentUser nie jest null)
//        return auth.currentUser != null
//    }
//}