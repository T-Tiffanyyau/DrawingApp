package com.example.drawingactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.drawingactivity.databinding.FragmentMainScreenBinding

class MainScreen : Fragment() {
    lateinit var binding: FragmentMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflator: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(layoutInflater)
        val drawingViewModel: DrawingViewModel by activityViewModels {
            DrawingViewModelFactory((requireActivity().application as DrawingApplication).DrawingtRepository)
        }

        // Switch to Drawing Screen when button is clicked
        binding.goDrawingScreen.setOnClickListener {
            drawingViewModel.setSelectDrawing(DrawingData("New Drawing", createBitmap(100, 100)))
            findNavController().navigate(R.id.lets_draw_action)
        }

        binding.composeViewMain.setContent {
            mainComposable(drawingViewModel = drawingViewModel)
        }
        Log.e("Drawing", "Main Screen Created")
        return binding.root
    }

    @Composable
    fun mainComposable(
        drawingViewModel: DrawingViewModel = viewModel(
            viewModelStoreOwner = LocalContext.current.findActivity()
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                // horizontalArrangement = Arrangement.Center
            ) {
                val listState = drawingViewModel.viewList.collectAsState()
                val list = listState.value
                LazyColumn {
                    items(list) { drawingData ->
                        Log.e("Drawing", "DrawingData: $drawingData")
                        cardComposable(drawingData, drawingViewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun cardComposable(
        drawingData: DrawingData,
        drawingViewModel: DrawingViewModel
    ) {

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable{
                        Log.e("Drawing", "Selected $drawingData clicked")
                        drawingViewModel.setSelectDrawing(drawingData)
                        findNavController().navigate(R.id.lets_draw_action)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    bitmap = drawingData.pic.asImageBitmap(),
                    contentDescription = "Paint Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
                Text(
                    text = drawingData.title,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(4.dp)
                )
                Button(
                    onClick = { drawingViewModel.deleteDrawing(drawingData) },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Delete")
                }
            }
        }
    }

    @Preview
    @Composable
    fun previewMain() {
        mainComposable()
    }
}