package com.yasir_hassan.todo_list

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasir_hassan.todo_list.ui.theme.ToDo_ListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDo_ListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainPage(
                        //name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(modifier: Modifier = Modifier) {

    // create context to read data
    val myContext = LocalContext.current

    // variable to observe the value of the textField when the value changes
    val todoName = remember {
        mutableStateOf("")
    }

    // to change the focus state of the textField, use FocusManager Interface
    // Create an instance of the FocusManager interface
    val focusManager = LocalFocusManager.current

    // variable to observe the state of the dailog window
    val deleteDialogStatus = remember {
        mutableStateOf(false)
    }

    val clickedItemIndex = remember {
        mutableStateOf(0)
    }

    // create a list for the item function
    val itemList = readData(myContext) // read saved data from the local file and transfer to the itemList

    // The main layout is a column
    Column(modifier = Modifier.fillMaxSize()) {
        // add a top row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // add a texfield for the top tow
            TextField(
                value = todoName.value,
                onValueChange = {
                    todoName.value = it
                },
                label = { Text(text = "Enter Todo") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Green,
                    unfocusedLabelColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                    .weight(7F) // 3F, parts, for the button weight
                    .height(60.dp), // the height of the text field
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Button(
                // adding data to the list
                onClick = {
                    // check if data entered in the text field
                    if(todoName.value.isNotEmpty()){
                        itemList.add(todoName.value)
                        // write the entered data to the local file
                        writeData(itemList, myContext)
                        // clear the textField
                        todoName.value = ""
                        // clear the focus state of the textField
                        focusManager.clearFocus()
                    }else{
                        //show toast message to the user
                        Toast.makeText(myContext, "Please, enter Todo", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier
                    .weight(3F)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(text = "Add", fontSize = 20.sp)
            }
        }

        // create Lazy column for the list items
        LazyColumn() {

            // add item function
            items(
                count = itemList.size,
                itemContent = { index -> // index is the item index
                    val item = itemList[index]
                    // create a card for each item
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        // remove color of the card
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        // add row to hold text for the icon, to cover the entire card horizontally
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // add a text component in the row
                            Text(
                                text = item, color = Color.White, fontSize = 18.sp, maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(300.dp)
                            )
                            // create a row for the icon
                            Row() {
                                // add an icon button
                                IconButton(onClick = {}) {
                                    Icon(
                                        Icons.Filled.Edit, contentDescription = "edit",
                                        tint = Color.White
                                    )
                                }
                                IconButton(onClick = {
                                    deleteDialogStatus.value = true
                                    clickedItemIndex.value = index
                                }) {
                                    Icon(
                                        Icons.Filled.Delete, contentDescription = "delete",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            )


        }

        // create a dialog message
        if(deleteDialogStatus.value){
            // call the AlertDialog composable function
            AlertDialog(onDismissRequest = {deleteDialogStatus.value = false},
                title = {
                    Text(text = "Delete")
                },
                text = {
                    Text(text = "Do you want to delete this item from the list?")
                },
                // add the confirm button
                confirmButton = {
                    TextButton(onClick = {
                        // remove the selected item from the list
                        itemList.removeAt(clickedItemIndex.value)
                        // write the last version of the list to the file
                        writeData(itemList, myContext)
                        // close the dialog after the use click yes button
                        deleteDialogStatus.value = false
                        // show toast message to the user
                        Toast.makeText(myContext, "Item is removed from the list.", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                        // specify the text of the confirm button
                        Text(text = "Yes")
                    }
                },
                // add the dismiss button parameter
                dismissButton = {
                    TextButton(onClick = {deleteDialogStatus.value = false}) {
                        Text(text = "No")
                    }
                }
            )
        }

    }
}


