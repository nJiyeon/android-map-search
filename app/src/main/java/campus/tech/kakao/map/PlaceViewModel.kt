package campus.tech.kakao.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PlaceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PlaceRepository = PlaceRepository(application)

    val places: LiveData<List<PlaceItem>> get() = repository.places
    val history: LiveData<List<HistoryItem>> get() = repository.history

    init {
        repository.loadAllHistory()
    }

    fun searchPlaces(keyword: String) {
        repository.searchPlaces(keyword)
    }

    fun addHistory(name: String) {
        repository.addHistory(name)
    }

    fun removeHistory(name: String) {
        repository.removeHistory(name)
    }
}
