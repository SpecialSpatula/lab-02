package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Creates new city repository, basically initializes it Want to do this before setcontent as set content may need some of this
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = {cityRepository.addCity(it)},
                        onRemoveCity = {cityRepository.removeCity(it)},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}

class CityRepository{
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow", "Sydney",
        "Berlin", "Vienna", "Tokyo", "Beijing",
        "Osaka", "New Delhi"    )
    val cities: List<String> get() = _cities
    fun addCity(city: String){
        _cities.add(city)
    }
    fun removeCity(selectedCity: String){
        _cities.remove(selectedCity)
    }
}

@Composable
fun CityListScreen(
  cities: List<String>,
  onAddCity: (String) -> Unit,
  onRemoveCity: (String) -> Unit,
  modifier:  Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf(value = "") }
    var selectedCity by remember { mutableStateOf(value = "")}
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.padding( all = 16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = {newCityName = it},
                label = {Text("City name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()){
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Add City")
            }
        }
        Row(modifier = modifier.padding( all = 16.dp)) {

            Spacer(modifier = Modifier.width(8.dp))


        }

        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(cities) { city ->
               CityRow(city = city)
                Button(
                    onClick = {
                        val selectedCity = city
                        onRemoveCity(city)
                    }
                ){
                    Text ("Remove City")
                }
            }
        }
    }
}


@Composable
fun CityRow(city: String){


        Text(
            text = city,
            fontSize = 28.sp,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 14.dp)
        )


}


