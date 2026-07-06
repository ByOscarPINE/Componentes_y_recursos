package com.example.componentesyrecursos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.componentesyrecursos.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.BABar)

        // 1. Ocultar Banner Superior (Card 1)
        binding.contenedor.btnSkip.setOnClickListener {
            binding.contenedor.matCartView.visibility = View.GONE
        }

        // 2. Control del Comportamiento de Alineación del FAB
        binding.FABtn.setOnClickListener {
            if (binding.BABar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
                binding.BABar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            } else {
                binding.BABar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }


        // 3. Navigation Icon Menú
        binding.BABar.setNavigationOnClickListener {
            Log.i("Probando componente", "onCreate: NavIcon")
        }

        // 4. Botón Comprar con anclaje al FAB
        binding.contenedor.btnBuy.setOnClickListener {
            Snackbar.make(binding.root, "Comprado", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ir") {
                    Toast.makeText(this, "Historial", Toast.LENGTH_SHORT).show()
                }
                .setAnchorView(binding.FABtn)
                .show()
        }

        // Carga de Imagen Inicial en el Card Formulario
        loadImage()

        // 5. Listener de Foco del Input Email (Cambio a gris claro en foco)
        binding.contenedor.TIETemail.onFocusChangeListener = View.OnFocusChangeListener { _, focused ->
            if (focused) {
                binding.contenedor.TIETemail.setBackgroundColor(Color.parseColor("#E0E0E0"))
            } else {
                binding.contenedor.TIETemail.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        // 6. Validación Dinámica de la URL mediante TextWatcher
        binding.contenedor.TIETurl.addTextChangedListener {
            var errorStr: String? = null
            val currentUrl = binding.contenedor.TIETurl.text.toString()
            when {
                currentUrl.isEmpty() -> {
                    errorStr = "Required"
                }
                URLUtil.isValidUrl(currentUrl) -> {
                    loadImage(currentUrl)
                }
                else -> {
                    errorStr = "URL Invalida"
                }
            }
            binding.contenedor.TILurl.error = errorStr
        }

        // 7. Checkbox para activar/desactivar TextInputLayout de contraseña
        binding.contenedor.CBpass.setOnClickListener {
            binding.contenedor.TILpass.isEnabled = binding.contenedor.CBpass.isChecked
        }

        // 8. Switch para Mostrar/Ocultar el FAB de la pantalla
        binding.contenedor.switch1.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.text = "Ocultar Boton"
                binding.FABtn.show()
            } else {
                button.text = "Mostrar Boton"
                binding.FABtn.hide()
            }
        }

        // 9. Eventos del Chip de Volúmen
        binding.contenedor.Chip1.setOnCheckedChangeListener { chip, _ ->
            Toast.makeText(this, chip.text.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.contenedor.Chip1.setOnCloseIconClickListener {
            binding.contenedor.Chip1.visibility = View.GONE
        }

        // 10. Slider vinculando los datos al Texto del Chip
        binding.contenedor.Slider1.addOnChangeListener { _, value, _ ->
            binding.contenedor.Chip1.text = "Vol: ${value.toInt()}"
        }

        // 11. Cambiar dinámicamente el color de fondo del Formulario mediante el ButtonGroup
        binding.contenedor.MTcolores.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.contenedor.BtnRojo.id -> {
                        binding.contenedor.matCardForm.setCardBackgroundColor(Color.parseColor("#FFEBEE")) // Rojo Pastel
                    }
                    binding.contenedor.BtnVerde.id -> {
                        binding.contenedor.matCardForm.setCardBackgroundColor(Color.parseColor("#E8F5E9")) // Verde Pastel
                    }
                    binding.contenedor.BtnAzul.id -> {
                        binding.contenedor.matCardForm.setCardBackgroundColor(Color.parseColor("#E3F2FD")) // Azul Pastel
                    }
                }
            }
        }
    }

    // Inyección de Imágenes Segura por Glide
    private fun loadImage(url: String = "https://media3.giphy.com/media/v1.Y2lkPTZjMDliOTUyYXVqdHM5MzgyY3diNWFpN3hjdDhra296dmNidWhibGhiOGRnbGx4ayZlcD12MV9naWZzX3NlYXJjaCZjdD1n/zBOqRPmkEF3Ow/200w.gif") {
        Glide.with(this)
            .asGif()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.outline_bid_landscape_24)
            .error(R.drawable.outline_broken_image_24)
            .into(binding.contenedor.imgCard2)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.a_exit -> {
                Toast.makeText(this, "Nos vemos luego", Toast.LENGTH_SHORT).show()
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}