package com.example.randomapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.randomapp.DatabaseImpl.PlayThrough
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Headers
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val API_KEY = "551a9ead528849e0b387d1655d278a9e"
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameInput = findViewById<EditText>(R.id.playerName)
        val numberInput = findViewById<EditText>(R.id.numberOfItems)


        val gameButton = findViewById<Button>(R.id.enterButton)
        val leaderBoardButton = findViewById<Button>(R.id.leaderboardButton)

        gameButton.setOnClickListener {
            try {
                val playerName: String = nameInput.text.toString()
                val listSize: Int = Integer.parseInt(numberInput.text.toString())
                if (listSize in 1..10 && playerName.isNotBlank()) {
                    var gameList: ArrayList<String>
                    lifecycleScope.launch {
                        gameList = getRandomListOfNames(listSize)
                        val playThrough = PlayThrough(name = playerName, pairsAttempted = listSize, entryNo = 0, guessesUsed = 0, weightedScore = 0.0)
                        val intent = Intent(this@MainActivity, GameActivity::class.java)
                        intent.putExtra("DataList", gameList)
                        intent.putExtra("playThrough", playThrough)
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(this, "Need a name and input 1 - 10", Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Need a number 1 - 10", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Wrong Number Inputted")
            }
        }

        leaderBoardButton.setOnClickListener {
            val intent = Intent(this@MainActivity, LeaderboardActivity::class.java)
            startActivity(intent)
        }


    }

    private suspend fun getRandomListOfNames(numberOfItems: Int): ArrayList<String> {
        val urlString = "https://randommer.io/api/Name?nameType=firstName&quantity=$numberOfItems"

        return ArrayList<String>().also {gameList ->
                withContext(Dispatchers.IO) {
                    try {
                        val url: URL = URI(urlString).toURL()
                        val connection = url.openConnection() as HttpURLConnection

                        connection.setRequestProperty("X-Api-Key", API_KEY)
                        connection.setRequestProperty("Content-Type", "application/json")

                        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                            val reader = BufferedReader(InputStreamReader(connection.inputStream)).readText()
                            val nameArray = JSONArray(reader)
                            for (i in 0 until nameArray.length()) {
                                gameList.add(nameArray.getString(i) + " 1")
                                gameList.add(nameArray.getString(i) + " 2")
                            }
                            gameList.shuffle()
                            Log.i(TAG, gameList.toString())
                        } else {
                            Log.e(TAG, "Failed to fetch gameList: ${connection.responseCode}")
                        }
                        connection.disconnect()
                    } catch (ex: Exception) {
                        Log.e(TAG, "Exception Occurred: ${ex.message}")
                    }
            }
        }
    }
}