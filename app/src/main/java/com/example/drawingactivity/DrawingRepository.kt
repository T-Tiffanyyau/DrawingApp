package com.example.drawingactivity

import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Manages the data sources and fetches data from the network.
 */
open class DrawingRepository(
    private val scope: CoroutineScope,
    private val dao: DrawingDao
) {

    //updated with the DB is modified
    val allFunfacts = dao.allFunFacts()

    fun saveDrawing(drawing: DrawingData) {
        scope.launch {
            if (dao.countDrawingsByName(drawing.title) == 0) {
                dao.addFunfactData(drawing)
            } else {
//                Log.e("DrawingRepository", "A drawing with the same name already exists.")
//                Dialogs.showSaveWarningDialog(fragment, "A drawing with the same name already exists. Do you want to overwrite it?")
                dao.deleteDrawingByTitle(drawing.title)
                dao.addFunfactData(drawing)
            }
        }
    }

    suspend fun isSavetoSaveDrawing(drawing: DrawingData): Boolean {
        var save = false
        withContext(Dispatchers.IO) {
            if (dao.countDrawingsByName(drawing.title) == 0) {
                Log.e("DrawingRepository", "New Drawing: $save")
                save = true
            }
        }
        Log.e("DrawingRepository", "isSavetoSaveDrawing: $save")
        return save
    }

    fun deleteDrawing(drawing: DrawingData) {
        scope.launch {
            dao.deleteDrawingData(drawing)
        }
    }

//    val client = HttpClient()
//
//    //Make a "web request", then insert it into the DB, triggering the above to update
//    fun fetchFact() {
//        scope.launch {
//            Log.e("REPO", "Fetching weather for")
//
//            val response: HttpResponse =
//                client.get("https://uselessfacts.jsph.pl/random.json?language=en")
//            val HTTPReponseText = withContext(Dispatchers.IO) { response.bodyAsText() }
//            val ReponseInJsonObject = Json.parseToJsonElement(HTTPReponseText).jsonObject
//
//            val FactText = ReponseInJsonObject["text"]?.jsonPrimitive?.content
//            val FactLink = ReponseInJsonObject["source_url"]?.jsonPrimitive?.content
//
//            var newFact = FunfactData("Error", "none")
//            if (FactText != null && FactLink != null) {
//                newFact = FunfactData(FactText, FactLink)
//            }
//
//            //now that we got the weather from our "slow network request" add it to the DB
//            dao.addFunfactData(newFact)
//            Log.e("REPO", "told the DAO")
//        }
//    }
}
