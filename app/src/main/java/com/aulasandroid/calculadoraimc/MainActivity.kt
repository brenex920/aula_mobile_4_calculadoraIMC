package com.aulasandroid.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aulasandroid.calculadoraimc.ui.theme.CalculadoraIMCTheme
import java.util.function.ToDoubleFunction
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculadoraIMC(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
@Composable
fun calcularIMC(altura: Double,pesso: Double): Double{
    return (pesso / altura/ 100).pow(2.0)
}
@Composable
fun CalculadoraIMC(
    modifier: Modifier = Modifier
) {
    var altura by remember {
        mutableStateOf("")
    }
    var alturaD = altura.toDoubleOrNull() ?:0.0
    var pesso by remember {
        mutableStateOf("")
    }
    var pessoD = pesso.toDoubleOrNull() ?:0.0
    var imc by remember {
        mutableStateOf(0.0)
    }
    // Dentro do seu Composable CalculadoraIMC
    var classificacao by remember { mutableStateOf("") }
    var corCard by remember { mutableStateOf(Color.Gray) } // Cor inicial
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //header
        Column(modifier = Modifier.fillMaxWidth()
            .height(160.dp)
            .background(color = colorResource(R.color.cor_app)),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "imc logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(vertical = 16.dp)

            )
            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth()
//                    .size(400.dp)
                    .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                elevation = CardDefaults.cardElevation(4.dp)

                // circular
//                elevation = CardDefaults.cardElevation(4.dp),
//                shape = CircleShape,
//                border = BorderStroke(width = 4.dp, Color.Black)

            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .height(400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "seus dados",
                        fontSize = 24.sp,
                        color = colorResource(R.color.cor_app),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = "$altura",
                        onValueChange = { novoValor ->
                            altura = novoValor
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(text = "Digite sua Altura")
                        },
                        label = {
                            Text("Altura")
                        },
                        shape = RoundedCornerShape(
                          40
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = "$pesso",
                        onValueChange = { novoValor ->
                            pesso = novoValor
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(text = "Digite sua Pesso")
                        },
                        label = {
                            Text("Pesso")
                        },
                        shape = RoundedCornerShape(
                         40
                        ),
                    )
                        Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = {
                            if (alturaD > 0) {
                                val alturaMetros = if (alturaD > 3.0) alturaD / 100 else alturaD
                                imc = pessoD / (alturaMetros * alturaMetros)

                                // Lógica baseada na sua imagem
                                when {
                                    imc < 18.5 -> {
                                        classificacao = "Abaixo do peso"
                                        corCard = Color.Red
                                    }
                                    imc >= 18.5 && imc < 25.0 -> {
                                        classificacao = "Peso ideal"
                                        corCard = Color.Green
                                    }
                                    imc >= 25.0 && imc < 30.0 -> {
                                        classificacao = "Levemente acima do peso"
                                        corCard = Color(0xFFFFA500) // Laranja
                                    }
                                    imc >= 30.0 && imc < 35.0 -> {
                                        classificacao = "Obesidade grau I"
                                        corCard = Color.Red
                                    }
                                    imc >= 35.0 && imc < 40.0 -> {
                                        classificacao = "Obesidade grau II"
                                        corCard = Color.Red
                                    }
                                    else -> {
                                        classificacao = "Obesidade grau III"
                                        corCard = Color.Red
                                    }
                                }
                            }
                        },
                        modifier = Modifier.width(300.dp)
                            .height(64.dp),
                        shape = RoundedCornerShape(100.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.cor_app))



                    ) {
                        Text(
                            text = "CALCULAR",
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = {
                            altura = ""
                            pesso = ""
                            imc = 0.0
                            classificacao = ""
                            corCard = Color.Gray
                        },
                        modifier = Modifier.width(300.dp).height(48.dp),
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(1.dp, colorResource(R.color.cor_app))
                    ) {
                        Text(text = "LIMPAR", color = colorResource(R.color.cor_app))
                    }
                }

            }

        }
        //form
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)){
            Spacer(modifier = Modifier.height(30.dp))

            // É EXATAMENTE AQUI (Substitua o Card antigo por este novo)
            Card(
                modifier = Modifier.fillMaxWidth()
                    .height(120.dp)
                    .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(
                    containerColor = corCard
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "%.1f".format(imc),
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = classificacao,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        //result
    }

}

