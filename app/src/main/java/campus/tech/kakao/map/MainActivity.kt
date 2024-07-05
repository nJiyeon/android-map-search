package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PlaceViewModel by viewModels()
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDelete.setOnClickListener {
            binding.etSearch.setText(null)
        }

        setupView()

        viewModel.places.observe(this, Observer { cursor ->
            placeAdapter.submitCursor(cursor)
        })

        viewModel.history.observe(this, Observer { cursor ->
            historyAdapter.submitCursor(cursor)
        })

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText.isEmpty()) {
                    binding.tvNoResults.visibility = View.VISIBLE
                    binding.recyclerViewPlaces.visibility = View.INVISIBLE
                } else {
                    binding.tvNoResults.visibility = View.INVISIBLE
                    binding.recyclerViewPlaces.visibility = View.VISIBLE
                    viewModel.searchPlaces(searchText)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

    private fun setupView() {
        placeAdapter = PlaceAdapter { name ->
            viewModel.addHistory(name)
        }

        historyAdapter = HistoryAdapter { name ->
            viewModel.removeHistory(name)
        }

        binding.recyclerViewPlaces.apply {
            adapter = placeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.recyclerViewHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
