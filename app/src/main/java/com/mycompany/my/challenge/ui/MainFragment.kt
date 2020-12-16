package com.mycompany.my.challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.mycompany.my.challenge.R
import com.mycompany.my.challenge.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!! as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        binding.btnMain.isEnabled = false

        binding.etMainLowerBound.doAfterTextChanged {
            val lower = it.toString()
            val higher = binding.etMainHighBound.text.toString()
            binding.btnMain.isEnabled =
                lower.isNotBlank() && higher.isNotBlank() && lower.toInt() < higher.toInt()
            if (lower.isNotBlank() && higher.isNotBlank())
                if (lower.toInt() >=higher.toInt()) {
                    binding.etMainLowerBound.error = getString(R.string.error_lower_more_high)
                }
        }

        binding.etMainHighBound.doAfterTextChanged {
            val higher = it.toString()
            val lower = binding.etMainLowerBound.text.toString()
            binding.btnMain.isEnabled =
                lower.isNotBlank() && higher.isNotBlank() && lower.toInt() < higher.toInt()
            if (lower.isNotBlank() && higher.isNotBlank())
                if (lower.toInt() >=higher.toInt()) {
                    binding.etMainHighBound.error = getString(R.string.error_lower_more_high)
                }

        }

        btnMain.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCommentFragment(
                binding.etMainLowerBound.text.toString().toInt(),
                binding.etMainHighBound.text.toString().toInt()
            )
            findNavController(this).navigate(action)
        }

    }

}
