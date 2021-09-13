package org.zerock.tp4.board.mapper;

import org.zerock.tp4.board.domain.Reply;

import java.util.List;

public interface ReplyMapper {

    int insert(Reply reply);

    //특정한 게시글로 목록 가져올 때
    List<Reply> getListWithBoard(Long bno);//댓글은 검색기능제외

    int delete(Long rno);

    int update(Reply reply);

}
