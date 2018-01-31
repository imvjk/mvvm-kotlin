/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.movies

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.*
import android.util.Log
import android.view.*
import example.com.mvvmmoviedb.R
import example.com.mvvmmoviedb.databinding.MoviesFragBinding
import example.com.mvvmmoviedb.util.EndlessScrollViewListener
import java.util.*

/**
 * Fragment for list of movies.
 */
class MoviesFragment : Fragment() {

    private lateinit var viewDataBinding: MoviesFragBinding
    private lateinit var listAdapter: MoviesAdapter
    private lateinit var endlessScrollListener: EndlessScrollViewListener
    var currentFiltering = Filter.POPULAR

    companion object {
        fun newInstance() = MoviesFragment()
        private var START_INDEX = 1
        private const val TAG = "MoviesFragment"

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = MoviesFragBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as MainActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        viewDataBinding.viewmodel?.loadMovies(START_INDEX)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.menu_filter -> {
                    showFilteringPopUpMenu()
                    true
                }
                else -> false
            }

    private fun showFilteringPopUpMenu() {
        PopupMenu(context!!, activity!!.findViewById<View>(R.id.menu_filter)).run {
            menuInflater.inflate(R.menu.filter_movie, menu)
            setOnMenuItemClickListener {
                val selectedFilter = when (it.itemId) {
                    R.id.latest -> Filter.RELEASE
                    else -> Filter.POPULAR
                }
                if (selectedFilter != currentFiltering) {
                    currentFiltering = selectedFilter
                    endlessScrollListener.resetState()
                    viewDataBinding.viewmodel?.run {
                        items.clear()
                        loadMovies(START_INDEX, currentFiltering == Filter.RELEASE)
                    }
                }
                true
            }
            show()
        }
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = MoviesAdapter(ArrayList(0), viewModel)
            with(viewDataBinding.recyclerView) {
                val layoutManager = LinearLayoutManager(context)
                setLayoutManager(layoutManager)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
                val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(context, R.drawable.list_item_divider)!!)
                }
                addItemDecoration(dividerItemDecoration)
                endlessScrollListener = object : EndlessScrollViewListener(layoutManager, START_INDEX) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        viewDataBinding.viewmodel?.loadMovies(page, currentFiltering == Filter.RELEASE)
                    }
                }
                addOnScrollListener(endlessScrollListener)
                adapter = listAdapter
            }
        } else {
            Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    enum class Filter {
        RELEASE,
        POPULAR
    }
}
