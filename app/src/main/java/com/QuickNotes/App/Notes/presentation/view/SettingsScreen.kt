package com.QuickNotes.App.Notes.presentation.view

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Color.Sphere.Challenge.gamecolor.data.Prefs
import com.Color.Sphere.Challenge.gamecolor.data.SoundManager
import com.QuickNotes.App.Notes.R
import com.QuickNotes.App.Notes.presentation.navigation.OutlinedText

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var musicVolume by remember {
        mutableFloatStateOf(
            sharedPreferences.getFloat(
                "musicVolume",
                0.5f
            )
        )
    }

    LaunchedEffect(musicVolume) {
        Prefs.music = musicVolume
        SoundManager.setVolumeMusic()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .size(300.dp, 300.dp) // Размер блока настроек
                .background(Color(0xFFF5EDE0), shape = RoundedCornerShape(24.dp))
                //.shadow(8.dp, shape = RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    text = "Music",
                    outlineColor = Color.Gray,
                    fillColor = Color.White,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                // Текст "Music"


                // Слайдер громкости
                Slider(
                    value = musicVolume,
                    onValueChange = { newValue ->
                        musicVolume = newValue
                        sharedPreferences.edit().putFloat("musicVolume", newValue).apply()
                    },
                    valueRange = 0f..1f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFFAD9D7),
                        activeTrackColor = Color(0xFF94E458),
                        inactiveTrackColor = Color(0xFFFAD9D7)
                    ),
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Кнопка "Back"
                Button(
                    onClick = onBack,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC1E3CC)),
                    modifier = Modifier.shadow(0.dp, shape = RoundedCornerShape(24.dp))
                ) {
                    OutlinedText(
                        text = "Back",
                        outlineColor = Color.Gray,
                        fillColor = Color.White,
                        fontSize = 24.sp,

                        )

                }
            }
        }
    }
}
