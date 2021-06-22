package com.mimdal.todo.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mimdal.todo.R
import com.mimdal.todo.adapter.CustomSpinner
import com.mimdal.todo.data.model.Priority
import com.mimdal.todo.data.model.SpinnerPriority
import com.mimdal.todo.data.model.Todo
import com.mimdal.todo.databinding.FragmentUpdateBinding
import com.mimdal.todo.databinding.FragmentUpdateNewBinding
import com.mimdal.todo.util.Util
import com.mimdal.todo.viewModel.TodoViewModel


class UpdateFragment : Fragment() {

    private var updateBinding: FragmentUpdateNewBinding? = null
    private val viewModel: TodoViewModel by viewModels()
    private val args: UpdateFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        updateBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_new, container, false)
        updateBinding!!.args = this.args
        return updateBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        //set toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(updateBinding!!.updateToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Update"
            setDisplayHomeAsUpEnabled(true)
        }


        requireActivity().findViewById<FloatingActionButton>(R.id.fab).visibility = View.GONE
        updateBinding!!.updateSpinner.adapter =
            CustomSpinner(requireActivity(), Util.createSpinnerList())
        updateBinding!!.updateSpinner.onItemSelectedListener = viewModel.spinnerItemSelectedListener

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_save) {
            updateTodo()
        } else if (item.itemId == R.id.menu_delete) {
            deleteTodo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTodo() {
        AlertDialog.Builder(requireActivity()).apply {
            setMessage("Are you sure to delete this item?")
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteData(args.todo)
                Snackbar.make(
                    requireActivity().findViewById<ConstraintLayout>(R.id.root_layout),
                    "Item successfully deleted!",
                    Snackbar.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }

            setNegativeButton("No") { _, _ ->

            }

            create().show()
        }
    }

    private fun updateTodo() {

        val updatedId = args.todo.id
        val updatedTitle = updateBinding!!.updateTitleEdtText.text.toString()
        val updatedDescription = updateBinding!!.updateDescriptionEdtText.text.toString()
        val updatedPriority =
            (updateBinding!!.updateSpinner.selectedItem as SpinnerPriority).spinnerPriority

        if (viewModel.verifyUserData(updatedTitle, updatedDescription)) {

            val todo = Todo(
                updatedTitle,
                viewModel.convertStringToPriority(updatedPriority),
                updatedDescription
            )

            todo.id = updatedId

            viewModel.updateData(todo)

            Snackbar.make(
                requireActivity().findViewById<ConstraintLayout>(R.id.root_layout),
                "Successfully updated!",
                Snackbar.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Util.closeKeyboard(requireActivity().findViewById<ConstraintLayout>(R.id.update_frag))

        } else {
            Snackbar.make(
                requireActivity().findViewById<ConstraintLayout>(R.id.root_layout),
                "please fill all fields!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        updateBinding = null
    }

}