package com.bignerdranch.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.ApiInterface
import api.RetrofitClient
import com.bignerdranch.android.myapplication.ui.theme.MyApplicationTheme
import models.Schedule
import models.ScheduleResponse

class ScheduleActivity : AppCompatActivity() {
    private val retrofit = RetrofitClient.getInstance()
    val apiInterface = retrofit.create(ApiInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var schedule by remember { mutableStateOf<List<Schedule>>(emptyList()) }
            LaunchedEffect(Unit) {
                val scheduleResponse: ScheduleResponse = apiInterface.getSchedule(1)
                schedule = scheduleResponse.data
            }
            MyApplicationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScheduleScreen(scheduleItems = schedule)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(scheduleItems: List<Schedule>) {
    var selectedDayOfWeek by remember { mutableStateOf(0) } // Состояние выбранного дня недели

    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ) {
        Text(
            text = "Расписание",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            DayOfWeekButton(
                text = "ПН",
                selected = selectedDayOfWeek == 1,
                onClick = { selectedDayOfWeek = 1 }
            )
            DayOfWeekButton(
                text = "ВТ",
                selected = selectedDayOfWeek == 2,
                onClick = { selectedDayOfWeek = 2 }
            )
            DayOfWeekButton(
                text = "СР",
                selected = selectedDayOfWeek == 3,
                onClick = { selectedDayOfWeek = 3 }
            )
            DayOfWeekButton(
                text = "ЧТ",
                selected = selectedDayOfWeek == 4,
                onClick = { selectedDayOfWeek = 4 }
            )
            DayOfWeekButton(
                text = "ПТ",
                selected = selectedDayOfWeek == 5,
                onClick = { selectedDayOfWeek = 5 }
            )
            DayOfWeekButton(
                text = "СБ",
                selected = selectedDayOfWeek == 6,
                onClick = { selectedDayOfWeek = 6 }
            )
        }
        val filteredScheduleItems = scheduleItems.filter { item ->
            item.Weekday == selectedDayOfWeek
        }

        if (filteredScheduleItems.isNotEmpty()) {
            LazyColumn {
                items(filteredScheduleItems) { item ->
                    ScheduleItemCard(item)
                }
            }
        } else {
            Text(text = "Расписание пусто")
        }
    }
}
@Composable
fun DayOfWeekButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(end = 6.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color =if (selected) Color.Blue else Color.Red
            )
        )
    }
}
@ExperimentalMaterial3Api
@Composable
fun ScheduleItemCard(scheduleItem: Schedule) {
    Card(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.group),
                    contentDescription = null,
                    modifier = Modifier
                        .semantics { testTag = "favorite" }
                        .size(24.dp)
                )
                Text(
                    text = scheduleItem.GroupName,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.account),
                    contentDescription = null,
                    modifier = Modifier
                        .semantics { testTag = "favorite" }
                        .size(24.dp)
                )
                Text(
                    text = "Преподаватель: " + scheduleItem.Teacher,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.book),
                    contentDescription = null,
                    modifier = Modifier
                        .semantics { testTag = "favorite" }
                        .size(24.dp)
                )
                Text(
                    text = "Предмет: " + scheduleItem.Subject,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}