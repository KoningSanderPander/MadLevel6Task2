package nl.svdoetelaar.madlevel6task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import nl.svdoetelaar.madlevel6task2.databinding.FragmentMovieInfoBinding
import nl.svdoetelaar.madlevel6task2.model.MovieJsonResponse.Movie

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieInfoFragment : Fragment() {

    private lateinit var binding: FragmentMovieInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMovie()
    }

    private fun observeMovie() {
        setFragmentResultListener(MOVIE) { _, bundle ->
            bundle.getParcelable<Movie>(MOVIE)?.let { movie ->
                Glide.with(requireContext()).load(movie.getMovieImageUrl(movie.backdrop_path))
                    .into(binding.ivMovieBanner)
                Glide.with(requireContext()).load(movie.getMovieImageUrl(movie.poster_path))
                    .into(binding.ivMoviePoster)
                binding.tvMovieDate.text = movie.release_date
                binding.tvMovieDescription.text = movie.overview
                binding.tvMovieTitle.text = movie.title
                binding.tvMovieGrade.text = movie.vote_average.toString()
            }
        }
    }
}