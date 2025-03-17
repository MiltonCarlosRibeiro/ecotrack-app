package br.com.fiap.ecotrack_app.components

import android.graphics.Color
import android.graphics.Typeface
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CaloriasChart(caloriasFlow: Flow<Map<String, Int>>) {
    val context = LocalContext.current
    var caloriasData by remember { mutableStateOf(emptyList<Pair<String, Int>>()) }

    // 📌 Coletar os dados de calorias
    LaunchedEffect(Unit) {
        caloriasFlow.collectLatest { data ->
            caloriasData = data.toList()
        }
    }

    // 📌 Exibir o gráfico usando AndroidView
    AndroidView(
        modifier = Modifier.fillMaxWidth().height(300.dp), // 🔹 Aumentando o tamanho do gráfico
        factory = { ctx ->
            BarChart(ctx).apply {
                description.isEnabled = false  // 🔹 Removendo descrição para não poluir
                setFitBars(true)
                setTouchEnabled(true)
                setPinchZoom(true)
                setDrawGridBackground(false)
                legend.isEnabled = true

                // 📌 Melhorando os eixos do gráfico
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                    textSize = 12f
                    typeface = Typeface.DEFAULT_BOLD
                }

                axisLeft.apply {
                    setDrawGridLines(true)
                    axisMinimum = 0f // 🔹 Garantir que o mínimo é zero
                    textSize = 12f
                }

                axisRight.isEnabled = false  // 🔹 Removendo eixo direito desnecessário
            }
        },
        update = { chart ->
            if (caloriasData.isNotEmpty()) {
                val entries = caloriasData.mapIndexed { index, pair ->
                    BarEntry(index.toFloat(), pair.second.toFloat())
                }

                val dataSet = BarDataSet(entries, "Calorias Consumidas").apply {
                    colors = listOf(
                        ContextCompat.getColor(context, android.R.color.holo_blue_light),
                        ContextCompat.getColor(context, android.R.color.holo_orange_light),
                        ContextCompat.getColor(context, android.R.color.holo_red_light)
                    )
                    valueTextSize = 14f
                    valueTypeface = Typeface.DEFAULT_BOLD
                }

                val barData = BarData(dataSet).apply {
                    barWidth = 0.4f  // 🔹 Ajustando largura das barras
                }

                chart.data = barData
                chart.xAxis.valueFormatter = IndexAxisValueFormatter(caloriasData.map { it.first }) // 🔹 Definir labels no eixo X
                chart.invalidate() // 🔹 Atualizar o gráfico
            }
        }
    )
}
