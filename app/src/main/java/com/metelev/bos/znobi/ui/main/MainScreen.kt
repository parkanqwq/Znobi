package com.metelev.bos.znobi.ui.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.accompanist.pager.*
import com.metelev.bos.znobi.R
import com.metelev.bos.znobi.data.WeatherModule
import com.metelev.bos.znobi.ui.theme.PingCool
import kotlinx.coroutines.launch

@Composable
fun MainCard(currentDay: MutableState<WeatherModule>) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = PingCool,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = currentDay.value.time,
                            style = TextStyle(fontSize = 15.sp),
                            color = Color.Black
                        )
                        AsyncImage(
                            model = "https:${currentDay.value.icon}",
                            contentDescription = "weatherImage",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                    Text(
                        text = currentDay.value.city,
                        style = TextStyle(fontSize = 34.sp),
                        color = Color.Black
                    )
                    Text(
                        text = "${currentDay.value.currentTemp}°C",
                        style = TextStyle(fontSize = 64.sp),
                        color = Color.Black
                    )
                    Text(
                        text = currentDay.value.condition,
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "btnSearch",
                                tint = Color.Black
                            )
                        }
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "${currentDay.value.maxTemp}°C/${currentDay.value.minTemp}°C",
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.Black
                        )
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cloud_sync),
                                contentDescription = "btnSync",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherModule>>){
    val tabList = listOf("HOURS", "DAYS ")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = {
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, it),
                            color = Color.White
                        )
            },
            backgroundColor = PingCool
        ) {
            tabList.forEachIndexed{index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }, text = {
                        Text(text = text)
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) {
                index ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    daysList.value
                ) { _, item ->
                    ListItem(item)
                }
            }
        }
    }
}

