package org.zerock.tp4.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.tp4.board.domain.Board;
import org.zerock.tp4.board.dto.BoardDTO;
import org.zerock.tp4.board.mapper.BoardMapper;
import org.zerock.tp4.common.dto.PageRequestDTO;
import org.zerock.tp4.common.dto.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service//일종의 @Component
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = boardDTO.getDomain();

        boardMapper.insert(board);

        Long bno = board.getBno();

        board.getAttachList().forEach(attach ->{
            attach.setBno(bno);//아까 attach에 만든 setbno 를 사용해야함.
            boardMapper.insertAttach(attach);
        });

        return board.getBno();
    }

    @Override
    public PageResponseDTO<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO) {

       List<BoardDTO> dtoList = boardMapper.getList(pageRequestDTO).stream().map(board -> board.getDTO()).collect(Collectors.toList());
        int count = boardMapper.getCount(pageRequestDTO);

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>builder()
                .dtoList(dtoList)
                .count(count)
                .build();
        //서비스가 지금까지는 전달받기만하다가
        //이제 서비스에서 가공을 하고 보낸다.
        return pageResponseDTO;

    }


//    @Override
//    public List<BoardDTO> getDTOList(Criteria cri) {
//        log.info("get list with criteria:" + cri);
//        return boardMapper.getListWithPaging(cri).stream().map(board -> board.getDTO()).collect(Collectors.toList());
//
//    }




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

        boardMapper.deleteAttach(boardDTO.getBno());

        Board board = boardDTO.getDomain();

        Long bno = board.getBno();

        board.getAttachList().forEach(attach ->{
            attach.setBno(bno);//아까 attach에 만든 setbno 를 사용해야함.
            boardMapper.insertAttach(attach);
        });


        return boardMapper.update(board) > 0;
    }


}
