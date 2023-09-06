package com.mkrdeveloper.swipetodeletelazycolumnjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mkrdeveloper.swipetodeletelazycolumnjetpack.ui.theme.SwipeToDeleteLazyColumnJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeToDeleteLazyColumnJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val list = remember {
        mutableStateListOf(
            "Subscribe",
            "Like",
            "Share",
            "Comment",
            "MkrDeveloper"
        )
    }
    LazyColumn(
        state= rememberLazyListState(),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(items = list, key = { _, listItem ->
            listItem.hashCode()
        }) { index, item ->

           val state = rememberDismissState(
               confirmValueChange = {
                   if (it == DismissValue.DismissedToStart){
                       list.remove(item)
                   }
                   true
               }
           ) 

            SwipeToDismiss(

                state = state,
                background = {
                             val color = when(state.dismissDirection){
                                 DismissDirection.EndToStart-> Color.Red
                                     DismissDirection.StartToEnd-> Color.Green
                                     null-> Color.Transparent
                             }
                    Box(modifier = Modifier.fillMaxSize()
                        .background(color)
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                    ){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete",
                            modifier = Modifier.align(Alignment.CenterEnd))
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "edit",
                            modifier = Modifier.align(Alignment.CenterStart))
                    }
                    
                },
                dismissContent = {
                ItemUI(list = list, itemIndex = index)
            })

        }
    }
}

@Composable
fun ItemUI(modifier: Modifier = Modifier, list: List<String>, itemIndex: Int) {
    Card(
        modifier.padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        Box(
            modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(text = list[itemIndex], fontSize = 32.sp, fontWeight = FontWeight.Bold)

        }
    }
}