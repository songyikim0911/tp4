package org.zerock.tp4.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.tp4.board.dto.BoardDTO;
;
import org.zerock.tp4.board.service.BoardService;
import org.zerock.tp4.board.service.TimeService;
import org.zerock.tp4.common.dto.PageMaker;
import org.zerock.tp4.common.dto.PageRequestDTO;
import org.zerock.tp4.common.dto.PageResponseDTO;

@Controller
@RequestMapping("/board/*")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final TimeService timeService;

    private final BoardService boardService;//보드서비스를 주입해놓으면, 스프링이 필요한 객체를 주입해줌.


    //get 으로 들어오면 입력하는화면
    //post로 들어오면 실제 입력 처리


    @GetMapping("/time")
    public void getTime(Model model){
        log.info("....");
        model.addAttribute("time", timeService.getNow());
    }


    @GetMapping("register")
    public void registerGet(){

    }
    //자동으로 해당하는 url의 jsp를 찾아감

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
        //스프링은 알아서 변수들을 수집해서 DTO를 만들어주는 작업까지도 해줌
        //없는 애들은 null값으로 알아서 DTO로 만들어서 가져옴.



        /* 기존 bitboard에 있는, 아래의 일을
        여기서는 BoardDTO 매개변수를( title,content,writer)를 view에서 가져오면서,
        스프링이 자동으로 이 변수들로 BoardDTO를 만들어서 매개 변수에 넣는건가?
      BoardDTO boardDTO = BoardDTO.builder()
                          .title(request.getParameter("title"))
                          .content(request.getParameter("content"))
                          .writer(request.getParameter("writer"))
                          .build();


 */
        log.info("boardDTOM" + boardDTO);

//
//        Long bno = 111L;
        Long bno = boardService.register(boardDTO);

        log.info("===== c registerpost====");
        log.info(bno);

        redirectAttributes.addFlashAttribute("result",bno);
        //리다이렉트할때 일회성으로 값을 끄집어 내서 쓸 수 있다!!!!! 한번 ! 저번에 hello에서 값 한번 띄워낸것처럼.
        return "redirect:/board/list";

    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model){
        log.info("c   getList......................" + pageRequestDTO);

        PageResponseDTO<BoardDTO> responseDTO = boardService.getDTOList(pageRequestDTO);

        model.addAttribute("dtoList", responseDTO.getDtoList());

        int total = responseDTO.getCount();
        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();

        model.addAttribute("pageMaker", new PageMaker(page,size,total));

    }



//    @GetMapping("/list") 오류 다시보자...
//    public void getList(PageRequestDTO pageRequestDTO, Model model){
//        //이제 jsp로 담아서 보내야 하기 때문에 model필요!
//
//        log.info("c getList......" +pageRequestDTO);
//        //c찍어놓으면 controller에서 나오는 로그, s로찍으면 service에서 나오는 로그!
//
//       // model.addAttribute("dtoList", boardService.getDTOList(pageRequestDTO));
//        //아래와 같이 변경
//
//        PageResponseDTO<BoardDTO> responseDTO = boardService.getDTOList(pageRequestDTO);
//
//        model.addAttribute("dtoList", responseDTO);
//
//        //필요한 count등의 재료는 다 받아놨으니, 이제 가공을하면된다.
//
//        int total = responseDTO.getCount();
//        int page = pageRequestDTO.getPage();
//        int size = pageRequestDTO.getSize();//가공! 이 세개를 활용, 이제 pageMaker 만들어 줄 수 있다.
//
//        model.addAttribute("pageMaker", new PageMaker(page,size,total));
//        //이렇게 하고나면 이제 board/list/jsp로 가는것이다.
//    }


//    @GetMapping("/list")
//    public void list(Criteria cri, Model model){
//        log.info("list:" +cri);
//        model.addAttribute("dtoList", boardService.getDTOList(cri));
//        model.addAttribute("pageMaker", new PageDTO(cri,123));
//    }
//

    @GetMapping(value = {"/read", "/modify", "/read2"})//read로 들어오면 readpractice.jsp, modify로 들어오면 modify0909ver.jsp 2개 경로로 가능
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        log.info("c read" + bno);
        log.info("c read" + pageRequestDTO);
        model.addAttribute("boardDTO", boardService.read(bno));
    }

    @PostMapping("/remove")
    public String remove(Long bno,RedirectAttributes redirectAttributes){
        log.info("c    remove:" +bno);

        if(boardService.remove(bno)){
            log.info("remove success");
            redirectAttributes.addFlashAttribute("result","success");
        }//삭제 완료하면 모달 창 뜰 수 있게, result 값을 전달해주는것.
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){

        log.info("----------");
        log.info("----------");
        log.info(boardDTO);
        if(boardDTO.getFiles().size()>0){
            boardDTO.getFiles().forEach(dto -> log.info(dto));
        }
        log.info("----------");
        log.info("----------");
        log.info("----------");


        if(boardService.modify(boardDTO)){
            redirectAttributes.addFlashAttribute("result","modified");
        }
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size", pageRequestDTO.getSize());

        //컨트롤러에도 타입과 키워드 종류 전달해주는 구문 작성성

        if(pageRequestDTO.getType() != null){
            redirectAttributes.addAttribute("type", pageRequestDTO.getType());
            redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
        }

       return "redirect:/board/read";
    }

}
