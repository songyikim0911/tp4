
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Register Page</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Dashboard v1</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Board Register</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form action ="/board/register" method="post">
                            <div class="form-group">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Email address</label>
                                    <input type="text" name="title" class="form-control" id="exampleInputEmail1" placeholder="Enter Title">
                                    <!--각각 placeholder 이름 지어주기-->
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Writer</label>
                                    <input type="text" name="writer" class="form-control" id="exampleInputEmail2" placeholder="Enter Writer">
                                </div>
                                <div class="row">
                                    <div class="col-sm-12"><!--6에서 12 로 변경, 6은 절반이라는의미-->
                                        <!-- textarea -->
                                        <div class="form-group">
                                            <label>Textarea</label>
                                            <textarea name="content" class="form-control" rows="3" placeholder="Enter ..."></textarea>
                                            <!--textarea 네임값 추가-->
                                        </div>
                                    </div>
                                </div>

                                <!-- /.card-body -->

                                <div class="card-footer">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <!--submit 있는지 확인-->
                                </div>
                            </div>
                        </form>



                        <style>
                            .uploadResult{
                                display:flex;
                                justify-content:center;
                                flex-direction: row;
                            }
                        </style>

                        <label for="exampleInputFile">File input</label>
                        <div class="input-group">
                            <div class="custom-file">
                                <input type="file" name="uploadFiles" class="custom-file-input" id="exampleInputFile">
                                <label class="custom-file-label" for="exampleInputFile">Choose file</label>
                            </div>
                            <div class="input-group-append">
                                <span class="input-group-text" id="uploadBtn">Upload</span>
                            </div>
                        </div>
                        <button type="button" class="btn btn-info">Upload</button>
                        <div class="uploadResult">

                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>

</div>
<%@include file="../includes/footer.jsp"%>


<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>

    const uploadResultDiv = document.querySelector(".uploadResult")

    document.querySelector("#uploadBtn").addEventListener("click",(e)=>{

        const formData = new FormData()
        const fileInput = document.querySelector("input[name='uploadFiles']")//input[name='uploadFiles']제이쿼리..

        for(let i = 0; i< fileInput.files.length;i++){
            formData.append("uploadFiles", fileInput.files[i])//중요! uploadFiles는 파라미터이름, 컨트롤러에서 받는 이름임.
        }

        console.dir(formData)
        //console.log(fileInput)
        //console.dir(fileInput)//속성,값이 더 잘 나옴.확인가능
        console.log(formData)

        const headerObj = { headers: { 'Content-Type' : 'multipart/form-data'}}

        axios.post("/upload", formData, headerObj).then((response)=>{
            const arr = response.data //배열 데이터이므로, 배열로 넣어준 것
            console.log(arr)
            let str=""
            //배열이기때문에, 루프를 돌 필요가 있다.
            for(let i = 0; i < arr.length; i++){
                const {uuid, fileName, uploadPath, image, thumbnail, fileLink} = {...arr[i]}//스프레드 연산자 이걸로 이제 div만들어줄 예정
                //각각의 데이터가 파일업로드의 결과. json데이터의 하나하나임, (uuid,fileName,image,uploadPath값이들어있는!)
                if(image){//이미지인 경우
                    //보안필요, 접근할 수 있는사람만 접근하도록 하기. 그리고 resources-img에 값을넣어도 안보일수있고
                    //우리가 값을넣는것은 war에 묶어서 값을 넣어서 쏘기때문에
                    //1) 이미지 서버를 따로 만드는 방식, 웹서버가 이미지 경로처리
                    //2) 클라우드서버가 업로드 경로..
                    str +=`<div data-uuid='\${uuid}'><img src='/viewFile?file=\${thumbnail}'/><span>\${fileName}</span>
                           <button onclick="javascript:removeFile('\${fileLink}',this)">x</button></div>`//기존문자열에 더해주기
                }else {//이미지가 아닌 경우: 즉 일반파일
                    str += `<div data-uuid='\${uuid}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a></div>`
                }
                //
                // else{
                //         str +=`<div data-uuid='\${uuid}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a></div>`//기존문자열에 더해주기
                //     }


            }//end for
            uploadResultDiv.innerHTML +=str //완성된 문자열을 넣어주기, 업로드가 여러번 이루어질 수 있기떄문에
            //기존값을 유지하면서 값을 넣어야함!
        });

    },false)

    function removeFile(fileLink,ele){
        console.log(fileLink)
        axios.post("/removeFile", {fileName:fileLink}).then(response =>{
            const targetDiv = ele.parentElement
            targetDiv.remove()//삭제 처리

        })
    }

</script>

</body>


</html>
