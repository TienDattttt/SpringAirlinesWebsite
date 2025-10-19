<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
     xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
     xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
</head>

<body>
     <div layout:fragment="content">
          <div class="container py-5">
               <div class="row">
                    <div class="col-md-2 text-center">
                         <p><i class="fa fa-exclamation-triangle fa-5x me-2"></i><br />Mã trạng thái: 403</p>
                    </div>
                    <div class="col-md-10">
                         <h3>Lấy làm tiếc...</h3>
                         <p>Rất tiếc, bạn không có quyền truy cập trang này.<br />Vui lòng quay lại trang trước để tiếp
                              tục duyệt.</p>
                         <a class="btn btn-outline-danger" href="javascript:history.back()">Go Back</a>
                    </div>
               </div>
          </div>
     </div>
</body>

</html>