package com.example.moviesapp.ui.Login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLoginBinding
import com.example.moviesapp.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.loginButton.setOnClickListener {
            val email = binding.LoginEmail.text.toString()
            val password = binding.LoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(activity, HomeActivity::class.java)
                        requireActivity().finish()
                        startActivity(intent)
                    }
                }
            } else {
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
                viewPager.currentItem = 1
            }
        }

        binding.LoginRedirectSignup.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            viewPager.currentItem = 1
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(activity, HomeActivity::class.java)
            requireActivity().finish()
            startActivity(intent)
        }
    }
}