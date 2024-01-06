package pl.damian.bodzioch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieGetAllResponseDto extends BaseResponse implements Serializable {

    private List<MovieGetDto> movies;
}
