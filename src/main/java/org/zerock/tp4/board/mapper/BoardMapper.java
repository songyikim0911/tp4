package org.zerock.tp4.board.mapper;

import org.zerock.tp4.board.domain.Board;
import org.zerock.tp4.board.domain.Criteria;

import java.util.List;

public interface BoardMapper {

    void insert(Board board);

    //보드에 있는 list 가져오기
    List<Board> getList();//아직 페이징처리 안할거라 파라미터안가져옴
    //이 다음 순서는 xml로.

    List<Board> getListWithPaging(Criteria cri);

    Board select(Long bno);

    int delete(Long bno);

    int update(Board board);

}
