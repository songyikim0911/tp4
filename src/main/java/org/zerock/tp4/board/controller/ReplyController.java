package org.zerock.tp4.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.tp4.board.dto.ReplyDTO;
import org.zerock.tp4.board.service.ReplyService;

import java.util.List;

@Log4j2
@RestController//rest 용으로 만들어 질것, 지금까지 사용한 컨트롤러와 다르다/ @ResponseBody
//RestController를 이용하면 ResponseBody가 자동으로 걸린다. jacksondata bind도 동작하게됨.
@RequestMapping("replies")//rest는 url을 잡을 때 보통 복수로 잡기 때문에, url을 replies 로 잡는다.
//REST용의 특이점 : 리턴 하는 값이 모두 json으로 처리 될 예정이다.
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;


    //지금까지와는 다르다.
    //문자열 배열 반환 메서드만들어보기
    @GetMapping("")
    public String[] doA(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new String[]{"AAA","BBB","CCC"};
    }

    @PostMapping("")
    public int add(@RequestBody ReplyDTO replyDTO){//제이슨으로 들어오는것을 DTO로 바꿔주는 역할을 해주는 @RequestBody
        //AJAX 로 데이터를 주고받을때 에러가 많이 나는 부분.
        //제이슨으로 보내는데 문제가 생기는데, 제이슨으로 보낼때 얘를 자바의 객체로 보내야한다.

        log.info("===================");
        log.info(replyDTO);

        return replyService.add(replyDTO);

    }

    @DeleteMapping("/{rno}")//rno를 delete 방식으로 호출하겠다는의미,->삭제 처리를 함!
    //replies의 100번을 100번째에 호출...
    public String remove(@PathVariable(name="rno") Long rno){//내가 경로준애의 파라미터가 자동으로 수집되는것.PathVariable
        log.info("-------------reply remove------");

        log.info("rno:" + rno);

        replyService.remove(rno);

        return "success";
    }


    @PutMapping("/{rno}")
    public String modify(@PathVariable(name="rno") Long rno,
                         @RequestBody ReplyDTO replyDTO//제이슨으로 받기위해!
                         ){
                         log.info("----reply modify" + rno);  //rno들어오는지확인
                         log.info(replyDTO);//DTO들어오는지확인

        replyService.modify(replyDTO);


        return "success";
    }

    @GetMapping("/list/{bno}")//replies/list/230 -- 참고)rest방식은 표준이아니다. 이것을 어떻게 정하는지도 표준이아니다.
    public List<ReplyDTO> getBoardReplies(@PathVariable(name="bno") Long bno){
        //서비스 계층

        return replyService.getRepliesWithBno(bno);

    }

}












