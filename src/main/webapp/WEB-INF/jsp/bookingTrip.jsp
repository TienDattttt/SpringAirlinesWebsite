<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
	<!-- css files -->
	<link th:href="@{/css/stepper.css}" rel="stylesheet" />

	<!-- js files -->
	<script type="text/javascript" th:src="@{/js/airports.js}"></script>

	<script type="text/javascript">
		$('.selectpicker').selectpicker(); // bootstrap-select fields

		$(document).ready(function () {
			fetchArrivalAirports(); // file: "/js/airports.js"
		});
	</script>
</head>

<body>
	<div layout:fragment="content">

		<!-- stepper -->
		<div class="container padding-bottom-3x mb-1">
			<div class="card mb-1 border-0">
				<h2 class="fw-bold mb-1 text-center">Đặt Vé</h2>
				<div class="card-body">
					<div
						class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-earth-americas"></i>
								</div>
							</div>
							<h4 class="step-title">Chuyến Đi</h4>
						</div>
						<div class="step">
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
								<div the="step-icon">
									<i class="fa-solid fa-suitcase-rolling"></i>
								</div>
							</div>
							<h4 class="step-title">Hành Lý</h4>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Form -->
		<section>
			<div class="card mx-auto col-md-8 shadow-5-strong"
				style="background: hsla(0, 0%, 100%, 0.8); backdrop-filter: blur(30px);">
				<div class="card-body">
					<p class="text-muted">Điền vào mẫu dưới đây:</p>
					<div class="row d-flex justify-content-center">
						<div>
							<form th:action="@{/bookingTrip/ProcessBookingTrip}" th:object="${tripForm}" method="post">
								<div class="row">
									<div class="col-3">
										<div class="form-floating mb-3">
											<select name="departureAirportID" id="departureAirportID"
												class="selectpicker form-control" data-size="4"
												th:field="*{departureAirportID}">
												<option value="0">Chọn</option>
												<option th:each="option : ${departureAirportsList}"
													th:value="${option.id}" th:text="${option.code}"
													th:data-subtext="${option.city.name + ', ' + option.city.country.name}">
												</option>
											</select> <label for="departureAirportID">Sân Bay Khởi Hành</label>
										</div>
										<div class="text-danger">
											<p th:if="${#fields.hasErrors('departureAirportID')}"
												th:errors="*{departureAirportID}" class="error-message"></p>
										</div>
									</div>
									<div class="col-3">
										<div class="form-floating mb-3">
											<select name="arrivalAirportID" id="arrivalAirportID"
												class="selectpicker form-control" data-size="4"
												th:field="*{arrivalAirportID}">
												<option value="0">Chọn</option>
											</select><label for="arrivalAirportID">Sân Bay Đến</label>
										</div>
										<div class="text-danger">
											<p th:if="${#fields.hasErrors('arrivalAirportID')}"
												th:errors="*{arrivalAirportID}" class="error-message"></p>
										</div>
									</div>
									<div class="col-3">
										<div class="form-floating mb-3">
											<input type="text" name="departureDate" id="departureDate"
												class="form-control" placeholder="Ngày Khởi Hành"
												th:field="*{departureDate}"> <label for="departureDate">Ngày Khởi
												Hành</label>
											<script>
												flatpickr("#departureDate", {
													dateFormat: "Y-m-d",
													minDate: "today",
													altInput: true,
													altFormat: "d.m.Y",
												});
											</script>
										</div>
										<div class="text-danger">
											<p th:if="${#fields.hasErrors('departureDate')}"
												th:errors="*{departureDate}" class="error-message"></p>
										</div>
									</div>
									<div class="col-2">
										<div class="form-floating mb-3">
											<select name="numOfPassengers" id="numOfPassengers"
												class="selectpicker form-control" data-size="4"
												th:field="*{numOfPassengers}">
												<option value="0">Chọn</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
												<option value="9">9</option>
												<option value="10">10</option>
											</select> <label for="numOfPassengers">Số Hành Khách</label>
										</div>
										<div class="text-danger">
											<p th:if="${#fields.hasErrors('numOfPassengers')}"
												th:errors="*{numOfPassengers}" class="error-message"></p>
										</div>
									</div>
								</div>
								<div class="text-center">
									<button id="nextBtn" type="submit" class="btn btn-primary next-button" style="
											background-color: #007bff; 
											color: #ffffff; 
											border: none; 
											padding: 12px 30px; 
											font-size: 1.2rem; 
											font-weight: bold; 
											border-radius: 50px; 
											display: inline-flex; 
											align-items: center; 
											justify-content: center; 
											transition: all 0.3s ease-in-out; 
											box-shadow: 0 5px 15px rgba(0, 123, 255, 0.3); 
											position: relative; 
											overflow: hidden;" onmouseover="this.style.backgroundColor='#0056b3'; 
													this.style.boxShadow='0 10px 20px rgba(0, 123, 255, 0.4)'; 
													this.style.transform='translateY(-3px)';" onmouseout="this.style.backgroundColor='#007bff'; 
													this.style.boxShadow='0 5px 15px rgba(0, 123, 255, 0.3)'; 
													this.style.transform='translateY(0)';" onmousedown="this.style.transform='scale(0.97)'; 
													this.style.boxShadow='0 5px 10px rgba(0, 123, 255, 0.2)';"
										onmouseup="this.style.transform='translateY(0)';">
										Tiếp Tục
										<i class="fa-solid fa-circle-arrow-right" style="
												margin-left: 10px; 
												transition: all 0.3s ease-in-out;" onmouseover="this.style.transform='translateX(5px)';"
											onmouseout="this.style.transform='translateX(0)';">
										</i>
									</button>
								</div>

							</form>
							<span th:if="${error_message != null}" class="text-danger" th:text="${error_message}">
							</span>
						</div>
					</div>
				</div>
			</div>
		</section>

	</div>
</body>

</html>