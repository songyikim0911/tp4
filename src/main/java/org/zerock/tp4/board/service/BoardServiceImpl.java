package org.zerock.tp4.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.tp4.board.domain.Board;
import org.zerock.tp4.board.domain.Criteria;
import org.zerock.tp4.board.dto.BoardDTO;
import org.zerock.tp4.board.mapper.BoardMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = boardDTO.getDomain();

        boardMapper.insert(board);

        return board.getBno();
    }

    @Override
    public List<BoardDTO> getDTOList(Criteria cri) {
        log.info("get list with criteria:" + cri);
        return boardMapper.getListWithPaging(cri).stream().map(board -> board.getDTO()).collect(Collectors.toList());

    }

//    @Override
//    public List<BoardDTO> getDTOList() {//반환은 DTO객체로 변환해주기
//
//        //boardMapper.getList().stream().map
//
//       return boardMapper.getList().stream().map(board -> board.getDTO()).collect(Collectors.toList());
//
//    }

    @Override
    public BoardDTO read(Long bno) {
       Board board = boardMapper.select(bno);

       if(board!= null){
           return board.getDTO();
       }
       return null;
    }

    @Override
    public boolean remove(Long bno) {
        return boardMapper.delete(bno) > 0;//한건 이상 삭제 되면 true가 나올 예정
    }

    @Override
    public boolean modify(BoardDTO boardDTO) {
        return boardMapper.update(boardDTO.getDomain()) > 0;
    }


}
