
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
                        </form>
                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>

</div>
<%@include file="../includes/footer.jsp"%>
</body>
</html>
