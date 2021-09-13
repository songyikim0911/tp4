package org.zerock.tp4.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.tp4.board.dto.ReplyDTO;
import org.zerock.tp4.board.mapper.BoardMapper;
import org.zerock.tp4.board.mapper.ReplyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor//replyMapper 주입시 생성자 주입을 이용, 생성자 주입이 안전하기 때문.
@Transactional
public class ReplyServiceImpl implements ReplyService{

    private final ReplyMapper replyMapper;//mapper를 주입 받을것이고, 자동 주입을 이용
    private final BoardMapper boardMapper;

    @Override
    public int add(ReplyDTO replyDTO) {
        int count = replyMapper.insert(dtoToEntity(replyDTO));
        boardMapper.updateReplyCnt(replyDTO.getBno(),1);

        return count;
    }

    @Override
    public List<ReplyDTO> getRepliesWithBno(Long bno) {
        return replyMapper.getListWithBoard(bno).stream().
                map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    //map : 각각의 reply를 entityToDTO로 변경해주기.
    }

    @Override
    public int remove(Long rno) {
        return replyMapper.delete(rno);
    }

    @Override
    public int modify(ReplyDTO replyDTO) {
        return replyMapper.update(dtoToEntity(replyDTO));
    }
}
