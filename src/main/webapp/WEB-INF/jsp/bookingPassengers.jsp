<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
	<!-- css files -->
	<link th:href="@{/css/stepper.css}" rel="stylesheet" />

	<style>
		.icon-circle {
			display: inline-block;
			background-color: #e9ecef;
			color: white;
			border-radius: 50%;
			width: 60px;
			height: 60px;
			text-align: center;
			line-height: 60px;
			font-size: 30px;
			margin-top: 50%;
		}

		.card-pass:hover {
			box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
		}
	</style>
</head>

<body>
	<div layout:fragment="content">

		<div class="container padding-bottom-3x mb-1">
			<div class="card mb-1 border-0">
				<h2 class="fw-bold mb-1 text-center">Đặt Vé</h2>
				<div class="card-body">
					<div
						class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
						<div class="step completed">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-earth-americas"></i>
								</div>
							</div>
							<h4 class="step-title">Chuyến Đi</h4>
						</div>
						<div class="step completed">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-plane"></i>
								</div>
							</div>
							<h4 class="step-title">Chuyến Bay</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-users"></i>
								</div>
							</div>
							<h4 class="step-title">Hành Khách</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-chair"></i>
								</div>
							</div>
							<h4 class="step-title">Chỗ Ngồi</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-suitcase-rolling"></i>
								</div>
							</div>
							<h4 class="step-title">Hành Lý</h4>
						</div>
					</div>
				</div>
			</div>
		</div>

		<section>
			<div class="container">
				<form th:action="@{/bookingPassengers/ProcessBookingPassengers}" th:object="${passengerListDTO}"
					method="post">
					<div class="row">
						<div th:each="passenger, passengerIndex : *{passengerList}" class="col-lg-4 col-md-4 mb-3">
							<div class="card card-pass" style="max-width: 450px;">
								<div class="row">
									<div class="col-md-4">
										<div class="text-center">
											<div class="icon-circle">
												<i class="fa-solid fa-user"></i>
											</div>
											<h5 class="card-title">
												Hành Khách <br /> <span th:text="${passengerIndex.index + 1}"></span>
											</h5>
										</div>
									</div>
									<div class="col-md-7">
										<div class="card-body">

											<div class="form-floating mb-3">
												<input type="text" name="firstName" maxlength="30"
													th:field="*{passengerList[__${passengerIndex.index}__].firstName}"
													class="form-control" placeholder="Tên"> <label
													for="firstName">Tên</label>
											</div>
											<div class="text-danger">
												<p th:if="${#fields.hasErrors('passengerList[__${passengerIndex.index}__].firstName')}"
													th:errors="*{passengerList[__${passengerIndex.index}__].firstName}"
													class="error-message"></p>
											</div>

											<div class="form-floating mb-3">
												<input type="text" name="lastName" maxlength="30"
													th:field="*{passengerList[__${passengerIndex.index}__].lastName}"
													class="form-control" placeholder="Họ"> <label
													for="lastName">Họ</label>
											</div>
											<div class="text-danger">
												<p th:if="${#fields.hasErrors('passengerList[__${passengerIndex.index}__].lastName')}"
													th:errors="*{passengerList[__${passengerIndex.index}__].lastName}"
													class="error-message"></p>
											</div>

											<div class="form-floating mb-3">
												<select name="gender" id="numOfPassengers"
													class="selectpicker form-control"
													th:field="*{passengerList[__${passengerIndex.index}__].gender}">
													<option value="0">Chọn</option>
													<option value="M">Nam</option>
													<option value="F">Nữ</option>
												</select> <label for="gender">Giới Tính</label>
											</div>
											<div class="text-danger">
												<p th:if="${#fields.hasErrors('passengerList[__${passengerIndex.index}__].gender')}"
													th:errors="*{passengerList[__${passengerIndex.index}__].gender}"
													class="error-message"></p>
											</div>

											<div class="form-floating mb-3">
												<input type="text" name="passportID" maxlength="9"
													th:field="*{passengerList[__${passengerIndex.index}__].passportID}"
													class="form-control" placeholder="Số Hộ Chiếu"> <label
													for="passportID">Số Hộ Chiếu</label>
											</div>
											<div class="text-danger">
												<p th:if="${#fields.hasErrors('passengerList[__${passengerIndex.index}__].passportID')}"
													th:errors="*{passengerList[__${passengerIndex.index}__].passportID}"
													class="error-message"></p>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="mb-2">
							<a class="btn btn-secondary rounded-0 me-4" href="javascript:history.back()">
								<i class="fa-solid fa-circle-arrow-left p-2"></i>Quay lại</a>
							<button id="nextBtn" type="submit" class="btn btn-primary rounded-0">Tiếp tục
								<i class="fa-solid fa-circle-arrow-right p-2"></i></button>
						</div>
					</div>
				</form>
			</div>
		</section>
	</div>
</body>

</html>