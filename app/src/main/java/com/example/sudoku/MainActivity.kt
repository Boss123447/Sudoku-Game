package com.example.sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.sudoku.sudoku.SudokuGenerator
import com.example.sudoku.ui.theme.SudokuTheme

enum class Screen { HOME, GAME }
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SudokuTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppRoot(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SudokuScreen(
    modifier: Modifier = Modifier,
    difficulty: SudokuGenerator.Difficulty,
    onBackToHome: () -> Unit) {
    val generated = remember(difficulty) {
        SudokuGenerator().generate(difficulty)
    }

    // UI state grid
    val grid = remember {
        mutableStateListOf<Int>().apply {
            for (r in 0 until 9) for (c in 0 until 9) add(generated.get(r, c))
        }
    }

    // Lock the given cells
    val givens = remember {
        buildSet {
            for (r in 0 until 9) for (c in 0 until 9) {
                if (generated.get(r, c) != 0) add(r * 9 + c)
            }
        }
    }

    var selected by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    fun index(r: Int, c: Int) = r * 9 + c

    fun setCell(value: Int) {
        val (r, c) = selected ?: return
        val idx = index(r, c)
        if (idx in givens) return // don't edit givens
        grid[idx] = value
    }

    fun resetBoard() {
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                grid[index(r, c)] = generated.get(r, c)
            }
        }
        selected = null
    }

    fun isConflict(r: Int, c: Int): Boolean {
        val v = grid[index(r, c)]
        if (v == 0) return false

        // Row
        for (cc in 0 until 9) {
            if (cc != c && grid[index(r, cc)] == v) return true
        }

        // Column
        for (rr in 0 until 9) {
            if (rr != r && grid[index(rr, c)] == v) return true
        }

        // 3x3 box
        val br = (r / 3) * 3
        val bc = (c / 3) * 3
        for (rr in br until br + 3) {
            for (cc in bc until bc + 3) {
                if ((rr != r || cc != c) && grid[index(rr, cc)] == v) return true
            }
        }

        return false
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sudoku", fontSize = 32.sp)
        Spacer(Modifier.height(12.dp))

        val boardSize = 360.dp
        val cellSize = boardSize / 9f

        // Board container
        Box(
            modifier = Modifier
                .size(boardSize)
                .drawBehind {
                    val cell = size.width / 9f
                    val thin = 1.dp.toPx()
                    val thick = 3.dp.toPx()

                    // Outer border
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(0f, 0f),
                        size = size,
                        style = Stroke(width = thick)
                    )

                    // Inner lines
                    for (i in 1..8) {
                        val stroke = if (i % 3 == 0) thick else thin
                        val pos = i * cell

                        drawLine(Color.Black, Offset(pos, 0f), Offset(pos, size.height), stroke)
                        drawLine(Color.Black, Offset(0f, pos), Offset(size.width, pos), stroke)
                    }
                }
        ) {
            Column {
                for (r in 0 until 9) {
                    Row {
                        for (c in 0 until 9) {
                            val idx = index(r, c)
                            val v = grid[idx]
                            val isGiven = idx in givens
                            val isSelected = selected?.first == r && selected?.second == c
                            val conflict = isConflict(r ,c)

                            //highlight row/col
                            val inSameRowOrCol =
                                selected != null && (selected!!.first == r || selected!!.second == c)
                            val bg = when {
                                isSelected -> Color(0xFFB3D7FF)
                                inSameRowOrCol -> Color(0xFFEAF3FF)
                                else -> Color.Transparent
                            }
                            val outline = isSelected || inSameRowOrCol

                            Box(
                                modifier = Modifier
                                    .size(cellSize)
                                    .drawBehind {
                                        if (outline) {
                                            val stroke = 2.dp.toPx()
                                            // draw a rectangle outline INSIDE the cell
                                            drawRect(
                                                color = Color(0xFF2B5FD9),
                                                size = size,
                                                style = Stroke(width = stroke)
                                            )
                                        }
                                    }
                                    .clickable {
                                        selected = if (selected?.first == r && selected?.second == c) null else (r to c)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (v == 0) "" else v.toString(),
                                    fontSize = 18.sp,
                                    fontWeight = if (isGiven) FontWeight.ExtraBold else FontWeight.Bold,
                                    color = when {
                                        isGiven -> Color.Black
                                        conflict -> Color.Red
                                        else -> Color(0xFF1E4DB7)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { resetBoard() }) {
                Text("Reset")
            }
            OutlinedButton(onClick = onBackToHome) {
                Text("Home")
            }
        }
        Spacer(Modifier.height(14.dp))

        // Number pad
        NumberPad(
            onNumber = { setCell(it) },
            onErase = { setCell(0) },
            enabled = selected != null && index(selected!!.first, selected!!.second) !in givens
        )
    }
}

@Composable
private fun NumberPad(
    onNumber: (Int) -> Unit,
    onErase: () -> Unit,
    enabled: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        for (row in 0 until 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (col in 1..3) {
                    val n = row * 3 + col
                    Button(
                        onClick = { onNumber(n) },
                        enabled = enabled,
                        modifier = Modifier.size(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(n.toString(), fontSize = 18.sp)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }

        OutlinedButton(
            onClick = onErase,
            enabled = enabled,
            modifier = Modifier.height(48.dp)
        ) {
            Text("Erase")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SudokuPreview() {
    SudokuTheme {
        SudokuScreen(
            difficulty = SudokuGenerator.Difficulty.MEDIUM,
            onBackToHome = {}
        )
    }
}

@Composable
fun AppRoot(modifier: Modifier = Modifier) {
    var screen by remember { mutableStateOf(Screen.HOME) }
    var difficulty by remember { mutableStateOf(SudokuGenerator.Difficulty.MEDIUM) }

    when (screen) {
        Screen.HOME -> HomeScreen(
            modifier = modifier,
            onPick = { diff ->
                difficulty = diff
                screen = Screen.GAME
            }
        )

        Screen.GAME -> SudokuScreen(
            modifier = modifier,
            difficulty = difficulty,
            onBackToHome = { screen = Screen.HOME }
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPick: (SudokuGenerator.Difficulty) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sudoku")
        Spacer(Modifier.height(24.dp))

        Button(onClick = { onPick(SudokuGenerator.Difficulty.EASY) }, modifier = Modifier.fillMaxWidth()) {
            Text("Easy")
        }
        Spacer(Modifier.height(12.dp))

        Button(onClick = { onPick(SudokuGenerator.Difficulty.MEDIUM) }, modifier = Modifier.fillMaxWidth()) {
            Text("Medium")
        }
        Spacer(Modifier.height(12.dp))

        Button(onClick = { onPick(SudokuGenerator.Difficulty.HARD) }, modifier = Modifier.fillMaxWidth()) {
            Text("Hard")
        }
    }
}