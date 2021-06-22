package com.mimdal.todo.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mimdal.todo.R
import com.mimdal.todo.adapter.CustomSpinner
import com.mimdal.todo.data.model.SpinnerPriority
import com.mimdal.todo.data.model.Todo
import com.mimdal.todo.databinding.FragmentAddBinding
import com.mimdal.todo.databinding.FragmentAddNewBinding
import com.mimdal.todo.util.Util
import com.mimdal.todo.viewModel.TodoViewModel


class AddFragment : Fragment() {

    private var addBinding: FragmentAddNewBinding? = null
    private val viewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new, container, false)
        return addBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //set toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(addBinding!!.addToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Add"
            setDisplayHomeAsUpEnabled(true)
        }

        requireActivity().findViewById<FloatingActionButton>(R.id.fab).visibility = View.GONE

        addBinding!!.addSpinner.adapter = CustomSpinner(requireActivity(), Util.createSpinnerList())
        addBinding!!.addSpinner.onItemSelectedListener = viewModel.spinnerItemSelectedListener

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val inputTitle = addBinding!!.addTitleEdtText.text.toString()
        val inputPriority = (addBinding!!.addSpinner.selectedItem as SpinnerPriority).spinnerPriority
        val inputDescription = addBinding!!.addDescriptionEdtText.text.toString()
        val validation = viewModel.verifyUserData(inputTitle, inputDescription)
        if (validation) {
            val todo = Todo(
                inputTitle,
                viewModel.convertStringToPriority(inputPriority),
                inputDescription
            )

            viewModel.insertData(todo)
            Snackbar.make(
                requireActivity().findViewById<ConstraintLayout>(R.id.add_frag),
                "Successfully added!",
                Snackbar.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

            //hide keyboard
            Util.closeKeyboard(requireActivity().findViewById<ConstraintLayout>(R.id.add_frag))

        } else {
            Snackbar.make(
                requireActivity().findViewById<ConstraintLayout>(R.id.add_frag),
                "Please fill all parts!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        addBinding = null
    }


}


