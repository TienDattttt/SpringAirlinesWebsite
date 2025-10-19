<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>

	<script>
		$(document).ready(function () {

			$('#modal').on('hidden.bs.modal', function (e) {
				window.location.reload();
			});


			$(document).on('click', '.openModalButton', function () {

				var routeNumber = $(this).data('route-number');
				$('#modal-body').load('userFeedbackAdd?routeNumber=' + routeNumber, function () {
					$('#modal').modal('show');
				});
			});
		});
	</script>
</head>

<body>
	<div layout:fragment="content">
		<div class="container">
			<h2 class="fw-bold mb-4 text-center">Biểu Mẫu Phản Hồi</h2>
			<p class="text-muted">
				Nhấp vào các nút để điền phản hồi về các tuyến bay trước đó của bạn.
			</p>
		</div>

		<section th:if="${!routeFeedbackMap.isEmpty()}">
			<div class="container" style="align-items: center;">

				<button th:each="entry : ${routeFeedbackMap}" class="btn btn-light p-4 m-4 openModalButton"
					th:data-route-number="${entry.key.routeNumber}" th:disabled="${!entry.value}">
					<div th:if="${#lists.size(entry.key.flights) == 1}">

						<div th:each="flight : ${entry.key.flights}" class="d-flex justify-content-center">
							<div th:if="${flightStat.index == 0}">
								<i class="fa-solid fa-calendar me-2"></i><span class="me-4"
									th:text="${'Arrival On ' + flight.arrivalDate + ' ' + flight.arrivalTime}"></span>
								<span
									th:text="${flight.departureAirport.city.name + ', ' + flight.departureAirport.city.country.name}"></span>
								<i class="fa-solid fa-arrow-right-long me-2"></i> <span
									th:text="${flight.arrivalAirport.city.name + ', ' + flight.arrivalAirport.city.country.name}"></span>
							</div>
						</div>
					</div>
					<div th:if="${#lists.size(entry.key.flights) == 2}" class="d-flex justify-content-center">

						<div th:each="flight : ${entry.key.flights}">
							<div th:if="${flightStat.index == 0}">
								<i class="fa-solid fa-calendar me-2"></i><span class="me-4"
									th:text="${'Arrival On ' + flight.arrivalDate + ' ' + flight.arrivalTime}"></span>
								<span
									th:text="${flight.departureAirport.city.name + ', ' + flight.departureAirport.city.country.name}"></span>
								<i class="fa-solid fa-arrow-right-long me-2"></i>
							</div>
							<div th:if="${flightStat.index == 1}">
								<span
									th:text="${flight.arrivalAirport.city.name + ', ' + flight.arrivalAirport.city.country.name}"></span>
							</div>
						</div>
					</div>
				</button>
			</div>
		</section>

		<section th:if="${routeFeedbackMap.isEmpty()}">
			<br />
			<div class="container text-center">
				<div class="alert alert-primary w-40" role="alert">
					Không có đặt chỗ nào tồn tại, hãy tham gia cùng chúng tôi để khám phá một cuộc phiêu lưu mới!
				</div>
			</div>
		</section>

		<!-- Modal -->
		<div class="modal fade" id="modal" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header d-flex justify-content-center bg-info text-white">
						<h5 class="modal-title" id="exampleModalLabel">Phản hồi</h5>
					</div>
					<div class="modal-body" id="modal-body"></div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>