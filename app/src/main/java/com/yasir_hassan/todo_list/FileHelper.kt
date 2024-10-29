package com.yasir_hassan.todo_list

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

// file of list to be saved on the device memory
const val FILE_NAME = "todolist.dat" // or txt

// SnapshotStateList<> is used for saving and retrieving list items
// SnapshotStateList<> is an implementation of mutableList than can be oberved in
// Snapshot, which is the result created by mutableStateListOf.
// this class implements the same semantic as arrayList.
// using FileOutputStream class for saving the data
fun writeData(items: SnapshotStateList<String>, context: Context){
    // write data to file
    val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE) // reached within the application
    val oos = ObjectOutputStream(fos) //
    // convert the snapshotStateList to an arrayList before saving
    val itemList = ArrayList<String>() // create the arrayList
    itemList.addAll(items) // convert the snapshotStateList to ArrayList
    // write the arrayList to the file
    oos.writeObject(itemList)

    oos.close() // close the file in the device memory

}

// function to read data which return a snapshotStateList of String
// Using FileInputStream class for reading the data
fun readData(context: Context): SnapshotStateList<String>{
    // transfer the data to an array list
    var itemList: ArrayList<String>
    val fis = context.openFileInput(FILE_NAME)
    val ois = ObjectInputStream(fis)
    itemList = ois.readObject() as ArrayList<String>  // cast data from object to string

    // Conver the itemLIst to SnapshotStateList to be returned by the function
    val items = SnapshotStateList<String>() // create SSSL
    // convert the arrayList to SSSL
    items.addAll(itemList)
    return items
}


