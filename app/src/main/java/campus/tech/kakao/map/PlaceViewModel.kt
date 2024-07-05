package campus.tech.kakao.map

import android.app.Application
import android.content.ContentValues
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.database.Cursor

class PlaceViewModel(application: Application) : AndroidViewModel(application) {
    private val placeDBHelper: PlaceDBHelper = PlaceDBHelper(application)
    private val historyDBHelper: HistoryDBHelper = HistoryDBHelper(application, "history.db", null, 2)

    private val _places = MutableLiveData<Cursor>()
    val places: LiveData<Cursor> get() = _places

    private val _history = MutableLiveData<Cursor>()
    val history: LiveData<Cursor> get() = _history

    init {
        loadAllHistory()
    }

    fun searchPlaces(keyword: String) {
        val selection = "${PlaceContract.COLUMN_CATEGORY} LIKE ?"
        val selectionArgs = arrayOf("%$keyword%")
        val db = placeDBHelper.readableDatabase
        val cursor = db.query(
            PlaceContract.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        _places.value = cursor
    }

    fun addHistory(name: String) {
        val db = historyDBHelper.writableDatabase
        val values = ContentValues().apply {
            put(HistoryContract.COLUMN_NAME, name)
        }
        db.insert(HistoryContract.TABLE_NAME, null, values)
        loadAllHistory()
    }

    fun removeHistory(name: String) {
        val db = historyDBHelper.writableDatabase
        val selection = "${HistoryContract.COLUMN_NAME} = ?"
        val selectionArgs = arrayOf(name)
        db.delete(HistoryContract.TABLE_NAME, selection, selectionArgs)
        loadAllHistory()
    }

    private fun loadAllHistory() {
        val db = historyDBHelper.readableDatabase
        val cursor = db.query(
            HistoryContract.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        _history.value = cursor
    }
}
