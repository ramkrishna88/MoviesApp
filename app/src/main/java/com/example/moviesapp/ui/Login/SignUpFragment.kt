package com.example.moviesapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.SignUpButton.setOnClickListener {
            val email = binding.SignUpEmail.text.toString()
            val password = binding.SignUpPassword.text.toString()
            val conformPassword = binding.SignUpPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && conformPassword.isNotEmpty()) {
                if (password == conformPassword) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val viewPager =
                                    requireActivity().findViewById<ViewPager2>(R.id.viewPager)
                                viewPager.currentItem = 0
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    it.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(requireContext(), "Your message here", Toast.LENGTH_SHORT).show()

                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Empty fields are not allowed ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.LoginRedirect.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            viewPager.currentItem = 0
        }
        return binding.root
    }
}