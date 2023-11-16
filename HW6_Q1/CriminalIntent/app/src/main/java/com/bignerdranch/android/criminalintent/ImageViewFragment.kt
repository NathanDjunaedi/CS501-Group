package com.bignerdranch.android.criminalintent

import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnLayout
import androidx.fragment.app.DialogFragment
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeDetailBinding
import com.bignerdranch.android.criminalintent.databinding.FragmentImageViewBinding
import java.io.File

private const val TAG = "IMAGEVIEW_FRAGMENT"
class ImageViewFragment : DialogFragment() {
    private var _binding: FragmentImageViewBinding? = null
    private var imageUri: String? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.getString("imageURI") != null) {
                imageUri = it.getString("imageURI")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
            _binding = FragmentImageViewBinding.inflate(inflater, container, false)
            return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updatePhoto(imageUri)
    }

    private fun updatePhoto(photoFileName: String?) {
            val photoFile = photoFileName?.let {
                File(requireContext().applicationContext.filesDir, it)
            }
            if (photoFile?.exists() == true) {
                binding.zoomedImageView.doOnLayout { measuredView ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        measuredView.width,
                        measuredView.height
                    )
                    binding.zoomedImageView.setImageBitmap(scaledBitmap)
                    binding.zoomedImageView.tag = photoFileName
                }
            } else {
                binding.zoomedImageView.setImageBitmap(null)
                binding.zoomedImageView.tag = null
            }
        }
    }