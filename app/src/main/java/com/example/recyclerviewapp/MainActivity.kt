package com.example.recyclerviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recyclerviewapp.ui.theme.RecyclerViewAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel : MainViewModel = viewModel { MainViewModel(getApplication()) }
            val list by viewModel.getLiveList().observeAsState(listOf<CustomModel>())
            RecyclerViewAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn (
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items (
                            items = list,
                            itemContent = { model ->
                                CellComposable(model)
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ImageComposable() {
        val androidDrawable = painterResource(id = R.drawable.ic_launcher_foreground)
        Icon (
            painter  = androidDrawable,
            contentDescription = null
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CellComposable(model : CustomModel) {
        Card (
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth().combinedClickable (
                enabled = true,
                onClick = {

                }, onLongClick = {
                    startActivity( DetailActivity.newIntent(getBaseContext(), model = model) )
                }
            ),
            elevation = CardDefaults.cardElevation (
                defaultElevation = 3.dp,
                pressedElevation = 0.dp,
                focusedElevation = 1.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp
            ),
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Row {
                ImageComposable()
                Column (
                    modifier = Modifier.padding(16.dp).fillMaxWidth().align(Alignment.CenterVertically)
                ) {
                    Text (
                        text = model.name,
                        style = typography.labelLarge
                    )
                    Text (
                        text = stringResource(id = R.string.hold_tap_to_view_detail),
                        style = typography.bodySmall
                    )
                }
            }
        }
    }
}