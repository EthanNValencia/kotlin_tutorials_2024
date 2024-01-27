package com.nt.shoppinglistapp_007

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ShoppingItem(val id: Int, var name: String, var quantity: Int, var isEditing: Boolean = false)

@Composable
fun ShoppingList() {
    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialogue by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var idCounter by remember { mutableIntStateOf(0) }

    fun switchDialogue() {
        showDialogue = !showDialogue
    }

    fun addNewItem() {
        if(name.isNotBlank() && quantity.isNotBlank()) {
            idCounter+=1
            val item = ShoppingItem(idCounter, name, quantity.toInt())
            shoppingItems = shoppingItems + item
            switchDialogue()
        }
    }

    fun checkQuantityInput(input: String) {
        if(input.toIntOrNull() != null || input.isBlank()) {
            quantity = input
        }
    }

    fun deleteItem() {

    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Button(onClick = { switchDialogue() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {

            Text("Add Item(${shoppingItems.size})")
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            items(shoppingItems) { item ->
                if(item.isEditing) {
                    ShoppingItemEditor(
                        item = item,
                        onSave = {
                            editedName, editedQuantity ->
                            shoppingItems = shoppingItems.map { it.copy(isEditing = false) }
                            var editedItem = shoppingItems.find { it.id == item.id }
                            editedItem?.let {
                                it.name = editedName
                                it.quantity = editedQuantity }},
                        onCancel = {})
                } else {
                    ShoppingListItem(item, {
                        shoppingItems = shoppingItems.map { it.copy(isEditing = it.id == item.id ) } },
                        { shoppingItems = shoppingItems.filter{ it.id != item.id } })
                }
            }
        }
    }
    if(showDialogue) {
        AlertDialog(onDismissRequest = {
            showDialogue = false },
            confirmButton = { Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { addNewItem() }) {
                    Text(text = "Add")
                }
                Button(onClick = { showDialogue = false }) {
                    Text(text = "Cancel")
                }
            } },
            title = { Text("Add Shopping Item") },
            text = {
                Column {
                    OutlinedTextField(value = name, onValueChange = { name = it }, singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), label = {Text("Name of Item")})
                    OutlinedTextField(value = quantity, onValueChange = {checkQuantityInput(it) }, singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), label = { Text("Quantity") })
                }
            })
    }
}

@Composable
fun ShoppingItemEditor(item: ShoppingItem, onSave: (String, Int) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    fun checkQuantityInput(input: String) {
        if(input.toIntOrNull() != null || input.isBlank()) {
            quantity = input
        }
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Column {
            BasicTextField(value = name, onValueChange = { name = it }, singleLine = true , modifier = Modifier
                .wrapContentSize()
                .padding(8.dp))
            BasicTextField(value = quantity, onValueChange = {checkQuantityInput(it) }, singleLine = true , modifier = Modifier
                .wrapContentSize()
                .padding(8.dp))
        }
        Button(onClick = { onSave(name, quantity.toIntOrNull() ?: 1) }) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(2.dp, Color(0XFF018786)), shape = RoundedCornerShape(20))) {
        Text(text = "id: ${item.id}", modifier = Modifier.padding(8.dp))
        Text(text = "${item.name}", modifier = Modifier.padding(8.dp))
        Text(text = "Qty:${item.quantity}", modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(2.dp)) {
            IconButton(onClick = { onEditClick() }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = { onDeleteClick() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}