package com.tuanha.photo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tuanha.photo.R
import com.tuanha.photo.ui.adapters.PhotoAdapter
import com.tuanha.photo.ui.viewmodels.PhotoViewModel
import com.tuanha.photo.utils.getAdapter
import kotlinx.android.synthetic.main.fragment_photo.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoFragment : Fragment() {

    private val viewModel by viewModel<PhotoViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.cloneInContext(requireActivity()).inflate(R.layout.fragment_photo, container, false) as View
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecPhoto()

        observeData()
    }

    private fun setUpRecPhoto() {
        swipeRefreshLayout.isRefreshing = true
        PhotoAdapter().setRecyclerView(recPhoto)
    }

    private fun observeData() = with(viewModel) {
        photoViewItem.observe(viewLifecycleOwner) {
            recPhoto.getAdapter<PhotoAdapter>()?.submitList(it)

            swipeRefreshLayout.isRefreshing = false
            swipeRefreshLayout.isEnabled = false
        }
    }
}