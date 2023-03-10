package com.example.cloudapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cloudapp.R
import com.example.cloudapp.adapters.ProductAdapter
import com.example.cloudapp.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts(db) // fetch data only
        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (products.isNotEmpty()) {
                binding.productRecyclerView.adapter = ProductAdapter(requireActivity()) {
                    viewModel.setProduct(it)
                    findNavController().navigate(R.id.action_navigation_home_to_viewProductFragment)
                }
                (binding.productRecyclerView.adapter as ProductAdapter).submitList(products)
            } else {
                binding.productRecyclerView.visibility = View.GONE
            }
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
        // swipe to refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getProducts(db)
        }

        binding.fabAdd.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_addProductFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val COLL_PRODUCTS = "Products"
    }
}