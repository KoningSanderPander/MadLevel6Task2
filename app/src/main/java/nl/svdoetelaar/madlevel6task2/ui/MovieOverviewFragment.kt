package nl.svdoetelaar.madlevel6task2.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_overview.*
import nl.svdoetelaar.madlevel6example.viewmodel.MovieViewModel
import nl.svdoetelaar.madlevel6task2.R
import nl.svdoetelaar.madlevel6task2.databinding.FragmentMovieOverviewBinding
import nl.svdoetelaar.madlevel6task2.model.MovieJsonResponse.Movie
import kotlin.math.floor

const val MOVIE = "MOVIE"

class MovieOverviewFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()

    private val movies = arrayListOf<Movie>()
    private val movieAdapter = MovieAdapter(movies, ::onClickMovie)

    private lateinit var binding: FragmentMovieOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setSubmitListener()

        movieViewModel.movies.observe(viewLifecycleOwner, {
            movies.clear()
            movies.addAll(it)
            movieAdapter.notifyDataSetChanged()
        })

        movieViewModel.errorText.observe(viewLifecycleOwner, {
            Snackbar.make(
                btnSubmit,
                getString(R.string.unable_to_fetch_movies),
                Snackbar.LENGTH_SHORT
            ).show()
        })
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvMovies.layoutManager = gridLayoutManager

        binding.rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.spanCount = calculateSpanCount()
                gridLayoutManager.requestLayout()
            }
        })

        binding.rvMovies.adapter = movieAdapter
    }

    private fun setSubmitListener() {
        binding.btnSubmit.setOnClickListener {
            binding.rvMovies.smoothScrollToPosition(0)

            val year = binding.etYear.text!!
            closeKeyboard()

            if (year.isEmpty()) {
                Snackbar.make(
                    binding.btnSubmit,
                    getString(R.string.invalid_year),
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            movieViewModel.getMovies(year.toString().toInt())
        }
    }

    private fun calculateSpanCount(): Int {
        val viewWidth = binding.rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val cardViewMargin = resources.getDimension(R.dimen.margin_medium)
        val spanCount: Int = floor(viewWidth / (cardViewWidth + cardViewMargin).toDouble())
            .toInt()
        return if (spanCount >= 1) spanCount else 1
    }

    private fun onClickMovie(movie: Movie) {
        setFragmentResult(MOVIE, bundleOf(MOVIE to movie))
        findNavController().navigate(R.id.action_movieOverviewFragment_to_movieInfoFragment)
    }

    private fun closeKeyboard() {
        // Null check to prevent from crashing
        if (activity?.currentFocus != null) {
            // Get input manager
            val inputManager: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // Tell input manager to hide input (close)
            inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}