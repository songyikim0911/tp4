package org.zerock.tp4.board.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.tp4.board.domain.Board;
import org.zerock.tp4.board.domain.BoardAttach;
import org.zerock.tp4.common.dto.PageRequestDTO;

import java.util.List;

public interface BoardMapper {

    void insert(Board board);

    //보드에 있는 list 가져오기
   // List<Board> getList();//아직 페이징처리 안할거라 파라미터안가져옴
    //이 다음 순서는 xml로.

    List<Board> getList(PageRequestDTO pageRequestDTO);
    //두군데에서 에러가 생김 1)getList를 호출하는 BoardService/boardServiceiImpl에서 생김. 2)
//2)x테스트코드

    int getCount(PageRequestDTO pageRequestDTO);

//    List<Board> getListWithPaging(Criteria cri);

    Board select(Long bno);

    int delete(Long bno);

    int update(Board board);

    int updateReplyCnt(@Param("bno")Long bno, @Param("num") int num );

    int insertAttach(BoardAttach attach);

    int deleteAttach(Long bno);


}
