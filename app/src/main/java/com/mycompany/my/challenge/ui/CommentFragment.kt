package com.mycompany.my.challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycompany.my.challenge.R
import com.mycompany.my.challenge.adapters.CommentsAdapter
import com.mycompany.my.challenge.databinding.FragmentCommentBinding
import com.mycompany.my.challenge.factories.CommentViewModelFactory
import com.mycompany.my.challenge.viewmodels.CommentViewModel
import kotlinx.android.synthetic.main.fragment_comment.*
import java.util.*


class CommentFragment : Fragment() {

    private lateinit var viewModel: CommentViewModel
    private lateinit var binding: FragmentCommentBinding
    private var loadTimer: Timer? = null

    private val args: CommentFragmentArgs by navArgs()
    private val lowerBound by lazy { args.lowBound }
    private val highestBound by lazy { args.highBound }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        val viewModelFactory = CommentViewModelFactory(lowerBound, highestBound)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CommentViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!! as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val commentsAdapter = CommentsAdapter()
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentsAdapter
        }

        binding.btnMainCancel.setOnClickListener {
            flAnimation.visibility = View.GONE
            btnMainCancel.visibility = View.GONE
            findNavController(this).popBackStack()
        }

        viewModel.getLoadingEvent().observe(viewLifecycleOwner, {
            activity?.runOnUiThread {
                binding.flAnimation.visibility = if (it) View.VISIBLE else View.GONE
                if (it && loadTimer == null) {
                    loadTimer = Timer()
                    loadTimer!!.schedule(object : TimerTask() {
                        override fun run() {
                            activity?.runOnUiThread {
                                binding.btnMainCancel.visibility = View.VISIBLE
                            }
                            loadTimer!!.cancel()
                            loadTimer = null
                        }
                    }, 3000)
                } else if (!it) {
                    if (loadTimer != null) {
                        loadTimer!!.cancel()
                        loadTimer = null
                    }
                    binding.btnMainCancel.visibility = View.GONE
                }
            }
        })

        viewModel.getData().observe(viewLifecycleOwner,
            { t ->
                commentsAdapter.submitList(t)
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController(this).popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
