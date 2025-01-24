package test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import movie.app.movieapidevelopment.MovieapidevelopmentApplication;
import movie.app.movieapidevelopment.controller.MovieController;
import movie.app.movieapidevelopment.dto.request.MovieRequest;
import movie.app.movieapidevelopment.dto.response.MovieResponse;
import movie.app.movieapidevelopment.exceptions.BadRequestException;
import movie.app.movieapidevelopment.exceptions.DatabaseException;
import movie.app.movieapidevelopment.exceptions.ResourceNotFoundException;
import movie.app.movieapidevelopment.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
@ContextConfiguration(classes = MovieapidevelopmentApplication.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private MovieResponse movieResponse;

    @BeforeEach
    void setUp() {
        movieResponse = createMovieResponse(1L, "Inception", "Christopher Nolan", 2010, "Sci-Fi", 8.2F);

        when(movieService.getAllMovies()).thenReturn(List.of(movieResponse));
        when(movieService.getMovieById(1L)).thenReturn(movieResponse);
        when(movieService.addMovie(any(MovieRequest.class))).thenReturn(movieResponse);
        when(movieService.updateMovie(eq(1L), any(MovieRequest.class))).thenReturn(movieResponse);
        doNothing().when(movieService).deleteMovie(1L);
    }


    private MovieRequest createMovieRequest(String title, String director, int releaseYear, String genre, float imdbRating) {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle(title);
        movieRequest.setDirector(director);
        movieRequest.setReleaseYear(releaseYear);
        movieRequest.setGenre(genre);
        movieRequest.setImdbRating(imdbRating);
        return movieRequest;
    }

    private MovieResponse createMovieResponse(Long id, String title, String director, int releaseYear, String genre, float imdbRating) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(id);
        movieResponse.setTitle(title);
        movieResponse.setDirector(director);
        movieResponse.setReleaseYear(releaseYear);
        movieResponse.setGenre(genre);
        movieResponse.setImdbRating(imdbRating);
        return movieResponse;
    }

    @Test
    void testGetAllMovies_Success() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Inception")))
                .andExpect(jsonPath("$[0].director", is("Christopher Nolan")))
                .andExpect(jsonPath("$[0].releaseYear", is(2010)))
                .andExpect(jsonPath("$[0].imdbRating").value(8.2));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void testGetAllMovies_Empty() throws Exception {
        when(movieService.getAllMovies()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void testGetMovieById_Success() throws Exception {
        mockMvc.perform(get("/api/v1/movies/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Inception")))
                .andExpect(jsonPath("$.director", is("Christopher Nolan")))
                .andExpect(jsonPath("$.releaseYear", is(2010)))
                .andExpect(jsonPath("$.imdbRating").value(8.2));

        verify(movieService, times(1)).getMovieById(1L);
    }

    @Test
    void testGetMovieById_NotFound() throws Exception {
        when(movieService.getMovieById(999L)).thenThrow(new ResourceNotFoundException("Movie not found"));

        mockMvc.perform(get("/api/v1/movies/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movie not found"));

        verify(movieService, times(1)).getMovieById(999L);
    }

    @Test
    void testGetMovieById_BadRequest() throws Exception {
        when(movieService.getMovieById(0L)).thenThrow(new BadRequestException("ID must be positive"));

        mockMvc.perform(get("/api/v1/movies/0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID must be positive"));

        verify(movieService, times(1)).getMovieById(0L);
    }


    @Test
    void testAddMovie_Success() throws Exception {
        MovieRequest movieRequest = createMovieRequest("Inception", "Christopher Nolan", 2010, "Sci-Fi", 8.8F);

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Inception")))
                .andExpect(jsonPath("$.director", is("Christopher Nolan")));

        verify(movieService, times(1)).addMovie(any(MovieRequest.class));
    }

    @Test
    void testAddMovie_InvalidRating() throws Exception {
        MovieRequest movieRequest = createMovieRequest("Inception", "Christopher Nolan", 2010, "Sci-Fi", -1F);
        when(movieService.addMovie(any(MovieRequest.class)))
                .thenThrow(new BadRequestException("IMDb rating must be between 0 and 10"));

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("IMDb rating must be between 0 and 10"));
    }

    @Test
    void testAddMovie_DatabaseError() throws Exception {
        MovieRequest movieRequest = createMovieRequest("Inception", "Christopher Nolan", 2010, "Sci-Fi", 8.8F);
        when(movieService.addMovie(any(MovieRequest.class)))
                .thenThrow(new DatabaseException("Error occurred while saving the movie"));

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error occurred while saving the movie"));
    }

    @Test
    void testUpdateMovie_Success() throws Exception {
        MovieRequest updateRequest = createMovieRequest("Inception Updated", "Christopher Nolan", 2011, "Thriller", 9.0F);
        mockMvc.perform(put("/api/v1/movies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Inception")));

        verify(movieService, times(1)).updateMovie(eq(1L), any(MovieRequest.class));
    }

    @Test
    void testUpdateMovie_NotFound() throws Exception {
        MovieRequest updateRequest = createMovieRequest("Unknown", "NoName", 2000, "Unknown", 5.0F);

        when(movieService.updateMovie(eq(99L), any(MovieRequest.class)))
                .thenThrow(new ResourceNotFoundException("Movie not found to update"));

        mockMvc.perform(put("/api/v1/movies/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movie not found to update"));

        verify(movieService, times(1)).updateMovie(eq(99L), any(MovieRequest.class));
    }

    @Test
    void testUpdateMovie_BadRequest() throws Exception {
        MovieRequest updateRequest = createMovieRequest("Unknown", "NoName", 2000, "Unknown", 5.0F);
        when(movieService.updateMovie(eq(0L), any(MovieRequest.class)))
                .thenThrow(new BadRequestException("ID must be positive"));

        mockMvc.perform(put("/api/v1/movies/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID must be positive"));
    }

    @Test
    void testDeleteMovie_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/movies/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).deleteMovie(1L);
    }

    @Test
    void testDeleteMovie_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Movie with ID 999 does not exist"))
                .when(movieService).deleteMovie(999L);

        mockMvc.perform(delete("/api/v1/movies/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movie with ID 999 does not exist"));

        verify(movieService, times(1)).deleteMovie(999L);
    }
}
