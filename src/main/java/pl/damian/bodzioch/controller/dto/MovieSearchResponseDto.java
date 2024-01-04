package pl.damian.bodzioch.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class MovieSearchResponseDto extends BaseResponse implements Serializable {

    private List<MovieSearchDto> movies;
}
