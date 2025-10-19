<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>


	<link th:href="@{/css/stepper.css}" rel="stylesheet" />

	<script type="text/javascript" th:src="@{/js/seatCountdown.js}"></script>

	<style>
		.alert {
			position: fixed;
			top: 35%;
			left: 0;
			z-index: 100000;
		}
	</style>
	<script>
		$(document).ready(function () {
			startCountdown();
		});
	</script>
</head>

<body>
	<div layout:fragment="content">


		<div class="container padding-bottom-3x mb-1">
			<div class="card mb-1 border-0">
				<h2 class="fw-bold mb-1 text-center">Đặt vé</h2>
				<div class="card-body">
					<div
						class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
						<div class="step completed">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-earth-americas"></i>
								</div>
							</div>
							<h4 class="step-title">Chuyến đi</h4>
						</div>
						<div class="step completed">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-plane"></i>
								</div>
							</div>
							<h4 class="step-title">Chuyến bay</h4>
						</div>
						<div class="step completed">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-users"></i>
								</div>
							</div>
							<h4 class="step-title">Hành khách</h4>
						</div>
						<div class="step completed">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-chair"></i>
								</div>
							</div>
							<h4 class="step-title">Chỗ ngồi</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-suitcase-rolling"></i>
								</div>
							</div>
							<h4 class="step-title">Hành lý</h4>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div id="countdown-alert" class="alert alert-primary w-40" role="alert">
			<input type="hidden" id="seatsReserveTime" th:value="${SeatsReservationTime}" /> Ghế bạn đã chọn được lưu
			trong <span id="countdown"></span> phút!
		</div>


		<section>
			<div class="container">
				<form th:action="@{/bookingLuggage/ProcessBookingLuggage}" th:object="${passengerListDTO}"
					method="post">
					<div class="row">
						<div th:each="passenger, passengerIndex : *{passengerList}" class="col-lg-4 col-md-4 mb-3">
							<div class="card card-pass" style="max-width: 450px;">
								<div class="row">
									<div class="col">
										<div class="card-body">
											<div class="form-floating mb-3">

												<input disabled type="text" class="form-control"
													th:id="${'passenger' + passenger.passportID}"
													th:value="${passenger.firstName + ' ' + passenger.lastName}">
												<label th:for="${'passenger' + passenger.passportID}">Tên Hành
													Khách</label>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="form-floating mb-3">

											<select th:id="${'luggage' + passenger.passportID}"
												th:name="'passenger_' + ${passenger.passportID} + '_luggageIds'"
												class="selectpicker form-control" multiple data-size="5">
												<option th:each="luggage : ${LuggageList}" th:value="${luggage.id}"
													th:text="${luggage.description + ' - ' + '$' + #numbers.formatDecimal(luggage.price, 0, 'COMMA', 0, 'POINT')}">
												</option>

											</select>
											<label th:for="${'luggage' + passenger.passportID}">Luggage</label>
											<script>

												$(document).ready(function () {
													$('.selectpicker option[value="1"]').prop('selected', true);
													$('.selectpicker').selectpicker('refresh');

													$(".selectpicker").on("change", function () {
														$('.selectpicker option[value="1"]').prop('selected', true);
														$('.selectpicker').selectpicker('refresh');
													});
												});
											</script>
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
							<button id="nextBtn" type="submit" class="btn btn-primary rounded-0">Tiếp
								<i class="fa-solid fa-circle-arrow-right p-2"></i></button>
						</div>
					</div>
				</form>
			</div>
		</section>
	</div>
</body>

</html>