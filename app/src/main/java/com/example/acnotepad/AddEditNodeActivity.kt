package com.example.acnotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.acnotepad.roomdb.Note
import java.text.SimpleDateFormat
import java.util.*

class AddEditNodeActivity : AppCompatActivity() {

    lateinit var noteTitleEdit: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addUpdateButton: Button
    lateinit var viewModel: NoteViewModel
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_node)

        noteTitleEdit = findViewById(R.id.idETNoteTitle)
        noteDescriptionEdit = findViewById(R.id.idETNoteDescription)
        addUpdateButton = findViewById(R.id.idBtnAddUpdate)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID",-1)
            addUpdateButton.text = "Update Note"
            noteTitleEdit.setText(noteTitle)
            noteDescriptionEdit.setText(noteDesc)
        } else {
            addUpdateButton.text = "Save Note"
        }

        addUpdateButton.setOnClickListener{
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription,currentDate)
                    updateNote.id = noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated...", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate:String=  sdf.format(Date())
                    viewModel.addNote(Note(noteTitle,noteDescription,currentDate))
                    Toast.makeText(this, "Note Added...", Toast.LENGTH_SHORT).show()
                }
            }

            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()

        }

    }

}