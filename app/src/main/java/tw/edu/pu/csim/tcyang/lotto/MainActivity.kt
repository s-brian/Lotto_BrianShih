package tw.edu.pu.csim.tcyang.lotto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import tw.edu.pu.csim.tcyang.lotto.ui.theme.LottoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Play(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Play(modifier: Modifier = Modifier) {
    var lucky by remember { mutableStateOf((1..100).random()) }
    var touchPosition by remember { mutableStateOf(Offset.Zero) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable{
                Toast.makeText(context, "this is a text", Toast.LENGTH_SHORT).show()
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        val position = event.changes.firstOrNull()?.position
                        if (position != null) {
                            touchPosition = position
                        }
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "樂透數字(1-100)為 $lucky",
            modifier = Modifier.pointerInput(Unit){
                detectTapGestures(
                    onTap = {
                        lucky += 1
                        Toast.makeText(context, "+1", Toast.LENGTH_SHORT).show()
                    },
                    onLongPress = {
                        lucky -= 1
                        Toast.makeText(context, "-1", Toast.LENGTH_SHORT).show()
                    }
                )
            })


        Button(onClick = { lucky = (1..100).random() }) {
            Text("重新產生樂透碼")
        }

        Text(text = "X = ${touchPosition.x.toInt()}, Y = ${touchPosition.y.toInt()}")
    }
}
