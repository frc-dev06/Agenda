package com.example.agenda.inside

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agenda.R

class CalcularPromedio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calcular_promedio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorteN1= findViewById<EditText>(R.id.txtCorteN1)
        val txtCorteN2= findViewById<EditText>(R.id.txtCorteN2)
        val txtCorteN3= findViewById<EditText>(R.id.txtCorteN3)

        val txtCorteP1= findViewById<EditText>(R.id.txtCorteP1)
        val txtCorteP2= findViewById<EditText>(R.id.txtCorteP2)
        val txtCorteP3= findViewById<EditText>(R.id.txtCorteP3)

        val btnCalcularPromedio= findViewById<Button>(R.id.btnCalcularPromedio)
        val btnBackToPromedio= findViewById<ImageButton>(R.id.btnBackToPromedio)

        val txtPromedio= findViewById<TextView>(R.id.txtPromedio)
        val txtRespuesta= findViewById<TextView>(R.id.txtRespuesta)

        btnCalcularPromedio.setOnClickListener {
            calcularPromedio(txtCorteN1, txtCorteN2, txtCorteN3,
                             txtCorteP1, txtCorteP2, txtCorteP3,
                             txtPromedio, txtRespuesta)
        }
        btnBackToPromedio.setOnClickListener {
            irAtras()
        }
    }

    fun calcularPromedio(txtN1: EditText,txtN2: EditText,txtN3: EditText,
                         txtP1: EditText, txtP2: EditText, txtP3: EditText,
                         txtProm: TextView, txtResp: TextView ){

        if (!txtN1.text.isEmpty() && !txtN2.text.isEmpty() && !txtN3.text.isEmpty()){
            val n1= txtN1.text.toString().toFloat()
            val n2= txtN2.text.toString().toFloat()
            val n3= txtN3.text.toString().toFloat()

            if (!txtP1.text.isEmpty() && !txtP2.text.isEmpty() && !txtP3.text.isEmpty() ){
                val p1= txtP1.text.toString().toInt()
                val p2= txtP2.text.toString().toInt()
                val p3= txtP3.text.toString().toInt()
                val porcentaje= (p1+p2+p3)
                if (porcentaje==100){
                    val corte1= n1*(p1/100f)
                    val corte2= n2*(p2/100f)
                    val corte3= n3*(p3/100f)

                    val promedioFinal=(corte1+corte2+corte3)
                    txtProm.setText(promedioFinal.toString())

                    when (promedioFinal) {
                        in 0.0..29.9 -> {txtResp.setText("El próximo semestre será")
                                               txtResp.setTextColor(Color.parseColor("#ff0000")) }
                        in 30.0..39.9 -> {txtResp.setText("Aún puedes esforzarte más")
                                                txtResp.setTextColor(Color.parseColor("#0000ff"))}
                        in 40.0..50.0 -> {txtResp.setText("Excelente trabajo")
                                                txtResp.setTextColor(Color.parseColor("#00ff00"))}
                        else -> txtResp.setText("Las notas ingresadas no parecen estar bien")
                    }

                }else{
                    Toast.makeText(this, "Ajusta los porcentajes", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Diligencia todos los porcentajes", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Diligencia todas las notas", Toast.LENGTH_SHORT).show()
        }
    }

    fun irAtras() {
        val intent= Intent(this, Inicio::class.java)
        startActivity(intent)
        this.finish()
    }
}