package org.zerock.tp4.board.domain;

import lombok.*;
import org.zerock.tp4.board.dto.BoardDTO;
import org.zerock.tp4.board.dto.UploadResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Board {

    private Long bno;
    private String title, content, writer;
    private LocalDateTime regDate, modDate;
    private int replyCnt;

    //보드 안에 BoardAttach의 목록이 필요하다
    @Builder.Default
    private List<BoardAttach> attachList = new ArrayList<>();

    public BoardDTO getDTO(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .build();

        //BoardAttach->uploadResponseDTO ->변환 후 List로 묶어서 처리 예정

            List<UploadResponseDTO> uploadResponseDTOList = attachList.stream().map(attach ->{
            UploadResponseDTO uploadResponseDTO = UploadResponseDTO.builder()
                    .uuid(attach.getUuid())
                    .fileName(attach.getFileName())
                    .uploadPath(attach.getPath())
                    .image(attach.isImage())
                    .build();
            return uploadResponseDTO;
        }).collect(Collectors.toList());

        boardDTO.setFiles(uploadResponseDTOList);

        return boardDTO;
    }

    public void setBno(Long bno){
        this.bno=bno;
    }

    public void addAttach(BoardAttach attach){
        attachList.add(attach);
    }

}
