package org.zerock.tp4.board.domain;

import lombok.*;
import org.zerock.tp4.board.dto.BoardDTO;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Board {

    private Long bno;
    private String title, content, writer;
    private LocalDateTime regDate, modDate;

    public BoardDTO getDTO(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .build();
        return boardDTO;
    }

    public void setBno(Long bno){
        this.bno=bno;
    }

}
