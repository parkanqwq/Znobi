package com.metelev.bos.znobi

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.metelev.bos.znobi.data.WeatherModule
import com.metelev.bos.znobi.ui.main.MainCard
import com.metelev.bos.znobi.ui.main.TabLayout
import com.metelev.bos.znobi.ui.theme.ZnobiTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZnobiTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModule>())
                }
                val currentDay = remember {
                    mutableStateOf(WeatherModule(
                        "", "","","","","","","",
                    ))
                }
                getData("Moscow", this, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.anime_logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.Crop
                )
                Column {
                    MainCard(currentDay)
                    TabLayout(daysList)
                }
            }
        }
    }
}

private fun getData(
    city: String,
    context: Context,
    daysList: MutableState<List<WeatherModule>>,
    currentDay: MutableState<WeatherModule>
) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            "9bec3e91ab0545f184f175216222511" +
            "&q=$city" +
            "&days=" +
            "3" +
            "&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val list = getWeatherByDay(response)
            daysList.value = list
            currentDay.value = list[0]
        },
        {
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
        }
    )
    queue.add(sRequest)
}

private fun getWeatherByDay(response: String): List<WeatherModule> {
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherModule>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherModule(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                item.getJSONArray("hour").toString()
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c")
    )
    return list
}