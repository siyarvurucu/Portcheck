
package com.example.portcheck

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.portcheck.ui.theme.PortcheckTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.net.URL
import javax.xml.parsers.SAXParserFactory


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ports = listOf(162, 1023, 1024)
        var result = ""
        CoroutineScope(IO).launch{
            for (i in ports.indices) {
                try {
                    Log.i("CREATION", "trying to create socket for port " + ports[i].toString())
                    val socket = ServerSocket(ports[i])
                    Log.i("CREATION", "socket created for port " + ports[i].toString())
                    socket.setSoTimeout(2000)
                    result = result + ports[i].toString() + " OK\n"
                } catch (e: Exception) {
                    result = result + ports[i].toString() + " NOT OK\n"
                }
            }

        }
        setContent {
            PortcheckTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting(result)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
            text = "$name",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    PortcheckTheme {
        Greeting("B")
    }
}
