package com.metelev.bos.znobi.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.metelev.bos.znobi.data.WeatherModule
import com.metelev.bos.znobi.ui.theme.PingCool

@Composable
fun ListItem(item: WeatherModule) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        elevation = 0.dp,
        backgroundColor = PingCool,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = item.time)
                Text(text = item.condition)
            }
            Text(
                text = item.currentTemp.ifEmpty { "${item.maxTemp}/${item.minTemp}" },
                style = TextStyle(fontSize = 26.sp),
            )
            AsyncImage(
                model = "https:${item.icon}",
                contentDescription = "weatherImageList",
                modifier = Modifier.size(55.dp)
            )
        }
    }
}