package movie.app.movieapidevelopment.mapper;

import movie.app.movieapidevelopment.domain.entity.MovieEntity;
import movie.app.movieapidevelopment.dto.request.MovieRequest;
import movie.app.movieapidevelopment.dto.response.MovieResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieEntity toEntity(MovieRequest dto);

    MovieResponse toDTO(MovieEntity movie);
}
