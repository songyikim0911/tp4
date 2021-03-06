package org.zerock.tp4.board.service;


import org.springframework.transaction.annotation.Transactional;
import org.zerock.tp4.board.dto.BoardDTO;
import org.zerock.tp4.common.dto.PageRequestDTO;
import org.zerock.tp4.common.dto.PageResponseDTO;

import java.util.List;

@Transactional
public interface BoardService {

    Long register(BoardDTO boardDTO);

//    List<BoardDTO> getDTOList();//서비스계층은 가능하면 영속계층 ntt를 건들지않고, dto로 받고 dto로 수정하고.

    //List<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO);
    //변경 완료

    PageResponseDTO<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO);

    //List<BoardDTO> getDTOList(Criteria cri);

    BoardDTO read(Long bno);

    boolean remove(Long bno);

    boolean modify(BoardDTO boardDTO);

}
