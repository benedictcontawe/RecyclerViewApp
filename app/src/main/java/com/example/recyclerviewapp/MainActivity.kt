package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.recyclerviewapp.ui.theme.RecyclerViewAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
        public fun newIntent(context : Context) : Intent = Intent(context.applicationContext, MainActivity::class.java)
    }

    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
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
                            itemContent = { model -> when (model) {
                                is IconModel -> IconCellComposable(model)
                                is NameModel -> NameCellComposable(model)
                            } }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ImageComposable(id : Int) {
        val androidDrawable = painterResource(id = id)
        Icon (
            contentDescription = null,
            painter  = androidDrawable
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun IconCellComposable(model : IconModel) {
        Card (
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .combinedClickable(
                    enabled = true,
                    onClick = { onClick(model.getName()) },
                    onLongClick = {
                        startActivity(DetailActivity.newIntent(getBaseContext(), model = model))
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
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Box (
                    modifier = Modifier.weight(0.20f),
                    contentAlignment = Alignment.Center
                ) {
                    ImageComposable(id= model.icon)
                }
                Box (
                    modifier = Modifier.weight(0.80f),
                    contentAlignment = Alignment.Center
                ) {
                    Column ( modifier = Modifier.padding(16.dp).fillMaxWidth() ) {
                        Text (
                            text = model.getName(),
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

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun NameCellComposable(model : NameModel) {
        Card (
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .combinedClickable(
                    enabled = true,
                    onClick = { onClick(model.getName()) },
                    onLongClick = {
                        startActivity(DetailActivity.newIntent(getBaseContext(), model = model))
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
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Box ( modifier = Modifier.weight(0.20f), )
                Box (
                    modifier = Modifier.weight(0.80f),
                    contentAlignment = Alignment.Center
                ) {
                    Column ( modifier = Modifier.padding(16.dp).fillMaxWidth() ) {
                        Text (
                            text = model.getName(),
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

    private fun onClick(name : String) {
        Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show()
        //TODO: Snackbar on going
    }
}