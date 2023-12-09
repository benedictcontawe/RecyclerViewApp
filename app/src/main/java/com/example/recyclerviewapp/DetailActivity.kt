package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DetailActivity : ComponentActivity() {

    companion object {
        private val TAG : String = DetailActivity::class.java.getSimpleName()
        private const val CUSTOM_ID = "custom_id"
        fun newIntent(context : Context, model : CustomModel) : Intent {
            return Intent(context, DetailActivity::class.java).putExtra(CUSTOM_ID, model)
        }
    }

    private val model : CustomModel by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            getIntent()?.getSerializableExtra(CUSTOM_ID, CustomModel::class.java) as CustomModel
        else getIntent()?.getSerializableExtra(CUSTOM_ID) as CustomModel
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollState = rememberScrollState()
            BoxWithConstraints {
                Column (
                    modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
                ) {
                    HeaderComposable(this@BoxWithConstraints.maxHeight)
                    ContentComposable()
                }
            }
        }
    }

    @Composable
    private fun HeaderComposable (containerHeight : Dp) {
        val androidDrawable = painterResource(id = R.drawable.ic_launcher_foreground)
        Image (
            modifier = Modifier.heightIn(max = containerHeight / 2).fillMaxWidth(),
            painter  = androidDrawable,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }

    @Composable
    private fun ContentComposable() {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Text (
                fontWeight = FontWeight.Light,
                modifier = Modifier.height(13.dp),
                style = MaterialTheme.typography.labelSmall,
                text = stringResource(id = R.string.name),
            )
            Divider(modifier = Modifier.padding(bottom = 4.dp))
            Text (
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.height(24.dp),
                style = MaterialTheme.typography.labelLarge,
                text = model.name,
            )
            Divider(modifier = Modifier.padding(bottom = 4.dp))
            Text (
                fontWeight = FontWeight.Light,
                modifier = Modifier.height(13.dp),
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(id = R.string.details),
            )
            Text (
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.height(24.dp),
                overflow = TextOverflow.Visible,
                style = MaterialTheme.typography.bodyLarge,
                text = model.detail,
                textAlign = TextAlign.Center
            )
        }
    }
}