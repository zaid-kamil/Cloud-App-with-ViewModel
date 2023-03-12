package com.example.cloudapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudapp.R
import com.example.cloudapp.databinding.FragmentEditProductBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditProductFragment : BottomSheetDialogFragment() {


    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var db: FirebaseFirestore
    private lateinit var _binding: FragmentEditProductBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_product, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabSave.setOnClickListener {
            val name = binding.editPrdName.text.toString()
            val brand = binding.editPrdBrand.text.toString()
            val category = binding.editPrdCategory.text.toString()
            val price = binding.editPrdPrice.text.toString()
            // valid if values are not empty
            if (name.isNotEmpty() && brand.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty()) {
                viewModel.updateProduct(db, name, brand, category, price)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.saveState.observe(viewLifecycleOwner) {
            when (it) {
                ProductState.SAVED -> {
                    Toast.makeText(requireContext(), "Product saved", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editProductFragment_to_navigation_home)
                }
                ProductState.ERROR -> {
                    Toast.makeText(requireContext(), "Error saving product", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    // do nothing
                }
            }
        }
    }
}