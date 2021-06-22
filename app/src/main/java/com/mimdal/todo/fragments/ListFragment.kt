package com.mimdal.todo.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.arch.core.util.Function
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mimdal.todo.R
import com.mimdal.todo.adapter.TodoListAdapter
import com.mimdal.todo.data.model.Todo
import com.mimdal.todo.databinding.FragmentListBinding
import com.mimdal.todo.databinding.FragmentListNewBinding
import com.mimdal.todo.util.Util
import com.mimdal.todo.viewModel.TodoViewModel

class ListFragment : Fragment() {

    private var listBinding: FragmentListNewBinding? = null
    private val viewModel: TodoViewModel by viewModels()
    private val adapter: TodoListAdapter by lazy { TodoListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        listBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_new, container, false)
        listBinding!!.lifecycleOwner = this
        return listBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listBinding!!.viewModel = this.viewModel

        //set toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(listBinding!!.mainToolbar)


        //make fab visible
        requireActivity().findViewById<FloatingActionButton>(R.id.fab).visibility = View.VISIBLE

        //set menu
        setHasOptionsMenu(true)

        //setup recyclerview
        setupRecyclerView()


        Transformations.switchMap(
            viewModel.searchQuery
        ) {
            Log.d("ListFragment", "searchQuery is ${viewModel.searchQuery.value}")
            if (!it.isNullOrEmpty()) {
                Log.d("ListFragment", "searchInDatabase is called")
                searchInDatabase(it)
            } else {
                Log.d("ListFragment", "getAllData is called")
                getAllData()
            }

        }.observe(viewLifecycleOwner, Observer { it ->

            adapter.setData(it)

            //check if empty views should be shown
            if (it.isEmpty()) {
                isDataListEmpty(true)
            } else {
                isDataListEmpty(false)
            }

        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAllItems()
            R.id.menu_priority_high -> sortByPriorityHigh()
            R.id.menu_priority_low -> sortByPriorityLow()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortByPriorityLow() {
        val sortedByPriorityLowList = adapter.dataList.sortedBy { it.priority }
        adapter.setData(sortedByPriorityLowList.reversed())
    }

    private fun sortByPriorityHigh() {
        val sortedByPriorityHighList = adapter.dataList.sortedBy { it.priority }
        adapter.setData(sortedByPriorityHighList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!viewModel.searchQuery.value.isNullOrEmpty()) {

                        viewModel.searchQuery.value = ""

                    } else {
                        requireActivity().finish()
                    }

                }
            }
        )
    }

    private fun setupRecyclerView() {
        listBinding!!.mainRecyclerView.adapter = adapter
        listBinding!!.mainRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        listBinding!!.mainRecyclerView.itemAnimator = null
        swipeToDelete(listBinding!!.mainRecyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipe = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                viewModel.deleteData(deletedItem)
                restoreDeletedItem(deletedItem);
            }
        }

        ItemTouchHelper(swipe).attachToRecyclerView(recyclerView)

    }

    private fun restoreDeletedItem(deletedItem: Todo) {
        Snackbar.make(
            requireActivity().findViewById<ConstraintLayout>(R.id.root_layout),
            "Item successfully deleted!",
            Snackbar.LENGTH_SHORT
        ).setAction("undo") {

            viewModel.insertData(deletedItem)
        }
            .show()
    }


    private fun deleteAllItems() {
        AlertDialog.Builder(requireActivity()).apply {
            setMessage("Are you sure to delete ALL items?")
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllData()
                Snackbar.make(
                    requireActivity().findViewById<ConstraintLayout>(R.id.root_layout),
                    "Every thing successfully deleted!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            setNegativeButton("No") { _, _ ->
            }

            create().show()
        }
    }

    private fun isDataListEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            listBinding!!.mainImageEmptyBox.visibility = View.VISIBLE
            listBinding!!.mainTxtEmptyBox.visibility = View.VISIBLE
        } else {
            listBinding!!.mainImageEmptyBox.visibility = View.INVISIBLE
            listBinding!!.mainTxtEmptyBox.visibility = View.INVISIBLE
        }
    }


    private fun searchInDatabase(query: String?): LiveData<List<Todo>> {
        return viewModel.searchDatabase("%$query%")
    }

    private fun getAllData(): LiveData<List<Todo>> {
        return viewModel.getAllData
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listBinding = null
    }


}
